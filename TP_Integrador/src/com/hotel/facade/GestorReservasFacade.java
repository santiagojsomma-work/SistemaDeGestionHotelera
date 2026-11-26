package com.hotel.facade;

import com.hotel.modelo.Empleado;
import com.hotel.modelo.Habitacion;
import com.hotel.modelo.Hotel;
import com.hotel.modelo.Huesped;
import com.hotel.modelo.Reserva;
import com.hotel.modelo.TipoHabitacion;

import java.time.LocalDate;


public class GestorReservasFacade {
    private Hotel hotel;

    public GestorReservasFacade(Hotel hotel) {
        this.hotel = hotel;
    }

    public Reserva crearReserva(int numeroReserva, LocalDate fecha,
                                 TipoHabitacion tipo, int noches,
                                 Huesped huesped, Habitacion habitacion,
                                 Empleado empleado) {
        // FIX: el facade construye el Builder y lo pasa a Hotel
        Reserva.Builder builder = new Reserva.Builder()
                .numeroReserva(numeroReserva)
                .fecha(fecha)
                .tipoHabitacion(tipo)
                .cantidadNoches(noches)
                .huesped(huesped)
                .habitacion(habitacion)
                .empleado(empleado);
        return hotel.generarReserva(builder);
    }

    
    public void finalizarReserva(Reserva reserva, int puntuacion) {
        hotel.aplicarPuntuacion(reserva, puntuacion);
    }

    public int getCantidadReservasActivas() {
        return hotel.getReservas().size();
    }

    public int getEstrellasDelHotel() {
        return hotel.getEstrellasObtenidas();
    }
}