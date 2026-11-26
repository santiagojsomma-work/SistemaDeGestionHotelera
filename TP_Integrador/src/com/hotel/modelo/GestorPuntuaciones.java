package com.hotel.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Extraída de Hotel para resolver Large Class.
 * Responsabilidad única: gestionar puntuaciones y calcular estrellas.
 */
public class GestorPuntuaciones {
    private List<Puntuacion> puntuaciones;
    private int estrellasObtenidas;

    public GestorPuntuaciones() {
        this.puntuaciones = new ArrayList<>();
        this.estrellasObtenidas = 0;
    }

    public void agregarPuntuacion(Puntuacion puntuacion) {
        this.puntuaciones.add(puntuacion);
        calcularEstrellas();
    }

    private void calcularEstrellas() {
        if (puntuaciones.isEmpty()) {
            this.estrellasObtenidas = 0;
            return;
        }
        int suma = 0;
        for (Puntuacion p : puntuaciones) {
            suma += p.getValor();
        }
        this.estrellasObtenidas = (int) Math.round((double) suma / puntuaciones.size());
    }

    public int getEstrellasObtenidas() {
        return estrellasObtenidas;
    }

    public List<Puntuacion> getPuntuaciones() {
        return new ArrayList<>(puntuaciones); // Copia para evitar modificación externa
    }
}