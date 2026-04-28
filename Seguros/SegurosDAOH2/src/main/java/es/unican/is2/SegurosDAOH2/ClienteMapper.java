package es.unican.is2.SegurosDAOH2;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.unican.is2.SegurosCommon.Cliente;

/**
 * Mapeador de filas SQL a objetos {@link Cliente}.
 */
public final class ClienteMapper {

    private ClienteMapper() {
    }

    public static Cliente toCliente(ResultSet rs) throws SQLException {
        Cliente result = new Cliente();
        result.setDni(rs.getString("dni"));
        result.setNombre(rs.getString("nombre"));
        result.setMinusvalia(rs.getBoolean("minusvalia"));
        return result;
    }
}
