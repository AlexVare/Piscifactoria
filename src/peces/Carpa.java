package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Carpa extends Omnivoro{
    
     private final PecesDatos datos = AlmacenPropiedades.CARPA;

    public PecesDatos getDatos() {
        return datos;
    }

    public Carpa(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }

    @Override
    public int comer(int comida) {
        if (comida != 0) {
            return 2;
        } else {
            return 3;
        }
    }
}
