package es.unican.is2.modelo;

import java.util.LinkedList;
import java.util.List;

/**
 * Clase que representa un cliente de la empresa de seguros
 * Un cliente se identifica por su dni
 */
public class Cliente {

    private String dni;
    private String nombre;  
    private boolean minusvalia;
    private List<Seguro> seguros = new LinkedList<Seguro>();
    
	/**
     * Retorna los seguros del cliente 
     */
    public List<Seguro> getSeguros() {
        return seguros;
    }
    
    /**
     * Asigna la lista de seguros
     */
    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros;
    }

    /**
     * Retorna el nombre del cliente.   
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el dni del cliente.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Asigna el dni del cliente
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    /**
     * Indica si el cliente es minusvalido
     */
    public boolean getMinusvalia() {
    	return minusvalia;
    }

    /**
     * Asigna la minusvalia del cliente
     * @param minusvalia
     */
     public void setMinusvalia(boolean minusvalia) {
        this.minusvalia = minusvalia;
    }
    
    /**
     * Calcula el total a pagar por el cliente por 
     * todos los seguros a su nombre
     */
    public double totalSeguros() {
        double total = 0.0;
        
        // 1. Sumamos el precio de todos los seguros que tiene este cliente
        for (Seguro s : seguros) {
            total += s.precio(); // Aquí llama a las matemáticas que acabas de pegar en Seguro.java
        }
        
        // 2. Si el cliente tiene minusvalía, le hacemos un 25% de descuento al total
        if (minusvalia) {
            total = total * 0.75;
        }
        
        // Devolvemos el total redondeado a 2 decimales
        return Math.round(total * 100.0) / 100.0;
    }

}