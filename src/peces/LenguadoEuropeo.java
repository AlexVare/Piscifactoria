package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class LenguadoEuropeo extends Pez{
    private final PecesDatos datos = AlmacenPropiedades.LENGUADO_EUROPEO;

    public PecesDatos getDatos() {
        return datos;
    }

    public LenguadoEuropeo(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
