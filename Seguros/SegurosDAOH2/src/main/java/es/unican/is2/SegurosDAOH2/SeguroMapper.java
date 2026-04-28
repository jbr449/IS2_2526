package es.unican.is2.SegurosDAOH2;

import java.sql.ResultSet;
import java.sql.SQLException;
import es.unican.is2.SegurosCommon.Cobertura;
import es.unican.is2.SegurosCommon.DataAccessException;
import es.unican.is2.SegurosCommon.Seguro;

/**
 * Mapeador de filas SQL a objetos {@link Seguro}.
 */
public final class SeguroMapper {

    private SeguroMapper() {
    }

    public static Seguro toSeguro(ResultSet results) throws DataAccessException {
        try {
            Seguro seg = new Seguro();
            seg.setId(results.getLong("id"));
            seg.setMatricula(results.getString("matricula"));
            seg.setFechaInicio(results.getDate("fechaInicio").toLocalDate());
            seg.setCobertura(Cobertura.valueOf(results.getString("cobertura")));
            seg.setPotencia(results.getInt("potencia"));
            seg.setConductorAdicional(results.getString("conductorAdicional"));
            return seg;
        } catch (SQLException e) {
            throw new DataAccessException("Error mapeando seguro", e);
        }
    }
}
