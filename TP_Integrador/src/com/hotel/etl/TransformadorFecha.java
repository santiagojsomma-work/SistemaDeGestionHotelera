package com.hotel.etl;

import com.hotel.util.Constantes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TransformadorFecha {
    private static final DateTimeFormatter FORMATO_ENTRADA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATO_SALIDA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String DELIMITADOR = ",";
    private static final int INDICE_COLUMNA_FECHA = 1;

    public void ejecutar() {
        String rutaEntrada = Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV;
        String rutaSalida = Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV_TRANSFORMADO;

        Path pathEntrada = Paths.get(rutaEntrada);
        if (!Files.exists(pathEntrada)) {
            throw new IllegalStateException("No existe el archivo de entrada '" + rutaEntrada + "'");
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaEntrada));
             BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaSalida))) {

            // SOLUCIÓN Long Method: métodos extraídos
            String encabezado = lector.readLine();
            if (encabezado == null) {
                throw new IOException("El archivo de entrada está vacío.");
            }
            escritor.write(encabezado);
            escritor.newLine();

            int totalTransformadas = procesarLineas(lector, escritor);

            System.out.println("ETL completado exitosamente:");
            System.out.println("  - Archivo de entrada: " + rutaEntrada);
            System.out.println("  - Archivo de salida:  " + rutaSalida);
            System.out.println("  - Registros transformados: " + totalTransformadas);

        } catch (IOException e) {
            System.err.println("Error de E/S durante el ETL: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
    private int procesarLineas(BufferedReader lector, BufferedWriter escritor) throws IOException {
        String linea;
        int numeroLinea = 1;
        int totalTransformadas = 0;

        while ((linea = lector.readLine()) != null) {
            numeroLinea++;
            if (!linea.trim().isEmpty()) {
                String lineaTransformada = transformarLinea(linea, numeroLinea);
                escritor.write(lineaTransformada);
                escritor.newLine();
                totalTransformadas++;
            }
        }
        return totalTransformadas;
    }

    private String transformarLinea(String linea, int numeroLinea) {
        String[] columnas = linea.split(DELIMITADOR, -1);
        if (columnas.length < 5) {
            throw new IllegalArgumentException("Línea " + numeroLinea + " malformada");
        }

        String fechaOriginal = columnas[INDICE_COLUMNA_FECHA].trim();
        if (fechaOriginal.isEmpty()) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": fecha vacía");
        }

        columnas[INDICE_COLUMNA_FECHA] = transformarFecha(fechaOriginal, numeroLinea);
        return String.join(DELIMITADOR, columnas);
    }

    private String transformarFecha(String fecha, int numeroLinea) {
        if (fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return fecha;
        }

        try {
            LocalDate fechaParseada = LocalDate.parse(fecha, FORMATO_ENTRADA);
            return fechaParseada.format(FORMATO_SALIDA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": fecha inválida '" + fecha + "'");
        }
    }

    public static void main(String[] args) {
        TransformadorFecha etl = new TransformadorFecha();
        etl.ejecutar();
    }
}