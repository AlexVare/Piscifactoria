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

    int capacidadVeg=0;
    int capacidadAni=0;

    int capacidadVegMax=0;
    int capacidadAniMax=0;

    /**
     * Constructor privado de la clase AlmacenCentral.
     * Inicializa la capacidad máxima del almacén.
     */
    private AlmacenCentral() {
        this.capacidadAniMax = 200;
        this.capacidadAni=200;
        this.capacidadVegMax = 200;
        this.capacidadVeg=200;
    }

    public int getCapacidadVegMax() {
        return capacidadVegMax;
    }

    public int getCapacidadVeg() {
        return capacidadVeg;
    }

    public int getCapacidadAniMax() {
        return capacidadVegMax;
    }

    public int getCapacidadAni() {
        return capacidadVeg;
    }
    
     /**
     * Aumenta la capacidad máxima del almacén en una cantidad determinada.
     *
     * @param cantidad La cantidad en la que se aumentará la capacidad máxima.
     */
    public void aumentarCapacidad(int cantidad){
        this.capacidadVegMax+=cantidad;
        this.capacidadAniMax+=cantidad;
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