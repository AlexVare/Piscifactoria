package piscifactoria;

import java.util.ArrayList;

import peces.Pez;
import tanque.Tanque;

public class Piscifactoria {

    private final boolean rio;
    private int ntanque = 0;
    private ArrayList<Tanque<Pez>> tanques = new ArrayList<>();
    private int almacen;
    private int almacenMax;

    public Piscifactoria(boolean rio) {
        this.rio = rio;
        if (this.rio) {
            this.tanques.add(new Tanque<Pez>(25));
            this.setNtanque(this.tanques.size());
            this.almacen = 25;
            this.almacenMax = 25;
        } else {
            this.tanques.add(new Tanque<Pez>(100));
            this.ntanque = this.tanques.size();
            this.almacen = 100;
            this.almacenMax = 100;
        }
    }

    public int getNtanque() {
        return ntanque;
    }

    public void setNtanque(int ntanque) {
        this.ntanque = ntanque;
    }

    public boolean isRio() {
        return rio;
    }

    public void alimentar() {
        for (int i = 0; i < tanques.size(); i++) {
            if (almacen != 0) {
                this.almacen = tanques.get(i).nuevoDia(this.almacen);
            }
        }
    }
}