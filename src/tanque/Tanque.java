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

    public int nuevoDia(int comida) {
        int resto = comida;
        if (this.muertos.size() == 0) {
            this.hasDead();
        }
        for (int i = 0; i < peces.size(); i++) {
            if (this.peces.get(i).isVivo()) {
                if (this.muertos.size() != 0) {
                    if (this.peces.get(i).eliminarPez()) {
                        this.peces.remove(this.muertos.get(this.muertos.size()));
                        this.muertos.remove(this.muertos.size());
                    }
                    this.peces.get(i).grow(resto, true);
                } else {
                    resto -= this.peces.get(i).grow(resto, false);
                }
            }
        }
        return resto;
    }
}