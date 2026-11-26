package test;

import com.hotel.generador.GenerarArchivoReservas;
import com.hotel.modelo.TipoHabitacion;
import com.hotel.util.Constantes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestGenerador {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("TEST: GENERADOR DE RESERVAS");
        System.out.println("==============================================\n");

        int pruebasOk = 0;
        int pruebasFallidas = 0;

        // PRE: limpiar archivos previos
        try {
            Files.deleteIfExists(Paths.get(Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV));
            Files.deleteIfExists(Paths.get(Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_TXT));
        } catch (IOException e) {
            System.out.println("No se pudieron borrar archivos previos: " + e.getMessage());
        }

        System.out.println("[PRE] Archivos CSV y TXT eliminados (si existían).");

        // ACT: ejecutar el generador
        GenerarArchivoReservas generador = new GenerarArchivoReservas();
        generador.generar();

        System.out.println("[POST] Generador ejecutado.\n");

        // PRUEBA 1: existen los archivos
        boolean csvExiste = Files.exists(Paths.get(Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV));
        boolean txtExiste = Files.exists(Paths.get(Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_TXT));
        if (csvExiste && txtExiste) {
            System.out.println("[OK] PRUEBA 1: Ambos archivos existen (CSV y TXT).");
            pruebasOk++;
        } else {
            System.out.println("[FALLA] PRUEBA 1: Faltan archivos. CSV=" + csvExiste + ", TXT=" + txtExiste);
            pruebasFallidas++;
        }

        // PRUEBA 2: exactamente 5000 registros + 1 encabezado = 5001 líneas
        try {
            int lineasCSV = contarLineas(Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV);
            int lineasTXT = contarLineas(Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_TXT);
            if (lineasCSV == 5001 && lineasTXT == 5001) {
                System.out.println("[OK] PRUEBA 2: Ambos archivos tienen 5001 líneas (1 encabezado + 5000 registros).");
                pruebasOk++;
            } else {
                System.out.println("[FALLA] PRUEBA 2: CSV=" + lineasCSV + " líneas, TXT=" + lineasTXT + " líneas.");
                pruebasFallidas++;
            }
        } catch (IOException e) {
            System.out.println("[FALLA] PRUEBA 2: Error al contar líneas: " + e.getMessage());
            pruebasFallidas++;
        }

        // PRUEBA 3: noches entre 1 y 7, total = noches × tarifa, claves únicas
        try {
            Map<Integer, Integer> diccionario = generador.getDiccionarioReservas();
            Set<Integer> clavesUnicas = new HashSet<>(diccionario.keySet());
            boolean nochesCorrectas = true;
            boolean totalesCorrectos = true;
            int registrosProcesados = 0;

            try (BufferedReader lector = new BufferedReader(new FileReader(
                    Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV))) {
                lector.readLine(); // saltar encabezado
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] col = linea.split(",", -1);
                    int noches = Integer.parseInt(col[3]);
                    int total = Integer.parseInt(col[4]);
                    TipoHabitacion tipo = TipoHabitacion.valueOf(col[2]);
                    int totalEsperado = noches * tipo.getTarifa();

                    if (noches < Constantes.NOCHES_MIN || noches > Constantes.NOCHES_MAX) {
                        nochesCorrectas = false;
                    }
                    if (total != totalEsperado) {
                        totalesCorrectos = false;
                    }
                    registrosProcesados++;
                }
            }

            boolean diccionarioCorrecto = diccionario.size() == 5000 && clavesUnicas.size() == 5000;

            if (nochesCorrectas && totalesCorrectos && diccionarioCorrecto && registrosProcesados == 5000) {
                System.out.println("[OK] PRUEBA 3: Noches en [1,7], totales correctos, diccionario con 5000 claves únicas.");
                pruebasOk++;
            } else {
                System.out.println("[FALLA] PRUEBA 3: nochesCorrectas=" + nochesCorrectas
                        + ", totalesCorrectos=" + totalesCorrectos
                        + ", diccionarioCorrecto=" + diccionarioCorrecto
                        + ", registrosProcesados=" + registrosProcesados);
                pruebasFallidas++;
            }
        } catch (Exception e) {
            System.out.println("[FALLA] PRUEBA 3: Error al validar datos: " + e.getMessage());
            pruebasFallidas++;
        }

        // PRUEBA 4: CSV y TXT tienen el mismo contenido
        try {
            boolean sonIguales = compararArchivos(
                    Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV,
                    Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_TXT);
            if (sonIguales) {
                System.out.println("[OK] PRUEBA 4: CSV y TXT tienen el mismo contenido (mismas columnas y valores).");
                pruebasOk++;
            } else {
                System.out.println("[FALLA] PRUEBA 4: CSV y TXT difieren en contenido.");
                pruebasFallidas++;
            }
        } catch (IOException e) {
            System.out.println("[FALLA] PRUEBA 4: Error al comparar archivos: " + e.getMessage());
            pruebasFallidas++;
        }

        // RESUMEN
        System.out.println("\n==============================================");
        System.out.println("RESUMEN TEST GENERADOR: " + pruebasOk + " OK, " + pruebasFallidas + " FALLA(S).");
        System.out.println("==============================================");
    }

    private static int contarLineas(String ruta) throws IOException {
        int contador = 0;
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            while (lector.readLine() != null) {
                contador++;
            }
        }
        return contador;
    }

    private static boolean compararArchivos(String ruta1, String ruta2) throws IOException {
        try (BufferedReader l1 = new BufferedReader(new FileReader(ruta1));
             BufferedReader l2 = new BufferedReader(new FileReader(ruta2))) {
            String linea1, linea2;
            while ((linea1 = l1.readLine()) != null) {
                linea2 = l2.readLine();
                if (linea2 == null || !linea1.equals(linea2)) {
                    return false;
                }
            }
            return l2.readLine() == null; // ambos deben terminar al mismo tiempo
        }
    }
}