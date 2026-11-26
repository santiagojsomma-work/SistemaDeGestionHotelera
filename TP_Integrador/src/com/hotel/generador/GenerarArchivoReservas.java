package com.hotel.generador;

import com.hotel.modelo.Reserva;
import com.hotel.modelo.TipoHabitacion;
import com.hotel.util.Constantes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GenerarArchivoReservas {
    private Random random;
    private Map<Integer, Integer> diccionarioReservas;

    public GenerarArchivoReservas() {
        this.random = new Random(Constantes.SEMILLA_RANDOM);
        this.diccionarioReservas = new HashMap<>();
    }

    public GenerarArchivoReservas(long semillaPersonalizada) {
        this.random = new Random(semillaPersonalizada);
        this.diccionarioReservas = new HashMap<>();
    }

    public void generar() {
        try {
            Path dataDir = Paths.get(Constantes.RUTA_DATA);
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }

            String rutaCSV = Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV;
            String rutaTXT = Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_TXT;

            // PASO 1: Generar todas las reservas UNA SOLA VEZ
            List<Reserva> reservas = new ArrayList<>();
            for (int i = 1; i <= Constantes.CANTIDAD_REGISTROS_RESERVAS; i++) {
                Reserva reserva = generarReserva(i);
                reservas.add(reserva);
                diccionarioReservas.put(reserva.getNumeroReserva(), reserva.getCantidadNoches());
            }

            // PASO 2: Escribir el MISMO contenido en ambos archivos
            escribirArchivo(rutaCSV, reservas);
            escribirArchivo(rutaTXT, reservas);

            System.out.println("Archivos generados exitosamente:");
            System.out.println("  - " + rutaCSV);
            System.out.println("  - " + rutaTXT);
            System.out.println("  - Diccionario con " + diccionarioReservas.size() + " entradas.");

        } catch (IOException e) {
            System.err.println("Error al generar archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // FIX: recibe la lista de reservas ya generadas
    private void escribirArchivo(String ruta, List<Reserva> reservas) throws IOException {
        String encabezado = "numeroReserva,fecha,tipoHabitacion,cantidadNoches,totalAPagar";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            writer.write(encabezado);
            writer.newLine();

            for (Reserva reserva : reservas) {
                String linea = formatearLinea(reserva);
                writer.write(linea);
                writer.newLine();
            }
        }
    }

    private Reserva generarReserva(int numeroReserva) {
        LocalDate fecha = generarFechaAleatoria();
        TipoHabitacion[] tipos = TipoHabitacion.values();
        TipoHabitacion tipo = tipos[random.nextInt(tipos.length)];
        int cantidadNoches = Constantes.NOCHES_MIN + random.nextInt(
                Constantes.NOCHES_MAX - Constantes.NOCHES_MIN + 1
        );

        return new Reserva.Builder()
                .numeroReserva(numeroReserva)
                .fecha(fecha)
                .tipoHabitacion(tipo)
                .cantidadNoches(cantidadNoches)
                .build();
    }

    private LocalDate generarFechaAleatoria() {
        int dayOfYear = 1 + random.nextInt(365);
        return LocalDate.of(2026, 1, 1).plusDays(dayOfYear - 1);
    }

    private String formatearLinea(Reserva reserva) {
        return reserva.getNumeroReserva() + "," +
                reserva.getFecha() + "," +
                reserva.getTipoHabitacion().name() + "," +
                reserva.getCantidadNoches() + "," +
                reserva.getTotalAPagar();
    }

    public Map<Integer, Integer> getDiccionarioReservas() {
        return diccionarioReservas;
    }

    public static void main(String[] args) {
        GenerarArchivoReservas generador = new GenerarArchivoReservas();
        generador.generar();
    }
}