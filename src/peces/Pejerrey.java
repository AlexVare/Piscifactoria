package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Pejerrey extends Carnivoro{
    
    private final PecesDatos datos = AlmacenPropiedades.PEJERREY;

    @Override
    public void comprobarMadurez() {
        if(this.edad>=this.datos.getMadurez()){
            this.setFertil(true);
        }else{
            this.setFertil(false);
        }
    }
}