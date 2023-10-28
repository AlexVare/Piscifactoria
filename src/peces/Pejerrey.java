package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Pejerrey extends Pez{
    
    private final PecesDatos datos = AlmacenPropiedades.PEJERREY;

    public Pejerrey(boolean sexo){
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}