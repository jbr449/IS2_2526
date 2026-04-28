package es.unican.is2.SegurosCommon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void constructorConParametrosInicializaDatos() {
        Cliente cliente = new Cliente("12345678A", "Eva", true);

        assertEquals("12345678A", cliente.getDni());
        assertEquals("Eva", cliente.getNombre());
        assertTrue(cliente.getMinusvalia());
        assertTrue(cliente.getSeguros().isEmpty());
    }

    @Test
    void setSegurosConNullMantieneListaVacia() {
        Cliente cliente = new Cliente();

        cliente.setSeguros(null);

        assertTrue(cliente.getSeguros().isEmpty());
    }

    @Test
    void totalSegurosSinMinusvaliaSumaPrecios() {
        Seguro s1 = new Seguro("1111AAA", 100, Cobertura.TERCEROS, LocalDate.now().minusYears(2));
        Seguro s2 = new Seguro("1111BBB", 80, Cobertura.TODO_RIESGO, LocalDate.now().minusYears(2));
        Cliente cliente = new Cliente("12345678A", "Eva", false, Arrays.asList(s1, s2));

        assertEquals(1420.0, cliente.totalSeguros());
    }

    @Test
    void totalSegurosConMinusvaliaAplicaDescuento() {
        Seguro s1 = new Seguro("1111AAA", 100, Cobertura.TERCEROS, LocalDate.now().minusYears(2));
        Seguro s2 = new Seguro("1111BBB", 80, Cobertura.TODO_RIESGO, LocalDate.now().minusYears(2));
        Cliente cliente = new Cliente("12345678A", "Eva", true, Arrays.asList(s1, s2));

        assertEquals(1065.0, cliente.totalSeguros());
    }

    @Test
    void totalSegurosListaVaciaDaCero() {
        Cliente cliente = new Cliente("12345678A", "Eva", true, Collections.emptyList());

        assertEquals(0.0, cliente.totalSeguros());
    }

    @Test
    void totalSegurosIgnoraEntradasNulas() {
        Seguro s1 = new Seguro("1111AAA", 100, Cobertura.TERCEROS, LocalDate.now().minusYears(2));
        Cliente cliente = new Cliente("12345678A", "Eva", false, Arrays.asList(s1, null));

        assertEquals(420.0, cliente.totalSeguros());
    }

    @Test
    void equalsYHashCodeSeBasanEnDni() {
        Cliente a = new Cliente("12345678A", "Eva", false);
        Cliente b = new Cliente("12345678A", "Otra", true);
        Cliente c = new Cliente("87654321B", "Eva", false);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
