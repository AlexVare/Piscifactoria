package peces;

import java.net.NetPermission;
import java.util.Random;

public class Carnivoro extends Pez {
    
    @Override
    public void Comer(){
        
        Random r= new Random();

        if(pezMuerto()){
            if(r.nextBoolean()){

            }else{

            }
        }
    }
}
