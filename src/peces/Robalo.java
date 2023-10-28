package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Robalo extends Pez{
    private final PecesDatos datos = AlmacenPropiedades.ROBALO;

    public PecesDatos getDatos() {
        return datos;
    }

    public Robalo(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
