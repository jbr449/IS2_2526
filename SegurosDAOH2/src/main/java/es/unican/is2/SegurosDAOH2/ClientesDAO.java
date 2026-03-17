package es.unican.is2.SegurosDAOH2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import es.unican.is2.SegurosCommon.Cliente;
import es.unican.is2.SegurosCommon.DataAccessException;
import es.unican.is2.SegurosCommon.IClientesDAO;
import es.unican.is2.SegurosCommon.Seguro;

/**
 * Implementación DAO para clientes sobre H2.
 */
public class ClientesDAO implements IClientesDAO {

    @Override
    public Cliente creaCliente(Cliente c) throws DataAccessException {
        if (cliente(c.getDni()) != null) {
            return null;
        }

        String insertStatement = String.format(
                "INSERT INTO Clientes(dni, nombre, minusvalia) VALUES ('%s','%s',%s)",
                escape(c.getDni()),
                escape(c.getNombre()),
                c.getMinusvalia());

        H2ServerConnectionManager.executeSqlStatement(insertStatement);
        return cliente(c.getDni());
    }

    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        Cliente result = null;
        Connection con = H2ServerConnectionManager.getConnection();

        try (Statement statement = con.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM Clientes WHERE dni = '" + escape(dni) + "'")) {

            if (results.next()) {
                result = procesaCliente(con, results);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error recuperando cliente " + dni, e);
        }

        return result;
    }

    @Override
    public Cliente actualizaCliente(Cliente nuevo) throws DataAccessException {
        Cliente old = cliente(nuevo.getDni());
        if (old == null) {
            return null;
        }

        String statementText = String.format(
                "UPDATE Clientes SET nombre='%s', minusvalia=%s WHERE dni='%s'",
                escape(nuevo.getNombre()),
                nuevo.getMinusvalia(),
                escape(nuevo.getDni()));
        H2ServerConnectionManager.executeSqlStatement(statementText);

        Set<String> segurosAntiguos = old.getSeguros().stream()
                .map(Seguro::getMatricula)
                .collect(Collectors.toSet());
        Set<String> segurosNuevos = nuevo.getSeguros().stream()
                .map(Seguro::getMatricula)
                .collect(Collectors.toSet());

        for (Seguro seguro : nuevo.getSeguros()) {
            if (!segurosAntiguos.contains(seguro.getMatricula())) {
                String sql = String.format(
                        "UPDATE Seguros SET cliente_FK = '%s' WHERE id = %d",
                        escape(nuevo.getDni()),
                        seguro.getId());
                H2ServerConnectionManager.executeSqlStatement(sql);
            }
        }

        for (Seguro seguro : old.getSeguros()) {
            if (!segurosNuevos.contains(seguro.getMatricula())) {
                String sql = String.format(
                        "UPDATE Seguros SET cliente_FK = NULL WHERE id = %d",
                        seguro.getId());
                H2ServerConnectionManager.executeSqlStatement(sql);
            }
        }

        return cliente(nuevo.getDni());
    }

    @Override
    public Cliente eliminaCliente(String dni) throws DataAccessException {
        Cliente cliente = cliente(dni);
        if (cliente == null) {
            return null;
        }

        H2ServerConnectionManager.executeSqlStatement(
                "DELETE FROM Clientes WHERE dni = '" + escape(dni) + "'");
        return cliente;
    }

    @Override
    public List<Cliente> clientes() throws DataAccessException {
        List<Cliente> clientes = new LinkedList<>();
        Connection con = H2ServerConnectionManager.getConnection();

        try (Statement statement = con.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM Clientes")) {

            while (results.next()) {
                clientes.add(procesaCliente(con, results));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error recuperando clientes", e);
        }

        return clientes;
    }

    private Cliente procesaCliente(Connection con, ResultSet results)
            throws SQLException, DataAccessException {
        Cliente result = ClienteMapper.toCliente(results);

        try (Statement statement = con.createStatement();
             ResultSet seguros = statement.executeQuery(
                     "SELECT * FROM Seguros WHERE cliente_FK = '" + escape(result.getDni()) + "'")) {

            while (seguros.next()) {
                result.getSeguros().add(SeguroMapper.toSeguro(seguros));
            }
        }

        return result;
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("'", "''");
    }
}
