package inputHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que proporciona métodos para gestionar archivos de log, Tanscripciones
 * y guardados
 * relacionados con la simulación de una actividad de pesca y piscicultura.
 *
 * @author Álex Varela
 */
public class EscritorHelper {

    private BufferedWriter streamTransicripcion = null;
    private BufferedWriter streamLog = null;
    private static EscritorHelper escritorHelper = null;
    private String partida;

    /**
     * Constructor privado para la clase ArchivoHelper.
     * Se encarga de inicializar los streams para log y transcripción,
     * crear directorios necesarios y realizar configuraciones iniciales.
     *
     * @param partida Nombre de la partida.
     * @param pisci1  Piscifactoría inicial.
     */
    private EscritorHelper(String partida, String pisci1) {

        try {
            this.partida = partida;
            File Transcript = new File("Tanscripciones");
            Transcript.mkdir();
            File Logs = new File("Logs");
            Logs.mkdir();
            File Saves = new File("saves");
            Saves.mkdir();
            File trans = new File("Tanscripciones/" + partida + ".tr");
            File log = new File("Logs/" + partida + ".log");
            streamTransicripcion = new BufferedWriter(new FileWriter(trans, true));
            streamLog = new BufferedWriter(new FileWriter(log, true));
            addError("");
            addLogs("");
            addTrans("");
            addFirstLines(partida, pisci1);
        } catch (IOException e) {
            escritorHelper.addError("Error al crear los streams  archivos de log y transcripcion\n");
        }
    }

    /**
     * Constructor privado adicional para la clase ArchivoHelper.
     * Se encarga de inicializar los streams para log y transcripción,
     * crear directorios necesarios y realizar configuraciones iniciales.
     *
     * @param partida Nombre de la partida.
     */
    private EscritorHelper(String partida) {
        try {
            this.partida = partida;
            File Transcript = new File("Tanscripciones");
            Transcript.mkdir();
            File Logs = new File("Logs");
            Logs.mkdir();
            File Saves = new File("Saves");
            Saves.mkdir();
            File trans = new File("Tanscripciones/" + partida + ".tr");
            File log = new File("Logs/" + partida + ".log");
            streamTransicripcion = new BufferedWriter(new FileWriter(trans, true));
            streamLog = new BufferedWriter(new FileWriter(log, true));
            addError("");
            addLogs("");
            addTrans("");
        } catch (IOException e) {
            escritorHelper.addError("Error al crear los streams  archivos de log y transcripcion\n");
        }
    }

    /**
     * Método estático que devuelve una instancia única de ArchivoHelper para la
     * partida especificada.
     * Si no existe, se crea una nueva instancia.
     *
     * @param partida Nombre de la partida.
     * @param pisci1  Piscifactoría inicial.
     * @return Instancia única de ArchivoHelper.
     */
    public static EscritorHelper getEscritorHelper(String partida, String pisci1) {
        if (escritorHelper == null) {
            escritorHelper = new EscritorHelper(partida, pisci1);
        }
        return escritorHelper;
    }

    /**
     * Método estático que devuelve una instancia única de ArchivoHelper para la
     * partida especificada.
     * Si no existe, se crea una nueva instancia.
     *
     * @param partida Nombre de la partida.
     * @return Instancia única de ArchivoHelper.
     */
    public static EscritorHelper getEscritorHelper(String partida) {
        if (escritorHelper == null) {
            escritorHelper = new EscritorHelper(partida);
        }
        return escritorHelper;
    }

    /**
     * Método que agrega las líneas iniciales al archivo de transcripción y
     * log.
     *
     * @param partida Nombre de la partida.
     * @param pisci1  Piscifactoría inicial.
     */
    public void addFirstLines(String partida, String pisci1) {
        String primerasLineas = "=====Arranque=====" + "\n" +
                "Iniciando partida " + partida + "." + "\n" +
                "=====Dinero Inicial=====" + "\n" +
                "Dinero: 100 monedas" + "\n" +
                "=====Peces Implementados=====" + "\n" +
                "Río: " + "\n" +
                "Pejerrey" + "\n" +
                "Carpa plateada" + "\n" +
                "Salmón chinook" + "\n" +
                "Tilapia del Nilo" + "\n" +
                "Carpa" + "\n" +
                "Mar:" + "\n" +
                "Róbalo" + "\n" +
                "Caballa" + "\n" +
                "Arenque del Atlántico" + "\n" +
                "Sargo" + "\n" +
                "Besugo" + "\n" +
                "Doble:" + "\n" +
                "Dorada" + "\n" +
                "Trucha Arcoiris" + "\n" +
                "--------------------------" + "\n" +
                "Piscifactoria inicial: " + pisci1 + "\n";
        addTrans(primerasLineas);

        String primeraLog = "Inicio de la simulación " + partida + ".\n";
        String segundaLog = "Piscifactoría inicial: " + pisci1 + ".\n";
        addLogs(primeraLog);
        addLogs(segundaLog);
    }

    /**
     * Cierra los streams de log y transcripción.
     */
    public void cerrarStreams() {
        try {
            if (streamTransicripcion != null) {
                streamTransicripcion.close();
            }
            if (streamLog != null) {
                streamLog.close();
            }
        } catch (IOException e) {
            escritorHelper.addError("Error al cerrar los streams de log y transcipcion\n");
        }
    }

    /**
     * Agrega una línea al archivo de transcripción.
     *
     * @param mensaje Mensaje a agregar.
     */
    public void addTrans(String mensaje) {
        try {
            streamTransicripcion.write(mensaje);
            streamTransicripcion.flush();
        } catch (IOException e) {
            escritorHelper.addError("Error al añadir una linea al archivo de transcripcion \n");
        }
    }

    /**
     * Obtiene la hora actual formateada.
     *
     * @return Hora actual formateada.
     */
    private String obtenerHora() {
        // Obtener la fecha y hora actual
        Date fechaHoraActual = new Date();

        // Definir el formato de fecha y hora deseado
        SimpleDateFormat formato = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");

        // Formatear la fecha y hora actual
        return formato.format(fechaHoraActual);
    }

    /**
     * Agrega una línea al archivo de log con la hora actual.
     *
     * @param mensaje Mensaje a agregar.
     */
    public void addLogs(String mensaje) {
        try {
            streamLog.write(obtenerHora() + " " + mensaje);
            streamLog.flush();
        } catch (IOException e) {
            escritorHelper.addError("Error al añadir una linea al archivo de log\n");
        }
    }

    /**
     * Agrega una línea al archivo de errores.
     *
     * @param mensaje Mensaje de error a agregar.
     */
    public void addError(String mensaje) {
        BufferedWriter streamError = null;
        try {
            File error = new File("Logs/0_errors.log");
            streamError = new BufferedWriter(new FileWriter(error));
            streamError.write(obtenerHora() + " " + mensaje);
            streamError.flush();
        } catch (IOException e) {
            escritorHelper.addError("Error al añadir una linea al archivo de errores\n");
        } finally {
            try {
                if (streamError != null) {
                    streamError.close();
                }
            } catch (IOException e) {
                escritorHelper.addError("Error al cerrar el stream de errores\n");
            }
        }
    }
}
