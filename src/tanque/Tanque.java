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

    public int alimentar(int comida) {
        int resto = comida;
        for (int i = 0; i < peces.size(); i++) {
            if (this.muertos.size() != 0) {
                if (this.peces.get(i).eliminarPez()) {
                    this.peces.remove(this.muertos.get(this.muertos.size()));
                    this.muertos.remove(this.muertos.size());
                }
            }else{
                
                if(this.peces.get(i).comer(resto)!=3){

                }
            }
        }
        return resto;
    }

}
