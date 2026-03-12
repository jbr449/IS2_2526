package es.unican.is2.negocio;

import es.unican.is2.modelo.Cliente;
import es.unican.is2.modelo.Seguro;
import es.unican.is2.persistencia.DataAccessException;
import es.unican.is2.persistencia.IClientesDAO;
import es.unican.is2.persistencia.ISegurosDAO;

/**
 * Clase que implementa la lógica de negocio de los seguros.
 */
public class GestionSeguros implements IGestionSeguros {

    private IClientesDAO clientesDAO;
    private ISegurosDAO segurosDAO;

    // Constructor que recibe las clases de acceso a la base de datos
    public GestionSeguros(IClientesDAO clientesDAO, ISegurosDAO segurosDAO) {
        this.clientesDAO = clientesDAO;
        this.segurosDAO = segurosDAO;
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        // 1. Buscamos al cliente en la base de datos
        Cliente c = clientesDAO.cliente(dni);
        if (c == null) {
            return null; // Si no existe, devolvemos null según indica la interfaz
        }

        // 2. Comprobamos si ya existe un seguro con esa matrícula
        Seguro existente = segurosDAO.seguroPorMatricula(s.getMatricula());
        if (existente != null) {
            throw new OperacionNoValida("Ya existe un seguro para esa matrícula");
        }

        // 3. Añadimos el seguro a la base de datos
        Seguro seguroCreado = segurosDAO.creaSeguro(s);
        
        // 4. Actualizamos el cliente con su nuevo seguro
        c.getSeguros().add(seguroCreado);
        clientesDAO.actualizaCliente(c);

        return seguroCreado;
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        // 1. Comprobamos que el cliente exista
        Cliente c = clientesDAO.cliente(dni);
        if (c == null) {
            return null; 
        }

        // 2. Comprobamos que el seguro exista
        Seguro s = segurosDAO.seguroPorMatricula(matricula);
        if (s == null) {
            return null;
        }

        // 3. Comprobamos si el seguro realmente pertenece a este cliente
        boolean pertenece = false;
        for (Seguro seguroCliente : c.getSeguros()) {
            if (seguroCliente.getMatricula().equals(matricula)) {
                pertenece = true;
                break;
            }
        }
        
        if (!pertenece) {
            throw new OperacionNoValida("El seguro no pertenece al cliente indicado");
        }

        // 4. Lo eliminamos de la base de datos
        return segurosDAO.eliminaSeguro(s.getId());
    }

    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        // 1. Buscamos el seguro por su matrícula
        Seguro s = segurosDAO.seguroPorMatricula(matricula);
        if (s == null) {
            return null; // Si no existe el seguro, devolvemos null
        }

        // 2. Le añadimos el conductor y actualizamos en la BBDD
        s.setConductorAdicional(conductor);
        return segurosDAO.actualizaSeguro(s);
    }
}