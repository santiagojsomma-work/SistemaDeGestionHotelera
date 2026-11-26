package test;

import com.hotel.modelo.*;
import java.time.LocalDate;

public class TestDominio {

    private static int ok = 0;
    private static int falla = 0;

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("TEST: DOMINIO (PDF 18.0 + PDF 1.4)");
        System.out.println("==============================================\n");

        // SOLUCIÓN Duplicated Code: cada caso es un método separado
        casoA_AltaHotel();
        casoB_Suscripcion();
        casoC_Reservas();
        casoD_Puntuacion();
        casoE_Demolicion();

        System.out.println("==============================================");
        System.out.println("RESUMEN TEST DOMINIO: " + ok + " OK, " + falla + " FALLA(S).");
        System.out.println("==============================================");
    }

    // SOLUCIÓN Duplicated Code: método helper
    private static void verificar(boolean condicion, String nombreCaso) {
        if (condicion) {
            System.out.println("[OK] " + nombreCaso + "\n");
            ok++;
        } else {
            System.out.println("[FALLA] " + nombreCaso + "\n");
            falla++;
        }
    }

    private static void casoA_AltaHotel() {
        System.out.println("--- CASO a) Alta de hotel, huéspedes y empleados ---");
        System.out.println("[PRE] cantidadHotelesCadena = " + Hotel.getCantidadHotelesCadena());

        Hotel hotel = Hotel.getInstancia("Hotel Central", "Av. Siempreviva 742");
        hotel.agregarHabitacion(new Habitacion(101, TipoHabitacion.SIMPLE));
        hotel.agregarHabitacion(new Habitacion(102, TipoHabitacion.DOBLE));
        hotel.agregarHabitacion(new Habitacion(103, TipoHabitacion.SUITE));

        Huesped hu1 = new Huesped("Ana", "García", "30111222", LocalDate.of(1990, 5, 15), "D1");
        Huesped hu2 = new Huesped("Luis", "Pérez", "25333444", LocalDate.of(1985, 8, 20), "D2");
        Huesped hu3 = new Huesped("María", "López", "28555666", LocalDate.of(1992, 3, 10), "D3");
        Huesped hu4 = new Huesped("Juan", "Díaz", "20777888", LocalDate.of(1988, 11, 25), "D4");
        Huesped hu5 = new Huesped("Sofía", "Rojas", "33999000", LocalDate.of(1995, 7, 30), "D5");
        Huesped hu6 = new Huesped("Pedro", "Sánchez", "15111222", LocalDate.of(1980, 1, 12), "D6");

        Empleado em1 = new Empleado("Carlos", "Méndez", "18222333", LocalDate.of(1975, 4, 5), 
                                     "LEG001", Cargo.RECEPCION, 800000);
        Empleado em2 = new Empleado("Laura", "Gómez", "20444555", LocalDate.of(1982, 9, 18), 
                                     "LEG002", Cargo.LIMPIEZA, 600000);

        System.out.println("[POST] Habitaciones: " + hotel.getHabitaciones().size()
                + " | cadena: " + Hotel.getCantidadHotelesCadena()
                + " | edad Ana: " + hu1.getEdad()
                + " | cargo em1: " + em1.getCargo());

        verificar(hotel.getHabitaciones().size() == 3 && Hotel.getCantidadHotelesCadena() == 1, "CASO a)");
    }

    private static void casoB_Suscripcion() {
        System.out.println("--- CASO b) Suscripción ---");
        System.out.println("[PRE] Observadores: " + Hotel.getInstancia("Hotel Central", "Av. Siempreviva 742").getObservadores().size());
        
        Hotel hotel = Hotel.getInstancia("Hotel Central", "Av. Siempreviva 742");
        Huesped[] huespedes = {
            new Huesped("Ana", "García", "30111222", LocalDate.of(1990, 5, 15), "D1"),
            new Huesped("Luis", "Pérez", "25333444", LocalDate.of(1985, 8, 20), "D2"),
            new Huesped("María", "López", "28555666", LocalDate.of(1992, 3, 10), "D3"),
            new Huesped("Juan", "Díaz", "20777888", LocalDate.of(1988, 11, 25), "D4"),
            new Huesped("Sofía", "Rojas", "33999000", LocalDate.of(1995, 7, 30), "D5"),
            new Huesped("Pedro", "Sánchez", "15111222", LocalDate.of(1980, 1, 12), "D6")
        };
        
        for (Huesped h : huespedes) hotel.suscribir(h);
        
        System.out.println("[POST] Observadores: " + hotel.getObservadores().size());
        verificar(hotel.getObservadores().size() == 6, "CASO b)");
    }

    private static void casoC_Reservas() {
        System.out.println("--- CASO c) Generar dos reservas ---");
        Hotel hotel = Hotel.getInstancia("Hotel Central", "Av. Siempreviva 742");
        System.out.println("[PRE] Reservas: " + hotel.getReservas().size());

        Huesped hu1 = new Huesped("Ana", "García", "30111222", LocalDate.of(1990, 5, 15), "D1");
        Huesped hu2 = new Huesped("Luis", "Pérez", "25333444", LocalDate.of(1985, 8, 20), "D2");
        Habitacion h101 = new Habitacion(101, TipoHabitacion.SIMPLE);
        Habitacion h103 = new Habitacion(103, TipoHabitacion.SUITE);
        Empleado em1 = new Empleado("Carlos", "Méndez", "18222333", LocalDate.of(1975, 4, 5), 
                                     "LEG001", Cargo.RECEPCION, 800000);

        Reserva r1 = hotel.generarReserva(new Reserva.Builder()
                .numeroReserva(1).fecha(LocalDate.of(2026, 6, 10))
                .tipoHabitacion(TipoHabitacion.SIMPLE).cantidadNoches(3)
                .huesped(hu1).habitacion(h101).empleado(em1));
        
        Reserva r2 = hotel.generarReserva(new Reserva.Builder()
                .numeroReserva(2).fecha(LocalDate.of(2026, 7, 15))
                .tipoHabitacion(TipoHabitacion.SUITE).cantidadNoches(5)
                .huesped(hu2).habitacion(h103).empleado(em1));

        System.out.println("[POST] Reservas: " + hotel.getReservas().size()
                + " | r1 total: $" + r1.getTotalAPagar() + " | r2 total: $" + r2.getTotalAPagar());

        boolean cOk = hotel.getReservas().size() == 2
                && r1.getHuesped() == hu1 && r1.getHabitacion() == h101 && r1.getEmpleado() == em1
                && hu1.getReservas().contains(r1)
                && r1.getTotalAPagar() == 3 * TipoHabitacion.SIMPLE.getTarifa();
        verificar(cOk, "CASO c) + bidireccionalidad");
    }

    private static void casoD_Puntuacion() {
        System.out.println("--- CASO d) Puntuación ---");
        Hotel hotel = Hotel.getInstancia("Hotel Central", "Av. Siempreviva 742");
        System.out.println("[PRE] Estrellas: " + hotel.getEstrellasObtenidas());

        Huesped hu1 = new Huesped("Ana", "García", "30111222", LocalDate.of(1990, 5, 15), "D1");
        Habitacion h101 = new Habitacion(101, TipoHabitacion.SIMPLE);
        Empleado em1 = new Empleado("Carlos", "Méndez", "18222333", LocalDate.of(1975, 4, 5), 
                                     "LEG001", Cargo.RECEPCION, 800000);

        Reserva r1 = hotel.generarReserva(new Reserva.Builder()
                .numeroReserva(1).fecha(LocalDate.of(2026, 6, 10))
                .tipoHabitacion(TipoHabitacion.SIMPLE).cantidadNoches(3)
                .huesped(hu1).habitacion(h101).empleado(em1));

        hotel.aplicarPuntuacion(r1, 5);
        hotel.aplicarPuntuacion(r1, 4);

        System.out.println("[POST] Estrellas: " + hotel.getEstrellasObtenidas()
                + " | r1.puntuacion != null: " + (r1.getPuntuacion() != null));
        verificar(r1.getPuntuacion() != null && r1.getPuntuacion().getReserva() == r1, 
                  "CASO d) + bidireccionalidad");
    }

    private static void casoE_Demolicion() {
        System.out.println("--- CASO e) Demolición ---");
        Hotel hotel = Hotel.getInstancia("Hotel Central", "Av. Siempreviva 742");
        Huesped hu1 = new Huesped("Ana", "García", "30111222", LocalDate.of(1990, 5, 15), "D1");
        System.out.println("[PRE] Hotel != null, Ana != null");

        try {
            hotel.demoler();
            boolean habNulas = hotel.getHabitaciones() == null;
            boolean anaVive = (hu1 != null);
            System.out.println("[POST] Habitaciones nulas: " + habNulas + " | Ana viva: " + anaVive);

            try {
                hotel.getHabitaciones().size();
                verificar(false, "CASO e)");
            } catch (NullPointerException npe) {
                System.out.println("       NullPointerException esperado.");
                verificar(habNulas && anaVive, "CASO e)");
            }
        } catch (Exception ex) {
            System.out.println("[FALLA] CASO e): " + ex.getMessage() + "\n");
            falla++;
        }
    }
}