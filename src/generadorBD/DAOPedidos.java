package generadorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import inputHelper.EscritorHelper;

public class DAOPedidos {
    private static final String INSERT_PEDIDO = "INSERT INTO Pedido ( referencia, cliente_id, pez_id, cantidad_solicitada, cantidad_enviada) VALUES ( ?, ?, ?, ?, ?)";
    private static PreparedStatement insertPedidoStatement;
    private static final String GETINCOMPLETOS_PEDIDO = "SELECT pedido.referencia AS ref, cliente.nombre AS nombre, pez.nombre AS pez, pedido.cantidad_enviada AS enviado, pedido.cantidad_solicitada AS solicitado FROM pedido JOIN cliente ON pedido.cliente_id = cliente.id JOIN pez ON pez.id = pedido.pez_id where pedido.cantidad_solicitada!=pedido.cantidad_enviada  order by pez desc;";
    private static PreparedStatement getIncompletosStatement;
    private static final String GETCOMPLETOS_PEDIDO = "SELECT pedido.referencia AS ref, cliente.nombre AS nombre, pez.nombre AS pez, pedido.cantidad_enviada AS enviado, pedido.cantidad_solicitada AS solicitado FROM pedido JOIN cliente ON pedido.cliente_id = cliente.id JOIN pez ON pez.id = pedido.pez_id where pedido.cantidad_solicitada=pedido.cantidad_enviada order by pedido.id asc;";
    private static PreparedStatement getCompletosStatement;
    private static final String BORRAR_PEDIDO = "DELETE from pedido where id>0;";
    private static PreparedStatement borrarStatement;
    private static final String GET_PEDIDO = "SELECT referencia as ref, pez.nombre as nombre, cantidad_solicitada as solicitada, cantidad_enviada as enviados from pedido join pez on pedido.pez_id=pez.id where pedido.referencia=?;";
    private static final String GET_PEZ = "SELECT pez.nombre as nombre from pedido join pez on pedido.pez_id=pez.id where pedido.referencia=?;";
    private static PreparedStatement getPezStatement;
    private static final String GET_CANTIDAD = "SELECT cantidad_solicitada as solicitada, cantidad_enviada as enviados from pedido where pedido.referencia=?;";
    private static PreparedStatement getCantidadStatement;

    private static Connection conexion;
    private static int ref = 1;

    public DAOPedidos(Connection conectar) {
        conexion = conectar;
        try {
            insertPedidoStatement = conexion.prepareStatement(INSERT_PEDIDO);
            getIncompletosStatement = conexion.prepareStatement(GETINCOMPLETOS_PEDIDO);
            getCompletosStatement = conexion.prepareStatement(GETCOMPLETOS_PEDIDO);
            borrarStatement = conexion.prepareStatement(BORRAR_PEDIDO);
            getPezStatement = conexion.prepareStatement(GET_PEZ);
            getCantidadStatement = conexion.prepareStatement(GET_CANTIDAD);
            obtenerUltimaReferencia();
        } catch (SQLException e) {
            throw new RuntimeException("Error al preparar la consulta INSERT_PEDIDO", e);
        }
    }

    public static void agregarPedido() {
        try {

            Random random = new Random();
            int idCliente = random.nextInt(1, 10);
            int idPez = random.nextInt(1, 12);
            int cantidad = random.nextInt(10, 50);

            insertPedidoStatement.setInt(1, ref);
            insertPedidoStatement.setInt(2, idCliente);
            insertPedidoStatement.setInt(3, idPez);
            insertPedidoStatement.setInt(4, cantidad);
            insertPedidoStatement.setInt(5, 0);
            insertPedidoStatement.executeUpdate();
            EscritorHelper.getEscritorHelper("").addTrans("Generado el pedido con referencia " + ref);
            EscritorHelper.getEscritorHelper("").addLogs("Generado el pedido con referencia " + ref);
            ref++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cerrarStatement() {
        try {
            if (insertPedidoStatement != null) {
                insertPedidoStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void obtenerUltimaReferencia() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexion.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(referencia) AS ultima_referencia FROM Pedido");
            if (resultSet.next()) {
                ref = (resultSet.getInt("ultima_referencia") + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> mostrarIncompletosPedido() {
        try {
            ResultSet rs = getIncompletosStatement.executeQuery();
            ArrayList<Integer> numero = new ArrayList<>();
            // Imprimir resultados
            while (rs.next()) {
                int ref = rs.getInt("ref");
                String nombreCliente = rs.getString("nombre");
                String nombrePez = rs.getString("pez");
                int enviado = rs.getInt("enviado");
                int solicitado = rs.getInt("solicitado");

                // Calcular el porcentaje de cantidad enviada respecto a la solicitada
                double porcentaje = ((double) enviado / solicitado) * 100;

                // Formatear la salida con el formato requerido
                String output = String.format("[%d] Nombre cliente: %s %s %d/%d (%.2f%%)", ref,
                        nombreCliente,
                        nombrePez, enviado, solicitado, porcentaje);
                System.out.println(output);
                numero.add(ref);
            }
            rs.close();
            return numero;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void mostrarCompletosPedido() {
        try {
            ResultSet rs = getCompletosStatement.executeQuery();

            // Imprimir resultados
            while (rs.next()) {
                int ref = rs.getInt("ref");
                String nombreCliente = rs.getString("nombre");
                String nombrePez = rs.getString("pez");
                int enviado = rs.getInt("enviado");
                int solicitado = rs.getInt("solicitado");

                // Formatear la salida con el formato requerido
                String output = String.format("[%d] Nombre cliente: %s %s %d/%d", ref, nombreCliente, nombrePez,
                        enviado, solicitado);
                System.out.println(output);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void borrarDatos() {
        try {
            borrarStatement.executeUpdate();
            System.out.println("Datos borrados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tramitarPedido(int id_pedido) {

    }

    public static String getPez(int referencia) {
        try {
            getPezStatement.setInt(1, referencia);
            ResultSet rs = getPezStatement.executeQuery();
            String pez = "";
            while (rs.next()) {
                pez = rs.getString("nombre");
            }
            return pez;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getCantidad(int referencia) {

        int diferencia = 0;
        try {
            // Ejecutar la consulta para obtener la cantidad solicitada y enviada
            getCantidadStatement.setInt(1, referencia);
            ResultSet rs = getCantidadStatement.executeQuery();

            // Verificar si se encontró el pedido
            if (rs.next()) {
                int solicitada = rs.getInt("solicitada");
                int enviados = rs.getInt("enviados");

                diferencia = enviados - solicitada;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo del error, lanzar una excepción o devolver un valor predeterminado
        }
        return diferencia;
    }
}
