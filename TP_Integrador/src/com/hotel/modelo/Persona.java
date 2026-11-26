package com.hotel.modelo;

import java.time.LocalDate;
import java.time.Period;

public abstract class Persona implements CalculableEdad {
	 protected String nombre;
	 protected String apellido;
	 protected String dni;
	 protected LocalDate fechaNacimiento;
	 protected int edad;

    public Persona(String nombre, String apellido, String dni, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = calcularEdad(fechaNacimiento);
    }

    @Override
    public int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDni() { return dni; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public int getEdad() { return edad; }
    public abstract String getRol();
}