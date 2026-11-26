package com.hotel.modelo;

public class Habitacion {
    private int numero;
    private TipoHabitacion tipo;
    private int costoPorNoche;

    public Habitacion(int numero, TipoHabitacion tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.costoPorNoche = tipo.getTarifa();
    }

    public int getNumero() {
        return numero;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public int getCostoPorNoche() {
        return costoPorNoche;
    }
}