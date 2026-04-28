package es.unican.is2.SegurosDAOH2;

import es.unican.is2.SegurosCommon.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * Clase que gestiona el acceso a la base de datos H2 en memoria.
 */
public final class H2ServerConnectionManager {

    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_CREDENTIAL = UUID.randomUUID().toString();

    private static Connection connection;
    private static boolean initialized;

    private H2ServerConnectionManager() {
    }

    public static Connection getConnection() throws DataAccessException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_CREDENTIAL);
                cargaDatos();
            } catch (SQLException e) {
                throw new DataAccessException("Error al conectar con la base de datos", e);
            }
        }
        return connection;
    }

    public static void cargaDatos() throws DataAccessException {
        if (initialized) {
            return;
        }

        try (Statement stm = getConnection().createStatement()) {
            String sql = """
                    CREATE TABLE Clientes (
                        dni CHAR(9) NOT NULL,
                        nombre VARCHAR(100) NOT NULL,
                        minusvalia BOOLEAN NOT NULL,
                        PRIMARY KEY(dni)
                    )
                    """;
            stm.execute(sql);

            sql = """
                    CREATE TABLE Seguros (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        matricula CHAR(7) NOT NULL,
                        fechaInicio DATE NOT NULL,
                        cobertura VARCHAR(100) NOT NULL,
                        potencia INT NOT NULL,
                        conductorAdicional VARCHAR(100),
                        cliente_FK CHAR(9),
                        PRIMARY KEY(id),
                        FOREIGN KEY(cliente_FK) REFERENCES Clientes(dni)
                    )
                    """;
            stm.execute(sql);

            stm.executeUpdate("INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('11111111A', 'Juan', false)");
            stm.executeUpdate("INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('22222222A', 'Ana', false)");
            stm.executeUpdate("INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('33333333A', 'Luis', true)");
            stm.executeUpdate("INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('44444444A', 'Pepe', false)");

            stm.executeUpdate("INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) VALUES ('1111AAA', '2002-01-15', 'TERCEROS', 15, '11111111A')");
            stm.executeUpdate("INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, conductorAdicional, cliente_FK) VALUES ('1111BBB', '2016-05-20', 'TODO_RIESGO', 20, 'Pepe', '11111111A')");
            stm.executeUpdate("INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) VALUES ('1111CCC', '2022-05-21', 'TERCEROS', 100, '11111111A')");
            stm.executeUpdate("INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) VALUES ('2222AAA', '2010-06-01', 'TERCEROS_LUNAS', 25, '22222222A')");
            stm.executeUpdate("INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) VALUES ('4444AAA', '2024-01-02', 'TERCEROS', 40, '44444444A')");
            stm.executeUpdate("INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) VALUES ('4444BBB', '2024-01-02', 'TERCEROS_LUNAS', 300, '44444444A')");

            initialized = true;
        } catch (SQLException e) {
            throw new DataAccessException("Error al crear la base de datos", e);
        }
    }

    public static void executeSqlStatement(String stringStatement) throws DataAccessException {
        try (Statement stm = getConnection().createStatement()) {
            stm.execute(stringStatement);
        } catch (SQLException e) {
            throw new DataAccessException("Error ejecutando SQL", e);
        }
    }
}
