package es.unican.is2.SegurosCommon;

@SuppressWarnings("serial")
public class OperacionNoValida extends RuntimeException {

    public OperacionNoValida(String mensaje) {
        super(mensaje);
    }
}
