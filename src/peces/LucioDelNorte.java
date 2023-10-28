package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class LucioDelNorte extends Activo{
    
    private final PecesDatos datos = AlmacenPropiedades.LUCIO_NORTE;

    public LucioDelNorte(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
