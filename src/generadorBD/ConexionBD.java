package generadorBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/piscifactoria?rewriteBatchedStatements=true";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "abc123.";
    private static Connection conn = null;

    private ConexionBD() {
    };

    public static Connection obtenerConexion() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al crear la conexión" + e.getMessage());
            }
        }
        return conn;
    }

    public static void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al cerrar la conexión" + e.getMessage());
            }
        }
    }
}
