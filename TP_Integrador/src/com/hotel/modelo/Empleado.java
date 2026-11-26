package com.hotel.modelo;

import java.time.LocalDate;

public class Empleado extends Persona {
    private String legajo;
    private Cargo cargo;
    private double salario;

    public Empleado(String nombre, String apellido, String dni, LocalDate fechaNacimiento, 
                    String legajo, Cargo cargo, double salario) {
        super(nombre, apellido, dni, fechaNacimiento);
        this.legajo = legajo;
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getLegajo() { return legajo; }
    public Cargo getCargo() { return cargo; }
    public double getSalario() { return salario; }
    
    @Override
    public String getRol() { return "Empleado de " + cargo.name().toLowerCase(); }
}