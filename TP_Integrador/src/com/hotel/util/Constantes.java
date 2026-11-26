package com.hotel.util;

public final class Constantes {
    private Constantes() {
        // Prevenir instanciación (clase utilitaria)
    }

    public static final int CANTIDAD_REGISTROS_RESERVAS = 5000;
    public static final int NOCHES_MIN = 1;
    public static final int NOCHES_MAX = 7;
    public static final long SEMILLA_RANDOM = 42L; // Para pruebas reproducibles
    
    public static final String RUTA_DATA = "data/";
    public static final String NOMBRE_ARCHIVO_CSV = "reservas.csv";
    public static final String NOMBRE_ARCHIVO_TXT = "reservas.txt";
    public static final String NOMBRE_ARCHIVO_CSV_TRANSFORMADO = "reservas_transformado.csv";
}