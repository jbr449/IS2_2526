package es.unican.is2.SegurosDAOH2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.unican.is2.SegurosCommon.Cliente;
import es.unican.is2.SegurosCommon.Cobertura;
import es.unican.is2.SegurosCommon.DataAccessException;
import es.unican.is2.SegurosCommon.Seguro;

class DaoH2CoverageTest {

    private ClientesDAO clientesDAO;
    private SegurosDAO segurosDAO;

    @BeforeEach
    void setUp() {
        clientesDAO = new ClientesDAO();
        segurosDAO = new SegurosDAO();
    }

    @Test
    void debeRecuperarDatosIniciales() throws DataAccessException {
        Cliente cliente = clientesDAO.cliente("11111111A");
        Seguro seguro = segurosDAO.seguroPorMatricula("1111AAA");

        assertNotNull(cliente);
        assertNotNull(seguro);
        assertTrue(segurosDAO.seguros().size() >= 6);
        assertTrue(clientesDAO.clientes().size() >= 4);
    }

    @Test
    void debeCrearActualizarYEliminarSeguro() throws DataAccessException {
        Seguro nuevo = nuevoSeguro(uniqueMatricula());
        Seguro creado = segurosDAO.creaSeguro(nuevo);
        assertNotNull(creado);
        assertTrue(creado.getId() > 0);

        creado.setCobertura(Cobertura.TODO_RIESGO);
        creado.setPotencia(130);
        creado.setConductorAdicional("Conductor Test");
        Seguro actualizado = segurosDAO.actualizaSeguro(creado);

        assertNotNull(actualizado);
        assertEquals(Cobertura.TODO_RIESGO, actualizado.getCobertura());
        assertEquals(130, actualizado.getPotencia());
        assertEquals("Conductor Test", actualizado.getConductorAdicional());

        Seguro eliminado = segurosDAO.eliminaSeguro(actualizado.getId());
        assertNotNull(eliminado);
        assertNull(segurosDAO.seguro(actualizado.getId()));
    }

    @Test
    void debeCrearActualizarYEliminarClienteConAsignacionSeguro() throws DataAccessException {
        String dni = uniqueDni();
        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        cliente.setNombre("Cliente Test");
        cliente.setMinusvalia(false);

        Cliente creado = clientesDAO.creaCliente(cliente);
        assertNotNull(creado);
        assertNotNull(clientesDAO.cliente(dni));

        Seguro seguro = segurosDAO.creaSeguro(nuevoSeguro(uniqueMatricula()));
        Cliente paraActualizar = clientesDAO.cliente(dni);
        paraActualizar.setMinusvalia(true);
        paraActualizar.getSeguros().add(seguro);

        Cliente actualizado = clientesDAO.actualizaCliente(paraActualizar);
        assertNotNull(actualizado);
        assertTrue(actualizado.getMinusvalia());
        assertTrue(actualizado.getSeguros().stream()
                .anyMatch(s -> s.getMatricula().equals(seguro.getMatricula())));

        actualizado.setSeguros(new LinkedList<>());
        clientesDAO.actualizaCliente(actualizado);

        Cliente eliminado = clientesDAO.eliminaCliente(dni);
        assertNotNull(eliminado);
        assertNull(clientesDAO.cliente(dni));
    }

    private Seguro nuevoSeguro(String matricula) {
        Seguro seguro = new Seguro();
        seguro.setMatricula(matricula);
        seguro.setFechaInicio(LocalDate.now().minusDays(1));
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(95);
        return seguro;
    }

    private String uniqueMatricula() {
        return "T" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

    private String uniqueDni() {
        int numero = Math.abs(UUID.randomUUID().hashCode()) % 100000000;
        return String.format("%08dZ", numero);
    }
}
