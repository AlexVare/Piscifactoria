package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class SalmonChinook extends Pez{

    private final PecesDatos datos = AlmacenPropiedades.SALMON_CHINOOK;

    public SalmonChinook(boolean sexo) {
        super(sexo);
        this.ciclo=this.datos.getCiclo();
    }
}
