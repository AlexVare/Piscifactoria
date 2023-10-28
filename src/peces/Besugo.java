package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Besugo extends Pez{
    private final PecesDatos datos = AlmacenPropiedades.BESUGO;

    public Besugo(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
