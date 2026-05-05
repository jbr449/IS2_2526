import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConjuntoOrdenadoTest {

    private ConjuntoOrdenado<Integer> conjunto;

    @BeforeEach
    void setUp() {
        conjunto = new ConjuntoOrdenado<>();
    }

    @Test
    void addInsertaPrimerElementoYDevuelveTrue() {
        assertTrue(conjunto.add(10));
        assertEquals(1, conjunto.size());
        assertEquals(10, conjunto.get(0));
    }

    @Test
    void addInsertaEnOrdenNaturalAunqueSeAnhadaDesordenado() {
        conjunto.add(5);
        conjunto.add(1);
        conjunto.add(3);
        conjunto.add(2);
        conjunto.add(4);

        assertEquals(5, conjunto.size());
        assertEquals(1, conjunto.get(0));
        assertEquals(2, conjunto.get(1));
        assertEquals(3, conjunto.get(2));
        assertEquals(4, conjunto.get(3));
        assertEquals(5, conjunto.get(4));
    }

    @Test
    void addElementoDuplicadoDevuelveFalseYNoInserta() {
        assertTrue(conjunto.add(7));
        assertFalse(conjunto.add(7));
        assertEquals(1, conjunto.size());
        assertEquals(7, conjunto.get(0));
    }

    @Test
    void addConNuloLanzaNullPointerException() {
        assertThrows(NullPointerException.class, () -> conjunto.add(null));
    }

    @Test
    void getConIndiceNegativoLanzaIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.get(-1));
    }

    @Test
    void getConIndiceIgualAlTamanoLanzaIndexOutOfBoundsException() {
        conjunto.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.get(1));
    }

    @Test
    void removeConIndiceNegativoLanzaIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.remove(-1));
    }

    @Test
    void removeConIndiceIgualAlTamanoLanzaIndexOutOfBoundsException() {
        conjunto.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.remove(1));
    }

    @Test
    void removeEliminaYDevuelveElementoCorrecto() {
        conjunto.add(1);
        conjunto.add(3);
        conjunto.add(2);

        assertEquals(2, conjunto.remove(1));
        assertEquals(2, conjunto.size());
        assertEquals(1, conjunto.get(0));
        assertEquals(3, conjunto.get(1));
    }

    @Test
    void clearEliminaTodosLosElementos() {
        conjunto.add(1);
        conjunto.add(2);
        conjunto.add(3);

        conjunto.clear();

        assertEquals(0, conjunto.size());
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.get(0));
    }
}
