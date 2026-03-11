package es.unican.is2.negocio;

import es.unican.is2.modelo.Cliente;
import es.unican.is2.persistencia.DataAccessException;

public interface IInfoSeguros {

    /**
     * Obtiene un cliente a partir de su dni
     * @param dni DNI del cliente
     * @return el cliente
     * @throws DataAccessException si hay problemas de acceso a datos
     */
    public Cliente cliente(String dni) throws DataAccessException;

    /**
     * Calcula el total a pagar por los seguros de un cliente
     * @param dni DNI del cliente
     * @return total a pagar por los seguros
     * @throws DataAccessException si hay problemas de acceso a datos
     */
    public double totalSegurosCliente(String dni) throws DataAccessException;

}