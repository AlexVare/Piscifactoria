package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Dorada extends Omnivoro{
    private final PecesDatos datos = AlmacenPropiedades.DORADA;

    public Dorada(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
