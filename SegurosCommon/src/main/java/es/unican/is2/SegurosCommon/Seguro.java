package es.unican.is2.SegurosCommon;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase que representa un seguro de coche.
 */
public class Seguro {

    private long id;
    private String matricula;
    private int potencia;
    private Cobertura cobertura;
    private LocalDate fechaInicio;
    private String conductorAdicional;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public Cobertura getCobertura() {
        return cobertura;
    }

    public void setCobertura(Cobertura cobertura) {
        this.cobertura = cobertura;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getConductorAdicional() {
        return conductorAdicional;
    }

    public void setConductorAdicional(String conductorAdicional) {
        this.conductorAdicional = conductorAdicional;
    }

    /**
     * Calcula el precio del seguro.
     */
    public double precio() {
        LocalDate hoy = LocalDate.now();

        if (fechaInicio == null || hoy.isBefore(fechaInicio)) {
            return 0.0;
        }

        double precioBase = 0.0;
        if (cobertura == Cobertura.TODO_RIESGO) {
            precioBase = 1000.0;
        } else if (cobertura == Cobertura.TERCEROS_LUNAS) {
            precioBase = 600.0;
        } else if (cobertura == Cobertura.TERCEROS) {
            precioBase = 400.0;
        }

        if (potencia >= 90 && potencia <= 110) {
            precioBase *= 1.05;
        } else if (potencia > 110) {
            precioBase *= 1.20;
        }

        if (hoy.isBefore(fechaInicio.plusYears(1))) {
            precioBase *= 0.80;
        }

        return Math.round(precioBase * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Seguro other)) {
            return false;
        }
        if (id > 0 && other.id > 0) {
            return id == other.id;
        }
        return Objects.equals(matricula, other.matricula);
    }

    @Override
    public int hashCode() {
        return id > 0 ? Long.hashCode(id) : Objects.hash(matricula);
    }
}
