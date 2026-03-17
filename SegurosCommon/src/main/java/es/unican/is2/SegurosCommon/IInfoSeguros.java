package es.unican.is2.SegurosCommon;

/**
 * Interfaz de consulta de información de clientes y seguros.
 */
public interface IInfoSeguros {

    Cliente cliente(String dni) throws DataAccessException;

    double totalSegurosCliente(String dni) throws DataAccessException;
}
