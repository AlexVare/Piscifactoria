package peces;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Filtrador extends Pez {

    @Override
    public void grow(Tanque tanque, Piscifactoria pisc, Boolean almacenCen) {
        Random random = new Random();
        if (random.nextBoolean()) {
            this.edad++;
        } else {
            super.grow(tanque, pisc, almacenCen);
        }
    }

    /**
     * Método para que un pez filtrador coma.
     * 
     * @param tanque        El tanque en el que se encuentra el pez.
     * @param piscifactoria La piscifactoria a la que pertenece el tanque.
     */
    @Override
    public void comer(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCen) {

        if (this.alimentado == false) { // Primero comprobar si esta alimentado

            if (piscifactoria.getAlmacen() != 0) {
                piscifactoria.setAlmacen(piscifactoria.getAlmacen() - 1);
                this.alimentado = true;
            } else if (almacenCen) {
                AlmacenCentral.getInstance().setCapacidad(AlmacenCentral.getInstance().getCapacidad() - 1);
                this.alimentado = true;
            }
        }
    }
}
