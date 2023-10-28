package almacenCentral;

import monedero.Monedas;

public class AlmacenCentral {
    
    int capacidad=0;

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad += capacidad;
    }

    public AlmacenCentral() {
        this.capacidad = 200;
    }
    
    public Monedas upgrade(Monedas monedero){
        if(monedero.comprobarPosible(100)){
            monedero.compra(100);
            this.setCapacidad(50);
        }else{
            System.out.println("No tienes monedas suficientes");
        }
        return monedero;
    }
}
