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

    public boolean reproduccion() {
        if(this.maduro&&this.edad%this.datos.getCiclo()==0){
            return true;
        }else{
            return false;
        }
    }

    
}