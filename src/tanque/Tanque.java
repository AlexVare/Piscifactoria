package tanque;

import java.util.ArrayList;
import peces.Pez;

public class Tanque<T extends Pez> {

    ArrayList<T> peces = new ArrayList<>();
    int capacidad;
    ArrayList<Integer> muertos;

    public Tanque(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean hasDead() {
        this.muertos.removeAll(muertos);
        for (int i = 0; i < peces.size(); i++) {
            if (!peces.get(i).isVivo()) {
                this.muertos.add(i);
            }
        }
        if (muertos.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int nuevoDiaComer(int comida) {
        int resto = comida;
        int cadaveres=0;
        
        this.hasDead();
        cadaveres = this.muertos.size();
        for (int i = 0; i < peces.size(); i++) {
            if (this.peces.get(i).isVivo()) {
                if (this.muertos.size() != 0) {
                    if (this.peces.get(i).eliminarPez()) {
                        cadaveres--;
                    }
                    this.peces.get(i).grow(resto, true);
                } else {
                    resto -= this.peces.get(i).grow(resto, false);
                }
            }
        }
        //Quitar peces comidos
        if(this.muertos.size()!=0){
            for(int i=muertos.size();i>cadaveres;i--){
                this.peces.remove((int)this.muertos.get(i));
            }
        }
        return resto;
    }

    public void nuevoDiaReproduccion(){
        for(int i=0; i<this.peces.size();i++){
            if(this.peces.get(i).isVivo()){
                this.peces.get(i).comprobarMadurez();
                if(this.peces.get(i).isFertil()){
                    this.peces.get(i).comprobarFertilidad();
                }
            }
        }
    }

    public void limpiarTanque(){
        this.hasDead();
        for (Integer muerto : muertos) {
            this.peces.remove((int)muerto);
        }
    }

    public void vaciarTanque(){
        this.peces.removeAll(peces);
    }

    
}