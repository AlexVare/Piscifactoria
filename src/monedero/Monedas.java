package monedero;

public class Monedas {

    private int cantidad;

    /**
     * Instancia única del monedero.
     */
    private static Monedas instance=null;

    /**
     * Constructor privado de la clase Monedas que inicializa la cantidad de monedas.
     *
     * @param cantidad La cantidad inicial de monedas en el monedero.
     */
    private Monedas(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la instancia única del monedero, creándola si no existe.
     *
     * @return La instancia del monedero.
     */
    public static Monedas getInstancia() {
        if (instance == null) {
            instance = new Monedas(100);
        }
        return instance;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Comprueba si es posible realizar una compra con la cantidad especificada.
     *
     * @param precio El precio de la compra.
     * @return true si es posible realizar la compra, false en caso contrario.
     */
    public boolean comprobarPosible(int precio) {
        if (cantidad >= precio) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Realiza una compra, disminuyendo la cantidad de monedas.
     *
     * @param precio El precio de la compra.
     */
    public void compra(int precio){
        this.cantidad-=precio;
    }

    /**
     * Realiza una venta, aumentando la cantidad de monedas.
     *
     * @param precio El precio de la compra.
     */
    public void venta(int precio){
        this.cantidad+=precio;
    }

    /***
     * Método para aumentar las monedas
     * @param monedas Cantidad a aumentar
     */
    public void agregarMonedos(int monedas){
        this.cantidad+=monedas;
    }
}