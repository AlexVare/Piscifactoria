package almacenCentral;

import monedero.Monedas;

public class AlmacenCentral {
    
    int capacidad=0;

    public static AlmacenCentral instance;

    public static AlmacenCentral getInstance() {
        if(instance==null){
            instance=new AlmacenCentral();
        }
        return instance;
    }

    public int getCapacidad() {
        return capacidad;
    }

    private AlmacenCentral() {
        this.capacidad = 200;
    }
    
    public void aumentarCapacidad(int cantidad){
        this.capacidad+=cantidad;
    }

    public void upgrade(){
        if(Monedas.getInstancia().comprobarPosible(100)){
            Monedas.getInstancia().compra(100);
            this.aumentarCapacidad(50);
        }else{
            System.out.println("No tienes monedas suficientes");
        }
    }
}
