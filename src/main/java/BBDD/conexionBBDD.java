package BBDD;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class conexionBBDD {

    /** Conexión activa a la base de datos. */
    private final Connection connection;

    public conexionBBDD() throws SQLException {
        Properties connConfig = new Properties();
        connConfig.setProperty("user", "root");
        connConfig.setProperty("password", "mypass");

        connection = DriverManager.getConnection(
                "jdbc:mariadb://127.0.0.1:33066/personas?serverTimezone=Europe/Madrid",
                connConfig
        );
        connection.setAutoCommit(true);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
    }
    public Connection getConnection() {
        return connection;
    }
    public Connection CloseConexion() throws SQLException {
        connection.close(); // Cierra la conexión
        return connection;   // Retorna la conexión cerrada (opcional)
    }
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = conexionBBDD.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                System.out.println("Archivo db.properties no encontrado en el classpath.");
                return null;
            }
            properties.load(input);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



/*
    public static void main(String[] args) {
        try {
            conexionBBDD conexion = new conexionBBDD();
            System.out.println("Conexión establecida exitosamente.");
            conexion.CloseConexion();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
 */
}
