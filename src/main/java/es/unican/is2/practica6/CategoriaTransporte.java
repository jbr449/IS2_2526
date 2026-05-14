package es.unican.is2.practica6;

public enum CategoriaTransporte {
    Mercancias,
    MercanciasPeligrosas,
    Mercancias_Peligrosas,
    Personas;

    public boolean esMercanciaPeligrosa() {
        return this == MercanciasPeligrosas || this == Mercancias_Peligrosas;
    }
}
