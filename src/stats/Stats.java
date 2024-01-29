package stats;

import estadisticas.Estadisticas;

/**
 * La clase Stats extiende la clase Estadisticas y proporciona una instancia
 * única para el seguimiento de estadísticas.
 */
public class Stats extends Estadisticas {

    /**
     * Instancia única de la calse Stats.
     */
    private static Stats instance = null;

    /**
     * Constructor privado para crear una instancia de Stats con los nombres de las
     * estadísticas especificados.
     *
     * @param nombres Un array de cadenas que representa los nombres de las
     *                estadísticas a seguir.
     */
    private Stats(String[] nombres) {
        super(nombres);
    }

    /**
     * Obtiene la instancia única de Stats. Si no existe, crea una nueva instancia.
     *
     * @param nombres Un array de cadenas que representa los nombres de las
     *                estadísticas a seguir.
     * @return La instancia única de Stats.
     */
    public static Stats getInstancia(String[] nombres) {
        if (instance == null) {
            instance = new Stats(nombres);
        }
        return instance;
    }

    /**
     * Obtiene la instancia única de Stats. Si no existe, devuelve null.
     *
     * @return La instancia única de Stats o null si no existe una instancia
     *         previamente creada.
     */
    public static Stats getInstancia() {
        return instance;
    }
}
