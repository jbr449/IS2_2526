package es.unican.is2.practica6;

/**
 * Transporte realizado por un conductor.
 */
public class Transporte {

    private final double horas;
    private final int ton;
    private final int personas;
    private final CategoriaTransporte cat;

    /**
     * @param horas horas que ha durado el transporte
     * @param cat categoria del transporte
     * @param valor numero de personas o toneladas, segun la categoria
     */
    public Transporte(double horas, CategoriaTransporte cat, int valor) {
        if (horas <= 0 || valor <= 0 || cat == null) {
            throw new IllegalArgumentException();
        }
        this.horas = horas;
        this.cat = cat;
        if (cat == CategoriaTransporte.Personas) {
            this.personas = valor;
            this.ton = 0;
        } else {
            this.ton = valor;
            this.personas = 0;
        }
    }

    public double horas() {
        return horas;
    }

    public CategoriaTransporte categoria() {
        return cat;
    }

    public int ton() {
        return ton;
    }

    public int getPersonas() {
        return personas;
    }
}
