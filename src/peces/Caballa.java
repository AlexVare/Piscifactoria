package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Caballa extends Pez{
    private final PecesDatos datos = AlmacenPropiedades.CABALLA;

    public Caballa(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
