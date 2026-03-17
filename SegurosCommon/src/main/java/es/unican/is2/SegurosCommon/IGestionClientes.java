package es.unican.is2.SegurosCommon;

/**
 * Interfaz de negocio para gestionar los clientes de la empresa.
 */
public interface IGestionClientes {

    Cliente nuevoCliente(Cliente c) throws DataAccessException;

    Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException;
}
