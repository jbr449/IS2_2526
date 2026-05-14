package es.unican.is2.practica6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ConductorTest {

    @Test
    void testConstructor() {
        Conductor sut = new Conductor("123123123X", "Pepe", "Martinez", "Fernandez",
                "Avda. de los Castros s/n");
        assertEquals("123123123X", sut.dni());
        assertEquals("123123123X", sut.getDni());
        assertEquals("Pepe", sut.getNombre());
        assertEquals("Martinez", sut.getApellido1());
        assertEquals("Fernandez", sut.apellido2());
        assertEquals("Fernandez", sut.getApellido2());
        assertEquals("Avda. de los Castros s/n", sut.getDire());
        assertEquals("Avda. de los Castros s/n", sut.getDireccion());

        sut = new Conductor("123123123X", "Pepe", "Martinez", null, "Avda. de los Castros s/n");
        assertNull(sut.apellido2());
    }

    @Test
    void testConstructorParametrosNoValidos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor(null, "Pepe", "Martinez", "Fernandez", "Direccion"));
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor("123123123X", null, "Martinez", "Fernandez", "Direccion"));
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor("123123123X", "Pepe", null, "Fernandez", "Direccion"));
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor("123123123X", "Pepe", "Martinez", "Fernandez", null));
    }

    @Test
    void testSueldoYAnhadeTransporte() {
        Conductor sut = new Conductor("123123123X", "Pepe", "Martinez", "Fernandez",
                "Avda. de los Castros s/n");

        assertEquals(700.0, sut.sueldo());

        sut.anhadeTransporte(new Transporte(1, CategoriaTransporte.Personas, 1));
        assertEquals(705.5, sut.sueldo());

        sut.anhadeTransporte(new Transporte(10, CategoriaTransporte.Personas, 9));
        assertEquals(760.5, sut.sueldo());

        sut.anhadeTransporte(new Transporte(1, CategoriaTransporte.Personas, 10));
        assertEquals(766.5, sut.sueldo());

        sut.anhadeTransporte(new Transporte(10, CategoriaTransporte.Personas, 20));
        assertEquals(826.5, sut.sueldo());

        sut.anhadeTransporte(new Transporte(1, CategoriaTransporte.Mercancias, 1));
        assertEquals(833.5, sut.sueldo());

        sut.anhadeTransporte(new Transporte(10, CategoriaTransporte.MercanciasPeligrosas, 100));
        assertEquals(1133.5, sut.sueldo());
    }

    @Test
    void testAnhadeTransporteNoValido() {
        Conductor sut = new Conductor("123123123X", "Pepe", "Martinez", "Fernandez", "Direccion");
        assertThrows(IllegalArgumentException.class, () -> sut.anhadeTransporte(null));
    }

    @Test
    void testTransportesNoModificableDesdeFuera() {
        Conductor sut = new Conductor("123123123X", "Pepe", "Martinez", "Fernandez", "Direccion");
        sut.anhadeTransporte(new Transporte(1, CategoriaTransporte.Personas, 1));

        assertEquals(1, sut.transportes().size());
        assertThrows(UnsupportedOperationException.class, () -> sut.transportes().clear());
        assertTrue(sut.sueldo() > 700.0);
    }
}
