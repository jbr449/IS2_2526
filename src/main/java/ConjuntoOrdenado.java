import java.util.ArrayList;
import java.util.List;

import es.unican.is2.adt.IConjuntoOrdenado;

/**
 * Implementacion de un conjunto ordenado por el orden natural de sus elementos.
 */
public class ConjuntoOrdenado<E extends Comparable<E>> implements IConjuntoOrdenado<E> {

    private final List<E> lista = new ArrayList<>();

    @Override
    public E get(int indice) {
        return lista.get(indice);
    }

    @Override
    public boolean add(E elemento) {
        if (elemento == null) {
            throw new NullPointerException();
        }

        int indice = 0;
        while (indice < lista.size() && elemento.compareTo(lista.get(indice)) > 0) {
            indice++;
        }

        if (indice < lista.size() && elemento.compareTo(lista.get(indice)) == 0) {
            return false;
        }

        lista.add(indice, elemento);
        return true;
    }

    @Override
    public E remove(int indice) {
        return lista.remove(indice);
    }

    @Override
    public int size() {
        return lista.size();
    }

    @Override
    public void clear() {
        lista.clear();
    }
}
