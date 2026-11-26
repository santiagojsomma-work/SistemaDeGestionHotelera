package com.hotel.reporte;

import com.hotel.modelo.TipoHabitacion;
import com.hotel.util.Constantes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Reporte {
    private static final String DELIMITADOR = ",";
    private static final int INDICE_FECHA = 1;
    private static final int INDICE_TIPO_HABITACION = 2;
    private static final DateTimeFormatter FORMATO_FECHA_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATO_FECHA_ARG = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Map<String, Integer> reservasPorMes;
    private Map<String, Integer> reservasPorDiaSemana;
    private Map<TipoHabitacion, Integer> reservasPorTipoHabitacion;

    public Reporte() {
        this.reservasPorMes = new HashMap<>();
        this.reservasPorDiaSemana = new HashMap<>();
        this.reservasPorTipoHabitacion = new HashMap<>();
    }

    public void generarReportes() {
        String rutaCSV = Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV;

        if (!Files.exists(Paths.get(rutaCSV))) {
            throw new IllegalStateException("No existe '" + rutaCSV + "'. Ejecutá primero GenerarArchivoReservas.");
        }

        procesarArchivo(rutaCSV);
        mostrarResultados();
    }

    private void procesarArchivo(String rutaCSV) {
        reservasPorMes.clear();
        reservasPorDiaSemana.clear();
        reservasPorTipoHabitacion.clear();

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaCSV))) {
            lector.readLine(); // saltar encabezado
            String linea;
            int numeroLinea = 1;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (!linea.trim().isEmpty()) {
                    procesarLinea(linea, numeroLinea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarResultados() {
        int total = reservasPorMes.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("==============================================");
        System.out.println("REPORTE UNIFICADO DE RESERVAS");
        System.out.println("==============================================");
        System.out.println("Total de registros procesados: " + total);
        System.out.println();
        mostrarReservasPorMes();
        mostrarReservasPorDiaSemana();
        mostrarReservasPorTipoHabitacion();
    }

    private void procesarLinea(String linea, int numeroLinea) {
        String[] columnas = linea.split(DELIMITADOR, -1);
        if (columnas.length < 5) {
            throw new IllegalArgumentException("Línea " + numeroLinea + " malformada");
        }
        String fechaStr = columnas[INDICE_FECHA].trim();
        String tipoStr = columnas[INDICE_TIPO_HABITACION].trim();

        LocalDate fecha = parsearFechaFlexible(fechaStr, numeroLinea);
        String mes = String.format("%02d", fecha.getMonthValue()) + "-" + fecha.getYear();
        reservasPorMes.merge(mes, 1, Integer::sum);

        String diaSemana = traducirDiaSemana(fecha.getDayOfWeek());
        reservasPorDiaSemana.merge(diaSemana, 1, Integer::sum);

        TipoHabitacion tipo = TipoHabitacion.valueOf(tipoStr);
        reservasPorTipoHabitacion.merge(tipo, 1, Integer::sum);
    }

    private LocalDate parsearFechaFlexible(String fechaStr, int numeroLinea) {
        try {
            if (fechaStr.contains("/")) {
                return LocalDate.parse(fechaStr, FORMATO_FECHA_ARG);
            } else {
                return LocalDate.parse(fechaStr, FORMATO_FECHA_ISO);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": fecha inválida '" + fechaStr + "'");
        }
    }

    private String traducirDiaSemana(DayOfWeek dia) {
        switch (dia) {
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miércoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sábado";
            case SUNDAY: return "Domingo";
            default: return dia.name();
        }
    }

    // FIX: tipo acotado <K extends Comparable<K>> para que comparingByKey() compile
    private <K extends Comparable<K>> void mostrarMapaOrdenado(String titulo, Map<K, Integer> mapa, boolean descendente) {
        System.out.println(titulo);
        System.out.println("----------------------------------------------");
        Comparator<Map.Entry<K, Integer>> comparador = descendente
                ? Map.Entry.<K, Integer>comparingByValue().reversed()
                : Map.Entry.<K, Integer>comparingByKey();
        mapa.entrySet().stream()
                .sorted(comparador)
                .forEach(e -> System.out.printf("   %s: %d%n", e.getKey(), e.getValue()));
        System.out.println();
    }

    private void mostrarReservasPorMes() {
        mostrarMapaOrdenado("1) RESERVAS POR MES:", reservasPorMes, false);
    }

    private void mostrarReservasPorDiaSemana() {
        System.out.println("2) DÍA DE LA SEMANA CON MÁS RESERVAS:");
        System.out.println("----------------------------------------------");
        Map.Entry<String, Integer> max = reservasPorDiaSemana.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        if (max != null) {
            System.out.printf("   Día con más reservas: %s (%d reservas)%n", max.getKey(), max.getValue());
        }
        // FIX: título real en vez de string vacío
        mostrarMapaOrdenado("   Detalle completo:", reservasPorDiaSemana, true);
    }

    private void mostrarReservasPorTipoHabitacion() {
        mostrarMapaOrdenado("3) TIPOS DE HABITACIÓN MÁS CONTRATADOS (descendente):",
                reservasPorTipoHabitacion, true);
    }

    public Map<String, Integer> getReservasPorMes() { return reservasPorMes; }
    public Map<String, Integer> getReservasPorDiaSemana() { return reservasPorDiaSemana; }
    public Map<TipoHabitacion, Integer> getReservasPorTipoHabitacion() {
        // Devolver copia ordenada descendente por cantidad (coherente con lo que muestra el reporte)
        Map<TipoHabitacion, Integer> ordenado = new LinkedHashMap<>();
        reservasPorTipoHabitacion.entrySet().stream()
                .sorted(Map.Entry.<TipoHabitacion, Integer>comparingByValue().reversed())
                .forEach(e -> ordenado.put(e.getKey(), e.getValue()));
        return ordenado;
    }
    public static void main(String[] args) {
        Reporte reporte = new Reporte();
        reporte.generarReportes();
    }
}