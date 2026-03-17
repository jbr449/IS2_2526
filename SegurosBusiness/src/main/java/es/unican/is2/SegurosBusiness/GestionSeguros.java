package es.unican.is2.SegurosBusiness;

import es.unican.is2.SegurosCommon.Cliente;
import es.unican.is2.SegurosCommon.DataAccessException;
import es.unican.is2.SegurosCommon.IClientesDAO;
import es.unican.is2.SegurosCommon.IGestionClientes;
import es.unican.is2.SegurosCommon.IGestionSeguros;
import es.unican.is2.SegurosCommon.IInfoSeguros;
import es.unican.is2.SegurosCommon.ISegurosDAO;
import es.unican.is2.SegurosCommon.OperacionNoValida;
import es.unican.is2.SegurosCommon.Seguro;

/**
 * Implementación de la lógica de negocio de seguros.
 */
public class GestionSeguros implements IGestionClientes, IGestionSeguros, IInfoSeguros {

    private final IClientesDAO clientesDAO;
    private final ISegurosDAO segurosDAO;

    public GestionSeguros(IClientesDAO clientesDAO, ISegurosDAO segurosDAO) {
        this.clientesDAO = clientesDAO;
        this.segurosDAO = segurosDAO;
    }

    @Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        if (c == null || c.getDni() == null) {
            return null;
        }
        if (clientesDAO.cliente(c.getDni()) != null) {
            return null;
        }
        return clientesDAO.creaCliente(c);
    }

    @Override
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
        Cliente cliente = clientesDAO.cliente(dni);
        if (cliente == null) {
            return null;
        }
        if (!cliente.getSeguros().isEmpty()) {
            throw new OperacionNoValida("El cliente tiene seguros a su nombre");
        }
        return clientesDAO.eliminaCliente(dni);
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        Cliente cliente = clientesDAO.cliente(dni);
        if (cliente == null) {
            return null;
        }
        if (segurosDAO.seguroPorMatricula(s.getMatricula()) != null) {
            throw new OperacionNoValida("Ya existe un seguro con esa matrícula");
        }

        Seguro seguroCreado = segurosDAO.creaSeguro(s);
        cliente.getSeguros().add(seguroCreado);
        clientesDAO.actualizaCliente(cliente);
        return segurosDAO.seguroPorMatricula(s.getMatricula());
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        Cliente cliente = clientesDAO.cliente(dni);
        if (cliente == null) {
            return null;
        }

        Seguro seguro = segurosDAO.seguroPorMatricula(matricula);
        if (seguro == null) {
            return null;
        }

        boolean pertenece = cliente.getSeguros().stream()
                .anyMatch(s -> matricula.equals(s.getMatricula()));
        if (!pertenece) {
            throw new OperacionNoValida("El seguro no pertenece al cliente indicado");
        }

        return segurosDAO.eliminaSeguro(seguro.getId());
    }

    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        Seguro seguro = segurosDAO.seguroPorMatricula(matricula);
        if (seguro == null) {
            return null;
        }
        seguro.setConductorAdicional(conductor);
        return segurosDAO.actualizaSeguro(seguro);
    }

    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        return clientesDAO.cliente(dni);
    }

    @Override
    public double totalSegurosCliente(String dni) throws DataAccessException {
        Cliente cliente = clientesDAO.cliente(dni);
        return cliente == null ? 0.0 : cliente.totalSeguros();
    }
}
