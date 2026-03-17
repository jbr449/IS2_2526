package es.unican.is2.SegurosCommon;

import java.util.List;

/**
 * Interfaz DAO para clientes.
 */
public interface IClientesDAO {

    Cliente creaCliente(Cliente c) throws DataAccessException;

    Cliente cliente(String dni) throws DataAccessException;

    Cliente actualizaCliente(Cliente nuevo) throws DataAccessException;

    Cliente eliminaCliente(String dni) throws DataAccessException;

    List<Cliente> clientes() throws DataAccessException;
}
