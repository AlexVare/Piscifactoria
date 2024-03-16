package generadorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import inputHelper.EscritorHelper;
import propiedades.AlmacenPropiedades;

public class GeneradorBD {

    private static PreparedStatement insertClienteStatement;
    private static PreparedStatement insertPezStatement;

    public GeneradorBD(Connection conexion) {
        try {
            inicializarPreparedStatements(conexion);
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null).addError("Error al inicializar los prepared statements" + e);
        }
    }

    public static void generarTablas(Connection conexion) {
        crearTablaPedido(conexion);
        crearTablaCliente(conexion);
        crearTablaPez(conexion);
    }

    private void inicializarPreparedStatements(Connection conexion) throws SQLException {
        String insertClienteSql = "INSERT INTO Cliente (nombre, NIF, telefono) VALUES (?, ?, ?)";
        insertClienteStatement = conexion.prepareStatement(insertClienteSql);

        String insertPezSql = "INSERT INTO Pez (nombre, nombre_cientifico) VALUES (?, ?)";
        insertPezStatement = conexion.prepareStatement(insertPezSql);
    }

    private static void crearTablaCliente(Connection conexion) {
        String sql = "CREATE TABLE IF NOT EXISTS Cliente ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(255),"
                + "NIF VARCHAR(20) UNIQUE,"
                + "telefono VARCHAR(20)"
                + ")";
        Statement statement = null;
        try {
            statement = conexion.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null).addError(e.getMessage());
        }
    }

    private static void crearTablaPedido(Connection conexion) {
        String sql = "CREATE TABLE IF NOT EXISTS Pedido ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "referencia INT UNIQUE,"
                + "cliente_id INT,"
                + "pez_id INT,"
                + "cantidad_solicitada INT,"
                + "cantidad_enviada INT,"
                + "FOREIGN KEY (cliente_id) REFERENCES Cliente(id),"
                + "FOREIGN KEY (pez_id) REFERENCES Pez(id)"
                + ")";
        Statement statement = null;
        try {
            statement = conexion.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null).addError(e.getMessage());
        }
    }

    private static void crearTablaPez(Connection conexion) {
        String sql = "CREATE TABLE IF NOT EXISTS Pez ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(100),"
                + "nombre_cientifico VARCHAR(100)"
                + ")";
        Statement statement = null;
        try {
            statement = conexion.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null).addError(e.getMessage());
        }
    }

    public static void agregarClientes() {
        try {
            for (int i = 0; i < 10; i++) {
                String nombre = "Cliente " + (i + 1);
                String nif = generarNIF();
                String telefono = "62345678" + i;

                insertClienteStatement.setString(1, nombre);
                insertClienteStatement.setString(2, nif);
                insertClienteStatement.setString(3, telefono);
                insertClienteStatement.addBatch();
            }
            insertClienteStatement.executeBatch();
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null)
                    .addError("Error al agregar clientes a la base de datos" + e.getMessage());
        }
    }

    public static void registrarPeces() {
        String[] peces = { AlmacenPropiedades.BESUGO.getNombre(), AlmacenPropiedades.PEJERREY.getNombre(),
                AlmacenPropiedades.CARPA.getNombre(), AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
                AlmacenPropiedades.CARPA_PLATEADA.getNombre(), AlmacenPropiedades.TILAPIA_NILO.getNombre(),
                AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
                AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
                AlmacenPropiedades.DORADA.getNombre(), AlmacenPropiedades.TRUCHA_ARCOIRIS.getNombre() };
        String[] pecesC = { AlmacenPropiedades.BESUGO.getCientifico(), AlmacenPropiedades.PEJERREY.getCientifico(),
                AlmacenPropiedades.CARPA.getCientifico(), AlmacenPropiedades.SALMON_CHINOOK.getCientifico(),
                AlmacenPropiedades.CARPA_PLATEADA.getCientifico(), AlmacenPropiedades.TILAPIA_NILO.getCientifico(),
                AlmacenPropiedades.ROBALO.getCientifico(), AlmacenPropiedades.CABALLA.getCientifico(),
                AlmacenPropiedades.ARENQUE_ATLANTICO.getCientifico(), AlmacenPropiedades.SARGO.getCientifico(),
                AlmacenPropiedades.DORADA.getCientifico(), AlmacenPropiedades.TRUCHA_ARCOIRIS.getCientifico() };
        try {
            for (int i = 0; i < peces.length; i++) {

                insertPezStatement.setString(1, peces[i]);
                insertPezStatement.setString(2, pecesC[i]);
                insertPezStatement.addBatch();
            }
            insertPezStatement.executeBatch();
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null)
                    .addError("Error al agregar peces a la base de datos" + e.getMessage());
        }
    }

    private static String generarNIF() {
        // Generar un NIF válido siguiendo las normas de España
        char[] letrasNIF = "TRWAGMYFPDXBNJZSQVHLCKE".toCharArray();
        String nif = "";

        // Seleccionar una letra aleatoria para el NIF
        nif = nif + letrasNIF[(int) (Math.random() * 23)];

        // Generar 7 dígitos aleatorios
        for (int i = 0; i < 7; i++) {
            nif = nif + (int) (Math.random() * 10);
        }

        // Calcular la letra de control
        int suma = 0;
        for (int i = 0; i < 8; i++) {
            int digito = Character.getNumericValue(nif.charAt(i));
            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
        }
        int resto = suma % 10;
        int control = (10 - resto) % 10;
        nif = nif + control;

        return nif;
    }

    public static void cerrarStatements() {
        try {
            if (insertClienteStatement != null) {
                insertClienteStatement.close();
            }
            if (insertPezStatement != null) {
                insertPezStatement.close();
            }
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null).addError("Error al cerrar los statements" + e.getMessage());
        }
    }

    public static void verificarYAgregarDatos(Connection conexion) {
        if (tablasVacias(conexion)) {
            agregarClientes();
            registrarPeces();
        }
    }

    private static boolean tablasVacias(Connection conexion) {
        try {
            Statement statement = conexion.createStatement();
            ResultSet resultSetClientes = statement.executeQuery("SELECT COUNT(*) FROM Cliente");
            resultSetClientes.next();
            int countClientes = resultSetClientes.getInt(1);
            resultSetClientes.close();

            ResultSet resultSetPeces = statement.executeQuery("SELECT COUNT(*) FROM Pez");
            resultSetPeces.next();
            int countPeces = resultSetPeces.getInt(1);
            resultSetPeces.close();

            statement.close();

            return countClientes == 0 && countPeces == 0;
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper(null)
                    .addError("Error al comprobar si las tablas están vacías" + e.getMessage());
            return false;
        }
    }
}
