package peces;

import java.util.Random;

public abstract class Omnivoro extends Pez {

    private boolean noComer(){
        Random r = new Random();
        if (r.nextInt(1, 4) == 1){
            return true;
        }
        return false;
    }

    @Override
    public int grow(int comida, boolean comido){
        if (!this.noComer()){
            return super.grow(comida, comido);
        }
        return super.grow(comida, false);
    }
}