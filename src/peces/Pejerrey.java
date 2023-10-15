package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Pejerrey extends Carnivoro{
    
    private PecesDatos datos = AlmacenPropiedades.PEJERREY;

    @Override
    public void grow() {
        // TODO Auto-generated method stub
        super.grow();
    }

    @Override
    public void comprobarFertilidad() {
        if(this.edad>=this.datos.getMadurez() && (this.edad%this.datos.getCiclo())==0){
            this.setFertil(true);
        }else{
            this.setFertil(false);
        }
    }

}
