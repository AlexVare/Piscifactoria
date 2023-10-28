package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Besugo extends Pez{
    private final PecesDatos datos = AlmacenPropiedades.BESUGO;

    public PecesDatos getDatos() {
        return datos;
    }

    public Besugo(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
