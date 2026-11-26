package com.hotel.modelo;

import com.hotel.patron.ObservadorLiberacion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private static Hotel instancia;
    private static int cantidadHotelesCadena = 0;

    private String nombre;
    private String direccion;
    private int estrellasObtenidas;
    private List<Habitacion> habitaciones;
    private List<ObservadorLiberacion> observadores;
    private List<Reserva> reservas;
    private List<Puntuacion> puntuaciones;
    private GestorPuntuaciones gestorPuntuaciones; 

    private Hotel(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.estrellasObtenidas = 0;
        this.habitaciones = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.puntuaciones = new ArrayList<>();
        this.gestorPuntuaciones = new GestorPuntuaciones();
        cantidadHotelesCadena++;
    }

    public static Hotel getInstancia(String nombre, String direccion) {
        if (instancia == null) {
            instancia = new Hotel(nombre, direccion);
        }
        return instancia;
    }

    public static int getCantidadHotelesCadena() {
        return cantidadHotelesCadena;
    }

    public void agregarHabitacion(Habitacion habitacion) {
        this.habitaciones.add(habitacion);
    }

    public void liberarHabitacion(Habitacion habitacion, Huesped huespedQueSale) {
        for (ObservadorLiberacion obs : observadores) {
            if (obs != huespedQueSale) {
                obs.actualizar(habitacion.getTipo(), habitacion.getCostoPorNoche());
            }
        }
    }

    public void suscribir(ObservadorLiberacion observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void desuscribir(ObservadorLiberacion observador) {
        observadores.remove(observador);
    }

    
    public Reserva generarReserva(Reserva.Builder builder) {
        Reserva reserva = builder.build();
        this.reservas.add(reserva);
        if (reserva.getHuesped() != null) {
            reserva.getHuesped().agregarReserva(reserva);
        }
        return reserva;
    }

   
    public void aplicarPuntuacion(Reserva reserva, int valor) {
        Puntuacion puntuacion = reserva.crearPuntuacion(valor);
        this.gestorPuntuaciones.agregarPuntuacion(puntuacion);
        this.estrellasObtenidas = this.gestorPuntuaciones.getEstrellasObtenidas();
    }

    public int getEstrellasObtenidas() {
        return this.gestorPuntuaciones.getEstrellasObtenidas();
    }

    public List<Reserva> getReservas() { return reservas; }
    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public List<ObservadorLiberacion> getObservadores() { return observadores; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }

    public void demoler() {
        this.habitaciones.clear();
        this.habitaciones = null;
        this.observadores.clear();
        this.observadores = null;
        this.reservas.clear();
        this.reservas = null;
        this.puntuaciones.clear();
        this.puntuaciones = null;
        cantidadHotelesCadena--;
        instancia = null;
    }
}