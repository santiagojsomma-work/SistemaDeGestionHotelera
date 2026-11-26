package com.hotel.modelo;

import java.time.LocalDate;

public class Puntuacion {
    private int valor;
    private LocalDate fecha;
    private Huesped huesped;
    private Reserva reserva; 

    public Puntuacion(int valor, LocalDate fecha, Huesped huesped, Reserva reserva) {
        this.valor = valor;
        this.fecha = fecha;
        this.huesped = huesped;
        this.reserva = reserva;
    }

    public int getValor() { return valor; }
    public LocalDate getFecha() { return fecha; }
    public Huesped getHuesped() { return huesped; }
    public Reserva getReserva() { return reserva; }
}