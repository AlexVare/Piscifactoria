package generadorBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import inputHelper.EscritorHelper;

/**
 * Clase para establecer y cerrar la conexión con la base de datos MySQL.
 */
public class ConexionBD {
    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/piscifactoria?rewriteBatchedStatements=true";
    // Nombre de usuario de la base de datos
    private static final String USUARIO = "root";
    // Contraseña de la base de datos
    private static final String CONTRASENA = "abc123.";
    // Objeto de conexión a la base de datos
    private static Connection conn = null;

    // Constructor privado para evitar instanciación externa
    private ConexionBD() {
    };

    /**
     * Método para obtener la conexión a la base de datos.
     * 
     * @return La conexión a la base de datos.
     */
    public static Connection obtenerConexion() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            } catch (SQLException e) {
                EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
                System.out.println("Error al crear la conexión" + e.getMessage());
            }
        }
        return conn;
    }

    /**
     * Método para cerrar la conexión a la base de datos.
     */
    public static void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
                System.out.println("Error al cerrar la conexión" + e.getMessage());
            }
        }
    }
}
