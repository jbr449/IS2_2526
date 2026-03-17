package es.unican.is2.SegurosCommon;

import java.util.List;

/**
 * Interfaz DAO para los seguros.
 */
public interface ISegurosDAO {

    Seguro creaSeguro(Seguro s) throws DataAccessException;

    Seguro eliminaSeguro(long id) throws DataAccessException;

    Seguro actualizaSeguro(Seguro nuevo) throws DataAccessException;

    Seguro seguro(long id) throws DataAccessException;

    Seguro seguroPorMatricula(String matricula) throws DataAccessException;

    List<Seguro> seguros() throws DataAccessException;
}
