package com.hotel.modelo;

import com.hotel.patron.ObservadorLiberacion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Huesped extends Persona implements ObservadorLiberacion {
    private String documento;
    private List<Reserva> reservas;

    public Huesped(String nombre, String apellido, String dni, LocalDate fechaNacimiento, String documento) {
        super(nombre, apellido, dni, fechaNacimiento);
        this.documento = documento;
        this.reservas = new ArrayList<>();
    }

    public String getDocumento() { return documento; }
    
    
    public void agregarReserva(Reserva reserva) {
        if (!this.reservas.contains(reserva)) {
            this.reservas.add(reserva);
        }
    }

    public List<Reserva> getReservas() { return reservas; }

    @Override
    public void actualizar(TipoHabitacion tipoLiberado, int costoPorNoche) {
        System.out.println("Notificación a huésped " + this.getNombre() + 
                ": Se liberó una habitación " + tipoLiberado + " con costo $" + costoPorNoche);
    }
    @Override
    public String getRol() { return "Huésped"; }
}