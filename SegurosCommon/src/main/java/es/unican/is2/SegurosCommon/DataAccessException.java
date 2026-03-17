package es.unican.is2.SegurosCommon;

/**
 * Excepción de acceso a datos.
 */
public class DataAccessException extends Exception {

    private static final long serialVersionUID = 1L;

    public DataAccessException() {
        super();
    }

    public DataAccessException(String mensaje) {
        super(mensaje);
    }

    public DataAccessException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
