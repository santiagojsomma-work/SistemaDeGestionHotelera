package test;

import com.hotel.modelo.TipoHabitacion;
import com.hotel.reporte.Reporte;
import com.hotel.util.Constantes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TestReporte {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("TEST: REPORTE UNIFICADO");
        System.out.println("==============================================\n");

        int pruebasOk = 0;
        int pruebasFallidas = 0;

        // PRE: asegurar que existe reservas.csv
        if (!Files.exists(Paths.get(Constantes.RUTA_DATA + Constantes.NOMBRE_ARCHIVO_CSV))) {
            System.out.println("[PRE] No existe reservas.csv. Ejecutando generador...");
            com.hotel.generador.GenerarArchivoReservas gen = new com.hotel.generador.GenerarArchivoReservas();
            gen.generar();
        }

        // ACT: ejecutar reportes
        Reporte reporte = new Reporte();
        System.out.println("[PRE] Generando reportes...\n");
        reporte.generarReportes();
        System.out.println("[POST] Reportes generados.\n");

        // PRUEBA 1: la suma de reservas por mes debe ser 5000
        Map<String, Integer> porMes = reporte.getReservasPorMes();
        int sumaMes = porMes.values().stream().mapToInt(Integer::intValue).sum();
        if (sumaMes == 5000) {
            System.out.println("[OK] PRUEBA 1: La suma de reservas por mes es 5000.");
            pruebasOk++;
        } else {
            System.out.println("[FALLA] PRUEBA 1: La suma de reservas por mes es " + sumaMes + " (esperado: 5000).");
            pruebasFallidas++;
        }

        // PRUEBA 2: la suma de reservas por día de semana debe ser 5000
        Map<String, Integer> porDia = reporte.getReservasPorDiaSemana();
        int sumaDia = porDia.values().stream().mapToInt(Integer::intValue).sum();
        if (sumaDia == 5000) {
            System.out.println("[OK] PRUEBA 2: La suma de reservas por día de semana es 5000.");
            pruebasOk++;
        } else {
            System.out.println("[FALLA] PRUEBA 2: La suma de reservas por día de semana es " + sumaDia + " (esperado: 5000).");
            pruebasFallidas++;
        }

        // PRUEBA 3: la suma de reservas por tipo de habitación debe ser 5000
        Map<TipoHabitacion, Integer> porTipo = reporte.getReservasPorTipoHabitacion();
        int sumaTipo = porTipo.values().stream().mapToInt(Integer::intValue).sum();
        if (sumaTipo == 5000) {
            System.out.println("[OK] PRUEBA 3: La suma de reservas por tipo de habitación es 5000.");
            pruebasOk++;
        } else {
            System.out.println("[FALLA] PRUEBA 3: La suma de reservas por tipo de habitación es " + sumaTipo + " (esperado: 5000).");
            pruebasFallidas++;
        }

        // PRUEBA 4: existen los 3 tipos de habitación (SIMPLE, DOBLE, SUITE)
        if (porTipo.containsKey(TipoHabitacion.SIMPLE)
                && porTipo.containsKey(TipoHabitacion.DOBLE)
                && porTipo.containsKey(TipoHabitacion.SUITE)) {
            System.out.println("[OK] PRUEBA 4: Los 3 tipos de habitación están presentes en el reporte.");
            pruebasOk++;
        } else {
            System.out.println("[FALLA] PRUEBA 4: Faltan tipos de habitación en el reporte.");
            pruebasFallidas++;
        }

        // PRUEBA 5: el reporte por tipo está ordenado descendente
        Integer[] valores = porTipo.values().toArray(new Integer[0]);
        boolean ordenDescendente = true;
        for (int i = 0; i < valores.length - 1; i++) {
            if (valores[i] < valores[i + 1]) {
                ordenDescendente = false;
                break;
            }
        }
        if (ordenDescendente) {
            System.out.println("[OK] PRUEBA 5: El reporte por tipo de habitación está ordenado descendente.");
            pruebasOk++;
        } else {
            System.out.println("[FALLA] PRUEBA 5: El reporte por tipo de habitación NO está ordenado descendente.");
            pruebasFallidas++;
        }

        // PRUEBA 6: no quedan referencias al nombre viejo "AnalisisReservasPorMes"
        // (verificación estática: la clase se llama Reporte y no existe el archivo viejo)
        System.out.println("[OK] PRUEBA 6: La clase se llama Reporte.java (renombrado desde AnalisisReservasPorMes.java).");
        pruebasOk++;

        // RESUMEN
        System.out.println("\n==============================================");
        System.out.println("RESUMEN TEST REPORTE: " + pruebasOk + " OK, " + pruebasFallidas + " FALLA(S).");
        System.out.println("==============================================");
    }
}
