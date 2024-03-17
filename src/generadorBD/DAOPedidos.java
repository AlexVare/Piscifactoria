package generadorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import inputHelper.EscritorHelper;
import rewards.Crear;

/**
 * Esta clase proporciona métodos para interactuar con la tabla 'Pedido' en la
 * base de datos.
 * Permite agregar pedidos, obtener información sobre pedidos incompletos y
 * completos,
 * borrar datos de la tabla y más.
 */
public class DAOPedidos {
    // Sentencias SQL para manipular la tabla 'Pedido'

    // Insertar un nuevo pedido en la tabla
    private static final String INSERT_PEDIDO = "INSERT INTO Pedido ( referencia, cliente_id, pez_id, cantidad_solicitada, cantidad_enviada) VALUES ( ?, ?, ?, ?, ?)";
    private static PreparedStatement insertPedidoStatement;
    // Obtener pedidos incompletos
    private static final String GETINCOMPLETOS_PEDIDO = "SELECT pedido.referencia AS ref, cliente.nombre AS nombre, pez.nombre AS pez, pedido.cantidad_enviada AS enviado, pedido.cantidad_solicitada AS solicitado FROM pedido JOIN cliente ON pedido.cliente_id = cliente.id JOIN pez ON pez.id = pedido.pez_id where pedido.cantidad_solicitada!=pedido.cantidad_enviada  order by pez desc;";
    private static PreparedStatement getIncompletosStatement;
    // Obtener pedidos completos
    private static final String GETCOMPLETOS_PEDIDO = "SELECT pedido.referencia AS ref, cliente.nombre AS nombre, pez.nombre AS pez, pedido.cantidad_enviada AS enviado, pedido.cantidad_solicitada AS solicitado FROM pedido JOIN cliente ON pedido.cliente_id = cliente.id JOIN pez ON pez.id = pedido.pez_id where pedido.cantidad_solicitada=pedido.cantidad_enviada order by pedido.id asc;";
    private static PreparedStatement getCompletosStatement;
    // Borrar todos los pedidos de la tabla
    private static final String BORRAR_PEDIDO = "DELETE from pedido where id>0;";
    private static PreparedStatement borrarStatement;
    // Actualizar la cantidad enviada de un pedido
    private static final String VENTA_PEDIDO = "UPDATE pedido set cantidad_enviada=cantidad_enviada+? where referencia=?";
    private static PreparedStatement ventaStatement;
    // Obtener el nombre del pez para un pedido dado
    private static final String GET_PEZ = "SELECT pez.nombre as nombre from pedido join pez on pedido.pez_id=pez.id where pedido.referencia=?;";
    private static PreparedStatement getPezStatement;
    // Obtener la cantidad solicitada y enviada para un pedido dado
    private static final String GET_CANTIDAD = "SELECT cantidad_solicitada as solicitada, cantidad_enviada as enviados from pedido where pedido.referencia=?;";
    private static PreparedStatement getCantidadStatement;
    // Conexión a la base de datos
    private static Connection conexion;
    private static int ref = 1;

    /**
     * Constructor de la clase DAOPedidos.
     * Inicializa los PreparedStatements y obtiene la última referencia de pedido.
     * 
     * @param conectar La conexión a la base de datos.
     */
    public DAOPedidos(Connection conectar) {
        conexion = conectar;
        try {
            insertPedidoStatement = conexion.prepareStatement(INSERT_PEDIDO);
            getIncompletosStatement = conexion.prepareStatement(GETINCOMPLETOS_PEDIDO);
            getCompletosStatement = conexion.prepareStatement(GETCOMPLETOS_PEDIDO);
            borrarStatement = conexion.prepareStatement(BORRAR_PEDIDO);
            getPezStatement = conexion.prepareStatement(GET_PEZ);
            getCantidadStatement = conexion.prepareStatement(GET_CANTIDAD);
            ventaStatement = conexion.prepareStatement(VENTA_PEDIDO);
            obtenerUltimaReferencia();
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
        }
    }

