package es.unican.is2.SegurosCommon;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un cliente de la empresa de seguros.
 */
public class Cliente {

    private String dni;
    private String nombre;
    private boolean minusvalia;
    private List<Seguro> seguros = new LinkedList<>();

    public Cliente() {
    }

    public Cliente(String dni, String nombre, boolean minusvalia) {
        this(dni, nombre, minusvalia, new LinkedList<>());
    }

    public Cliente(String dni, String nombre, boolean minusvalia, List<Seguro> seguros) {
        this.dni = dni;
        this.nombre = nombre;
        this.minusvalia = minusvalia;
        setSeguros(seguros);
    }

    public List<Seguro> getSeguros() {
        return seguros;
    }

    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros == null ? new LinkedList<>() : new LinkedList<>(seguros);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean getMinusvalia() {
        return minusvalia;
    }

    public void setMinusvalia(boolean minusvalia) {
        this.minusvalia = minusvalia;
    }

    /**
     * Calcula el total a pagar por el cliente por todos los seguros a su nombre.
     */
    public double totalSeguros() {
        double total = 0.0;

        for (Seguro seguro : seguros) {
            if (seguro != null) {
                total += seguro.precio();
            }
        }

        if (minusvalia) {
            total *= 0.75;
        }

        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cliente other)) {
            return false;
        }
        return Objects.equals(dni, other.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }
}
