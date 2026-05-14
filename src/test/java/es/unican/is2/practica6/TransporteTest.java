package es.unican.is2.practica6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TransporteTest {

    @Test
    void testConstructor() {
        Transporte sut = new Transporte(1, CategoriaTransporte.Mercancias, 1);
        assertEquals(1, sut.horas());
        assertEquals(CategoriaTransporte.Mercancias, sut.categoria());
        assertEquals(1, sut.ton());
        assertEquals(0, sut.getPersonas());

        sut = new Transporte(10, CategoriaTransporte.MercanciasPeligrosas, 1000);
        assertEquals(10, sut.horas());
        assertEquals(CategoriaTransporte.MercanciasPeligrosas, sut.categoria());
        assertEquals(1000, sut.ton());
        assertEquals(0, sut.getPersonas());

        sut = new Transporte(10, CategoriaTransporte.Personas, 10);
        assertEquals(10, sut.horas());
        assertEquals(CategoriaTransporte.Personas, sut.categoria());
        assertEquals(10, sut.getPersonas());
        assertEquals(0, sut.ton());
    }

    @Test
    void testConstructorParametrosNoValidos() {
        assertThrows(IllegalArgumentException.class, () -> new Transporte(0, CategoriaTransporte.Mercancias, 1));
        assertThrows(IllegalArgumentException.class, () -> new Transporte(-1, CategoriaTransporte.Mercancias, 1));
        assertThrows(IllegalArgumentException.class, () -> new Transporte(10, CategoriaTransporte.Mercancias, 0));
        assertThrows(IllegalArgumentException.class, () -> new Transporte(10, null, 10));
    }
}
