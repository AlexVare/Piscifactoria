package tanque;
import java.util.ArrayList;

import peces.Pez;

public class Tanque<T extends Pez> {
    
    ArrayList<T> peces = new ArrayList<>();
    int capacidad;
    int muertos=0;

    public Tanque(int capacidad) {
        this.capacidad=capacidad;
    }

    public boolean hasDead(){
        for(int i=0; i<peces.size();i++){
            if(!peces.get(i).isVivo()){
                this.muertos++;
            }
        }
        if(muertos!=0){
            return true;
        }else{
            return false;
        }
    }

    public int alimentar(int comida){
        int resto=comida;
        if(this.hasDead()){
            
        }
        return resto;
    }
    
}
