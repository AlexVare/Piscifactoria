package peces;

import java.util.Random;

public class Omnivoro extends Pez{

    @Override
    public int comer(int comida) {
        if(this.noComer()){
            return 0;
        }else{
            return super.comer(comida);
        }
    }
    
    @Override
    public String showStatus() {
        // TODO Auto-generated method stub
        return super.showStatus();
    }
    
    private boolean noComer() {
        Random r= new Random();
        if(r.nextInt(1,4)==1){
            return true;
        }
        return false;
    }
}