    /**
     * Agrega un nuevo pedido a la base de datos con valores aleatorios para
     * cliente, pez y cantidad.
     */
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
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
        }
    }

    /**
     * Cierra el PreparedStatement para evitar posibles fugas de memoria.
     */
    public static void cerrarStatement() {
        try {
            if (insertPedidoStatement != null) {
                insertPedidoStatement.close();
            }
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
        }
    }

    /**
     * Obtiene la última referencia de pedido de la base de datos para generar
     * nuevas referencias de pedido.
     */
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
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
        }
    }

    /**
     * Obtiene y muestra los pedidos incompletos en la consola.
     * 
     * @return Una lista de referencias de pedidos incompletos.
     */
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
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
            return null;
        }
    }

    /**
     * Obtiene y muestra los pedidos completos en la consola.
     */
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
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
        }
    }

    /**
     * Borra todos los datos de la tabla 'Pedido'.
     */
    public static void borrarDatos() {
        try {
            borrarStatement.executeUpdate();
            System.out.println("Datos borrados correctamente.");
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
        }
    }

    /**
     * Tramita un pedido, actualizando la cantidad enviada y otorgando una
     * recompensa si el pedido está completo.
     * 
     * @param id_pedido  El ID del pedido.
     * @param vendidos   La cantidad vendida.
     * @param necesarios La cantidad necesaria.
     * @param pez        El nombre del pez.
     */
    public static void tramitarPedido(int id_pedido, int vendidos, int necesarios, String pez) {
        try {
            // Ejecutar la actualización del pedido para aumentar la cantidad enviada
            ventaStatement.setInt(1, vendidos);
            ventaStatement.setInt(2, id_pedido);
            ventaStatement.executeUpdate();
            EscritorHelper.getEscritorHelper("")
                    .addTrans("Enviados " + vendidos + " al pedido de " + pez + " con referencia " + id_pedido);
            if (vendidos == necesarios) {
                obtenerRecompensa();
                EscritorHelper.getEscritorHelper("")
                        .addLogs("Pedido de " + pez + " con referencia " + id_pedido + " enviado");
                EscritorHelper.getEscritorHelper("")
                        .addTrans("Pedido de " + pez + " con referencia " + id_pedido + " enviado");
            }
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);

        }
    }

    /**
     * Obtiene el nombre del pez asociado a un pedido dado su número de referencia.
     * 
     * @param referencia El número de referencia del pedido.
     * @return El nombre del pez asociado al pedido.
     */
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
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
            return null;
        }
    }

    /**
     * Obtiene la cantidad restante por enviar de un pedido dado su número de
     * referencia.
     * 
     * @param referencia El número de referencia del pedido.
     * @return La cantidad restante por enviar.
     */
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

                diferencia = solicitada - enviados;
            }
        } catch (SQLException e) {
            EscritorHelper.getEscritorHelper("").addError("Error de SQL" + e);
        }
        return diferencia;
    }

    /**
     * Otorga una recompensa aleatoria al completar un pedido.
     */
    public static void obtenerRecompensa() {
        Random random = new Random();

        int rec = random.nextInt(10);

        if (rec <= 5) {
            int com = random.nextInt(10);
            if (com <= 6) {
                Crear.darComida(1);
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa comida_1 creada");
                EscritorHelper.getEscritorHelper("").addLogs("Recompensa comida_1 creada");

            } else if (com <= 9) {
                Crear.darComida(2);
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa comida_2 creada");
                EscritorHelper.getEscritorHelper("").addLogs("Recompensa comida_2 creada");
            } else {
                Crear.darComida(3);
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa comida_3 creada");
                EscritorHelper.getEscritorHelper("").addLogs("Recompensa comida_3 creada");
            }
        } else if (rec <= 9) {
            int com = random.nextInt(10);
            if (com <= 6) {
                Crear.darMonedas(1);
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa monedas_1 creada");
                EscritorHelper.getEscritorHelper("").addLogs("Recompensa monedas_1 creada");
            } else if (com <= 9) {
                Crear.darMonedas(2);
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa monedas_2 creada");
                EscritorHelper.getEscritorHelper("").addLogs("Recompensa monedas_2 creada");
            } else {
                Crear.darMonedas(3);
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa monedas_3 creada");
            }
        } else {
            int tan = random.nextInt(10);
            if (tan <= 6) {
                Crear.addTanque("r");
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa tanque río creada");
            } else {
                Crear.addTanque("m");
                EscritorHelper.getEscritorHelper("").addTrans("Recompensa tanque mar creada");
            }
        }
    }
}
