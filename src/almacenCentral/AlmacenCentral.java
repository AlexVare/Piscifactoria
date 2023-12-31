package almacenCentral;

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
        if(instance==null){
            instance=new AlmacenCentral();
        }
        return instance;
    }

    int capacidad=0;

    int capacidadMax=0;

    /**
     * Constructor privado de la clase AlmacenCentral.
     * Inicializa la capacidad máxima del almacén.
     */
    private AlmacenCentral() {
        this.capacidadMax = 200;
        this.capacidad=200;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public int getCapacidad() {
        return capacidad;
    }
    
     /**
     * Aumenta la capacidad máxima del almacén en una cantidad determinada.
     *
     * @param cantidad La cantidad en la que se aumentará la capacidad máxima.
     */
    public void aumentarCapacidad(int cantidad){
        this.capacidadMax+=cantidad;
    }

    /**
     * Realiza una mejora en el almacén central, aumentando su capacidad máxima.
     * Se requiere el gasto de monedas para realizar esta mejora.
     */
    public void upgrade(){
        if(Monedas.getInstancia().comprobarPosible(100)){
            Monedas.getInstancia().compra(100);
            this.aumentarCapacidad(50);
        }else{
            System.out.println("No tienes monedas suficientes");
        }
    }
}
