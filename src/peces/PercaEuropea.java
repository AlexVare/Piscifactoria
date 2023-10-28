package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class PercaEuropea extends Activo{
    
    private final PecesDatos datos = AlmacenPropiedades.PERCA_EUROPEA;

    public PercaEuropea(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
