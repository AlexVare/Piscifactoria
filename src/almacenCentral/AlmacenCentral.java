package almacenCentral;

import inputHelper.EscritorHelper;
import monedero.Monedas;

/**
 * Clase que representa el Almacén Central.
 */
public class AlmacenCentral {

    /**
     * Instancia única de la clase AlmacenCentral.
     */
    public static AlmacenCentral instance;

    /**
     * Método para obtener la instancia única del Almacén Central.
     *
     * @return La instancia del Almacén Central.
     */
    public static AlmacenCentral getInstance() {
        if (instance == null) {
            instance = new AlmacenCentral();
        }
        return instance;
    }

    int capacidad = 0;
    int capacidadMax = 0;

    /**
     * Constructor privado de la clase AlmacenCentral.
     * Inicializa la capacidad máxima del almacén.
     */
    private AlmacenCentral() {
        this.capacidadMax = 200;
        this.capacidad = 200;
    }

    /**
     * Establece la capacidad actual del almacén.
     *
     * @param capacidad La nueva capacidad actual del almacén.
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Establece la capacidad máxima del almacén.
     *
     * @param capacidadMax La nueva capacidad máxima del almacén.
     */
    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    /**
     * Obtiene la capacidad máxima del almacén.
     *
     * @return La capacidad máxima del almacén.
     */
    public int getCapacidadMax() {
        return capacidadMax;
    }

    /**
     * Obtiene la capacidad actual del almacén.
     *
     * @return La capacidad actual del almacén.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Aumenta la capacidad máxima del almacén en una cantidad determinada.
     *
     * @param cantidad La cantidad en la que se aumentará la capacidad máxima.
     */
    public void aumentarCapacidad(int cantidad) {
        this.capacidadMax += cantidad;
    }

    /**
     * Agrega una cantidad especificada de comida al almacén central.
     *
     * @param cantidad La cantidad de comida a agregar.
     */
    public void agregarComida(int cantidad) {
        this.capacidad += cantidad;
    }

    /**
     * Realiza una mejora en el almacén central, aumentando su capacidad máxima.
     * Se requiere el gasto de monedas para realizar esta mejora.
     */
    public void upgrade() {
        if (Monedas.getInstancia().comprobarPosible(100)) {
            Monedas.getInstancia().compra(100);
            this.aumentarCapacidad(50);
        } else {
            System.out.println("No tienes monedas suficientes");
        }
    }

    /**
     * Compra una cantidad específica de comida para agregar al almacén central.
     *
     * @param cantidad La cantidad de comida a comprar.
     */
    public void comprarComida(int cantidad) {
        int costo;
        if (cantidad <= 25) {
            costo = cantidad;
        } else {
            costo = cantidad - (cantidad / 25) * 5;
        }
        if (Monedas.getInstancia().comprobarPosible(costo)) {
            this.capacidad += cantidad;
            Monedas.getInstancia().compra(costo);
            if (this.capacidad > this.capacidadMax) {
                this.capacidad = this.capacidadMax;
            }
            System.out.println("Añadida " + cantidad + " de comida");
            EscritorHelper.getEscritorHelper("").addTrans(
                    cantidad + "de comida comprada por " + costo + ". Se almacena en el almacén central" + "\n");
            EscritorHelper.getEscritorHelper("")
                    .addLogs(cantidad + "de comida comprada. Se almacena en el almacén central" + "\n");
        } else {
            System.out.println("No tienes suficientes monedas para agregar comida.");
        }
    }

}