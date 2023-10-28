package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Sargo extends Omnivoro{
    private final PecesDatos datos = AlmacenPropiedades.SARGO;

    public PecesDatos getDatos() {
        return datos;
    }

    public Sargo(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
