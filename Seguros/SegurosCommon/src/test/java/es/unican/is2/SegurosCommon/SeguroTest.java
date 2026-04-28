package es.unican.is2.SegurosCommon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class SeguroTest {

    @Test
    void constructorConParametrosInicializaDatos() {
        Seguro seguro = new Seguro("1111AAA", 100, Cobertura.TERCEROS, LocalDate.now().minusDays(1), "Pepe");

        assertEquals("1111AAA", seguro.getMatricula());
        assertEquals(100, seguro.getPotencia());
        assertEquals(Cobertura.TERCEROS, seguro.getCobertura());
        assertEquals("Pepe", seguro.getConductorAdicional());
    }

    @Test
    void precioEsCeroSiAunNoHaEmpezado() {
        Seguro seguro = new Seguro("1111AAA", 100, Cobertura.TERCEROS, LocalDate.now().plusDays(1));

        assertEquals(0.0, seguro.precio());
    }

    @Test
    void precioEsCeroSinCobertura() {
        Seguro seguro = new Seguro("1111AAA", 100, null, LocalDate.now().minusDays(1));

        assertEquals(0.0, seguro.precio());
    }

    @Test
    void precioBaseDependeDeCobertura() {
        Seguro terceros = new Seguro("1111AAA", 80, Cobertura.TERCEROS, LocalDate.now().minusYears(2));
        Seguro lunas = new Seguro("1111BBB", 80, Cobertura.TERCEROS_LUNAS, LocalDate.now().minusYears(2));
        Seguro riesgo = new Seguro("1111CCC", 80, Cobertura.TODO_RIESGO, LocalDate.now().minusYears(2));

        assertEquals(400.0, terceros.precio());
        assertEquals(600.0, lunas.precio());
        assertEquals(1000.0, riesgo.precio());
    }

    @Test
    void precioAplicaRecargoCincoPorCientoEntreNoventaYCientoDiez() {
        Seguro minimo = new Seguro("1111AAA", 90, Cobertura.TERCEROS, LocalDate.now().minusYears(2));
        Seguro maximo = new Seguro("1111BBB", 110, Cobertura.TERCEROS, LocalDate.now().minusYears(2));

        assertEquals(420.0, minimo.precio());
        assertEquals(420.0, maximo.precio());
    }

    @Test
    void precioAplicaRecargoVeintePorCientoPorEncimaDeCientoDiez() {
        Seguro seguro = new Seguro("1111AAA", 111, Cobertura.TERCEROS, LocalDate.now().minusYears(2));

        assertEquals(480.0, seguro.precio());
    }

    @Test
    void precioAplicaOfertaDurantePrimerAnho() {
        Seguro seguro = new Seguro("1111AAA", 100, Cobertura.TERCEROS_LUNAS, LocalDate.now().minusDays(200));

        assertEquals(504.0, seguro.precio());
    }

    @Test
    void precioNoAplicaOfertaDesdeElSegundoAnho() {
        Seguro seguro = new Seguro("1111AAA", 100, Cobertura.TERCEROS_LUNAS, LocalDate.now().minusYears(1));

        assertEquals(630.0, seguro.precio());
    }

    @Test
    void equalsConIdPositivoComparaId() {
        Seguro a = new Seguro("1111AAA", 80, Cobertura.TERCEROS, LocalDate.now().minusDays(1));
        Seguro b = new Seguro("9999ZZZ", 120, Cobertura.TODO_RIESGO, LocalDate.now().minusDays(1));
        a.setId(7L);
        b.setId(7L);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsSinIdComparaMatricula() {
        Seguro a = new Seguro("1111AAA", 80, Cobertura.TERCEROS, LocalDate.now().minusDays(1));
        Seguro b = new Seguro("1111AAA", 120, Cobertura.TODO_RIESGO, LocalDate.now().minusDays(1));
        Seguro c = new Seguro("2222BBB", 80, Cobertura.TERCEROS, LocalDate.now().minusDays(1));

        assertEquals(a, b);
        assertNotEquals(a, c);
    }
}
