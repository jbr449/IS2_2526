package es.unican.is2.modelo;

import java.time.LocalDate;

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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Cobertura getCobertura() {
		return cobertura;
	}

	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;		
	}

    public int getPotencia() {
        return potencia;
    }

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public String getConductorAdicional() {
		return conductorAdicional;
	}

	public void setConductorAdicional(String conductorAdicional) {
		this.conductorAdicional = conductorAdicional;
	}
    
	/**
	 * Calcula el precio del seguro según las reglas de negocio
	 */
	public double precio() {
		LocalDate hoy = LocalDate.now();

		// Si se consulta el precio del seguro antes de su inicio el valor retornado será 0.
		if (hoy.isBefore(fechaInicio)) {
			return 0.0;
		}

		// 1. Nivel de cobertura contratado
		double precioBase = 0.0;
		if (cobertura == Cobertura.TODO_RIESGO) {
			precioBase = 1000.0;
		} else if (cobertura == Cobertura.TERCEROS_LUNAS) {
			precioBase = 600.0;
		} else if (cobertura == Cobertura.TERCEROS) {
			precioBase = 400.0;
		}

		// 2. Potencia del coche
		double multiplicadorPotencia = 1.0;
		if (potencia >= 90 && potencia <= 110) {
			multiplicadorPotencia = 1.05; // 5% de subida
		} else if (potencia > 110) {
			multiplicadorPotencia = 1.20; // 20% de subida
		}

		double precioFinal = precioBase * multiplicadorPotencia;

		// 3. Oferta del primer año (20% de descuento)
		if (hoy.isBefore(fechaInicio.plusYears(1))) {
			precioFinal = precioFinal * 0.80;
		}

		// Redondeamos a 2 decimales para que quede bonito como precio
		return Math.round(precioFinal * 100.0) / 100.0;
	}
	
}