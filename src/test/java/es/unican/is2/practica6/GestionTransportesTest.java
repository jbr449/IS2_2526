package es.unican.is2.practica6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class GestionTransportesTest {

    @Test
    void testAnhadeYBuscaConductor() {
        GestionTransportes sut = new GestionTransportes();

        assertNull(sut.buscaConductor("123123123X"));
        assertTrue(sut.anhadeConductor("123123123X", "Pepe", "Martinez", "Fernandez", "Direccion"));

        Conductor conductor = sut.buscaConductor("123123123X");
        assertEquals("Pepe", conductor.getNombre());
        assertEquals(1, sut.conductores().size());
        assertSame(conductor, sut.conductores().get(0));
    }

    @Test
    void testNoPermiteDniDuplicado() {
        GestionTransportes sut = new GestionTransportes();

        assertTrue(sut.anhadeConductor("123123123X", "Pepe", "Martinez", "Fernandez", "Direccion"));
        assertFalse(sut.anhadeConductor("123123123X", "Ana", "Lopez", "Sainz", "Otra direccion"));

        assertEquals(1, sut.conductores().size());
        assertEquals("Pepe", sut.buscaConductor("123123123X").getNombre());
    }

    @Test
    void testPropagaParametrosNoValidosAlCrearConductor() {
        GestionTransportes sut = new GestionTransportes();

        assertThrows(IllegalArgumentException.class,
                () -> sut.anhadeConductor(null, "Pepe", "Martinez", "Fernandez", "Direccion"));
    }

    @Test
    void testConductoresNoModificableDesdeFuera() {
        GestionTransportes sut = new GestionTransportes();
        sut.anhadeConductor("123123123X", "Pepe", "Martinez", "Fernandez", "Direccion");

        assertThrows(UnsupportedOperationException.class, () -> sut.conductores().clear());
        assertEquals(1, sut.conductores().size());
    }

    @Test
    void testMejoresConductoresSinConductores() {
        GestionTransportes sut = new GestionTransportes();
        assertTrue(sut.mejoresConductores().isEmpty());
    }

    @Test
    void testMejoresConductoresConEmpate() {
        GestionTransportes sut = new GestionTransportes();
        sut.anhadeConductor("11111111A", "Ana", "Lopez", "Diaz", "Direccion 1");
        sut.anhadeConductor("22222222B", "Luis", "Perez", "Ruiz", "Direccion 2");
        sut.anhadeConductor("33333333C", "Marta", "Gomez", "Soto", "Direccion 3");

        sut.buscaConductor("11111111A").anhadeTransporte(new Transporte(1, CategoriaTransporte.Personas, 1));
        sut.buscaConductor("22222222B").anhadeTransporte(new Transporte(1, CategoriaTransporte.Personas, 1));

        List<Conductor> mejores = sut.mejoresConductores();
        assertEquals(2, mejores.size());
        assertEquals("Ana", mejores.get(0).getNombre());
        assertEquals("Luis", mejores.get(1).getNombre());
    }
}
