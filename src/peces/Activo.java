package peces;

import java.util.Random;

public abstract class Activo extends Pez {

    public Activo(boolean sexo) {
        super(sexo);
    }

    @Override
    public int comer(int comida) {
        Random comer = new Random();
        if (comida != 0) {
            if (comer.nextBoolean()) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 3;
        }
    }
}
