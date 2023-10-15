package peces;

import java.util.Random;

public class Omnivoro extends Pez{

    @Override
    public boolean comer() {
        if(this.noComer()){
        return super.comer();
        }else{
            return false;
        }
    }

    @Override
    public boolean comerPez() {
        // TODO Auto-generated method stub
        return super.comerPez();
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