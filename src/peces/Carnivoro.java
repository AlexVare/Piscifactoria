package peces;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Carnivoro extends Pez {

    /**
     * Método para que un pez Carnivoro coma.
     * 
     * @param tanque        El tanque en el que se encuentra el pez.
     * @param piscifactoria La piscifactoria a la que pertenece el tanque.
     */
    @Override
    public void comer(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCen) {
        Random random = new Random();

        if (this.alimentado == false) { // Primero comprobar si esta alimentado

            if (tanque.hasDead()) {
                this.alimentado = true;
                if (random.nextBoolean()) { // comprobamos si se elimina o no
                    tanque.borrarMuerto();
                }
            } else {
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

    @Override
    public boolean reproduccion() {
        return super.reproduccion();
    }
}
