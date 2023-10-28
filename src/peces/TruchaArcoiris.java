package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class TruchaArcoiris extends Pez{
    private final PecesDatos datos = AlmacenPropiedades.TRUCHA_ARCOIRIS;

    public TruchaArcoiris(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
