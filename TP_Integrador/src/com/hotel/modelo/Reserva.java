package com.hotel.modelo;

import java.time.LocalDate;

public class Reserva {
    private int numeroReserva;
    private LocalDate fecha;
    private TipoHabitacion tipoHabitacion;
    private int cantidadNoches;
    private int totalAPagar;
    private Huesped huesped;
    private Habitacion habitacion;
    private Empleado empleado;
    private Puntuacion puntuacion;

    private Reserva(Builder builder) {
        this.numeroReserva = builder.numeroReserva;
        this.fecha = builder.fecha;
        this.tipoHabitacion = builder.tipoHabitacion;
        this.cantidadNoches = builder.cantidadNoches;
        this.totalAPagar = this.cantidadNoches * this.tipoHabitacion.getTarifa();
        this.huesped = builder.huesped;
        this.habitacion = builder.habitacion;
        this.empleado = builder.empleado;
    }
    public Puntuacion crearPuntuacion(int valor) {
        Puntuacion puntuacion = new Puntuacion(valor, LocalDate.now(), this.huesped, this);
        this.puntuacion = puntuacion;
        return puntuacion;
    }

    public static class Builder {
        private int numeroReserva;
        private LocalDate fecha;
        private TipoHabitacion tipoHabitacion;
        private int cantidadNoches;
        private Huesped huesped;
        private Habitacion habitacion;
        private Empleado empleado;

        public Builder numeroReserva(int n) { this.numeroReserva = n; return this; }
        public Builder fecha(LocalDate f) { this.fecha = f; return this; }
        public Builder tipoHabitacion(TipoHabitacion t) { this.tipoHabitacion = t; return this; }
        public Builder cantidadNoches(int c) { this.cantidadNoches = c; return this; }
        public Builder huesped(Huesped h) { this.huesped = h; return this; }
        public Builder habitacion(Habitacion h) { this.habitacion = h; return this; }
        public Builder empleado(Empleado e) { this.empleado = e; return this; }

        public Reserva build() { return new Reserva(this); }
    }

    public int getNumeroReserva() { return numeroReserva; }
    public LocalDate getFecha() { return fecha; }
    public TipoHabitacion getTipoHabitacion() { return tipoHabitacion; }
    public int getCantidadNoches() { return cantidadNoches; }
    public int getTotalAPagar() { return totalAPagar; }
    public Huesped getHuesped() { return huesped; }
    public Habitacion getHabitacion() { return habitacion; }
    public Empleado getEmpleado() { return empleado; }
    public Puntuacion getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Puntuacion puntuacion) { this.puntuacion = puntuacion; }
}