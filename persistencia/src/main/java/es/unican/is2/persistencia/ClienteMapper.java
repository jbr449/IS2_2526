package es.unican.is2.persistencia;

import es.unican.is2.modelo.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteMapper {

    public static Cliente toCliente(ResultSet rs) throws SQLException {

        Cliente result = new Cliente();

        result.setDni(rs.getString("dni"));
        result.setNombre(rs.getString("nombre"));
        result.setMinusvalia(rs.getBoolean("minusvalia"));

        return result;
    }
}