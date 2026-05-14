package es.unican.is2.practica6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Conductor de la empresa y transportes que ha realizado.
 */
public class Conductor {

    private static final double SUELDO_BASE = 700.0;
    private static final double PAGO_POR_HORA = 5.0;
    private static final double PAGO_POR_TONELADA = 2.0;
    private static final double PLUS_MERCANCIAS_PELIGROSAS = 50.0;
    private static final int LIMITE_PERSONAS_PLUS_COMPLETO = 10;
    private static final double PLUS_PERSONAS_POCAS = 0.5;

    private final List<Transporte> transportes = new ArrayList<>();
    private final String dni;
    private final String nombre;
    private final String apellido1;
    private final String apellido2;
    private final String dire;

    public Conductor(String dni, String nombre, String apellido1, String apellido2, String direccion) {
        if (dni == null || nombre == null || apellido1 == null || direccion == null) {
            throw new IllegalArgumentException();
        }
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dire = direccion;
    }

    public String dni() {
        return dni;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String apellido2() {
        return apellido2;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getDire() {
        return dire;
    }

    public String getDireccion() {
        return dire;
    }

    public double sueldo() {
        double sueldoTransportes = 0.0;
        for (Transporte t : transportes) {
            sueldoTransportes += PAGO_POR_HORA * t.horas() + plusTransporte(t);
        }
        return SUELDO_BASE + sueldoTransportes;
    }

    private double plusTransporte(Transporte t) {
        if (t.categoria() == CategoriaTransporte.Personas) {
            return plusPersonas(t);
        }
        double plus = t.ton() * PAGO_POR_TONELADA;
        if (t.categoria().esMercanciaPeligrosa()) {
            plus += PLUS_MERCANCIAS_PELIGROSAS;
        }
        return plus;
    }

    private double plusPersonas(Transporte t) {
        if (t.getPersonas() < LIMITE_PERSONAS_PLUS_COMPLETO) {
            return t.horas() * PLUS_PERSONAS_POCAS;
        }
        return t.horas();
    }

    public void anhadeTransporte(Transporte t) {
        if (t == null) {
            throw new IllegalArgumentException();
        }
        transportes.add(t);
    }

    public List<Transporte> transportes() {
        return Collections.unmodifiableList(transportes);
    }
}
