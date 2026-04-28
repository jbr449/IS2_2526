package es.unican.is2.SegurosCommon;

/**
 * Interfaz de negocio para gestionar los seguros de la empresa.
 */
public interface IGestionSeguros {

    Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException;

    Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException;

    Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException;
}
