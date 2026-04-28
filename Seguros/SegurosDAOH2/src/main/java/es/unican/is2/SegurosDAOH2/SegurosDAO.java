package es.unican.is2.SegurosDAOH2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import es.unican.is2.SegurosCommon.DataAccessException;
import es.unican.is2.SegurosCommon.ISegurosDAO;
import es.unican.is2.SegurosCommon.Seguro;

/**
 * Implementación DAO para seguros sobre H2.
 */
public class SegurosDAO implements ISegurosDAO {

    @Override
    public Seguro creaSeguro(Seguro s) throws DataAccessException {
        if (seguroPorMatricula(s.getMatricula()) != null) {
            return null;
        }

        String conductor = s.getConductorAdicional() == null
                ? "NULL"
                : "'" + escape(s.getConductorAdicional()) + "'";

        String insertStatement = String.format(
                "INSERT INTO Seguros(matricula, fechaInicio, cobertura, potencia, conductorAdicional) VALUES ('%s', '%s', '%s', %d, %s)",
                escape(s.getMatricula()),
                s.getFechaInicio(),
                s.getCobertura(),
                s.getPotencia(),
                conductor);
        H2ServerConnectionManager.executeSqlStatement(insertStatement);
        return seguroPorMatricula(s.getMatricula());
    }

    @Override
    public Seguro eliminaSeguro(long id) throws DataAccessException {
        Seguro seguro = seguro(id);
        if (seguro == null) {
            return null;
        }
        H2ServerConnectionManager.executeSqlStatement("DELETE FROM Seguros WHERE id = " + id);
        return seguro;
    }

    @Override
    public Seguro actualizaSeguro(Seguro nuevo) throws DataAccessException {
        if (seguro(nuevo.getId()) == null) {
            return null;
        }

        String conductor = nuevo.getConductorAdicional() == null
                ? "NULL"
                : "'" + escape(nuevo.getConductorAdicional()) + "'";

        String statementText = String.format(
                "UPDATE Seguros SET matricula='%s', fechaInicio='%s', cobertura='%s', potencia=%d, conductorAdicional=%s WHERE id=%d",
                escape(nuevo.getMatricula()),
                nuevo.getFechaInicio(),
                nuevo.getCobertura(),
                nuevo.getPotencia(),
                conductor,
                nuevo.getId());
        H2ServerConnectionManager.executeSqlStatement(statementText);
        return seguro(nuevo.getId());
    }

    @Override
    public Seguro seguro(long id) throws DataAccessException {
        Connection con = H2ServerConnectionManager.getConnection();
        try (Statement statement = con.createStatement();
             ResultSet results = statement.executeQuery(
                     "SELECT id, matricula, fechaInicio, cobertura, potencia, conductorAdicional, cliente_FK FROM Seguros WHERE id = "
                             + id)) {

            if (results.next()) {
                return SeguroMapper.toSeguro(results);
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error recuperando seguro " + id, e);
        }
    }

    @Override
    public List<Seguro> seguros() throws DataAccessException {
        List<Seguro> seguros = new LinkedList<>();
        Connection con = H2ServerConnectionManager.getConnection();

        try (Statement statement = con.createStatement();
             ResultSet results = statement.executeQuery(
                     "SELECT id, matricula, fechaInicio, cobertura, potencia, conductorAdicional, cliente_FK FROM Seguros")) {

            while (results.next()) {
                seguros.add(SeguroMapper.toSeguro(results));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error recuperando seguros", e);
        }

        return seguros;
    }

    @Override
    public Seguro seguroPorMatricula(String matricula) throws DataAccessException {
        Connection con = H2ServerConnectionManager.getConnection();
        try (Statement statement = con.createStatement();
             ResultSet results = statement.executeQuery(
                     "SELECT id, matricula, fechaInicio, cobertura, potencia, conductorAdicional, cliente_FK FROM Seguros WHERE matricula = '"
                             + escape(matricula) + "'")) {

            if (results.next()) {
                return SeguroMapper.toSeguro(results);
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error recuperando seguro " + matricula, e);
        }
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("'", "''");
    }
}
