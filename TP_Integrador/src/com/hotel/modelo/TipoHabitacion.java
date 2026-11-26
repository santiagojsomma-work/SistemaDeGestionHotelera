package com.hotel.modelo;

public enum TipoHabitacion {
    SIMPLE(50000),
    DOBLE(80000),
    SUITE(120000);

    private final int tarifa;

    TipoHabitacion(int tarifa) {
        this.tarifa = tarifa;
    }

    public int getTarifa() {
        return tarifa;
    }
}