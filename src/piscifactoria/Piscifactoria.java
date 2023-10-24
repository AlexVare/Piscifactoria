package piscifactoria;

import java.util.ArrayList;
import monedero.Monedas;
import peces.Pez;
import tanque.Tanque;

public class Piscifactoria {

    private final boolean rio;
    private ArrayList<Tanque<Pez>> tanques = new ArrayList<>();
    private int almacen;
    private int almacenMax;

    public Piscifactoria(boolean rio) {
        this.rio = rio;
        if (this.rio) {
            this.tanques.add(new Tanque<Pez>(25));
            this.almacen = 25;
            this.almacenMax = 25;
        } else {
            this.tanques.add(new Tanque<Pez>(100));
            this.almacen = 100;
            this.almacenMax = 100;
        }
    }

    public boolean isRio() {
        return rio;
    }

    public void nuevoDia() {
        for (int i = 0; i < tanques.size(); i++) {
            if (almacen != 0) {
                this.almacen = tanques.get(i).nuevoDiaComer(this.almacen);
                tanques.get(i).nuevoDiaReproduccion();
            }
        }
    }

    public Monedas upgradeFood(Monedas monedero) {
        if (this.rio) {
            if (monedero.comprobarPosible(100)) {
                if (this.almacenMax < 250) {
                    monedero.compra(100);
                    this.almacenMax += 25;
                } else {
                    System.out.println("No puedes aumentar la capacidad");
                }
            } else {
                System.out.println("No tienes dinero suficiente");
            }
            return monedero;
        } else {
            if (monedero.comprobarPosible(200)) {
                if (this.almacenMax < 1000) {
                    monedero.compra(200);
                    this.almacenMax += 100;
                } else {
                    System.out.println("No puedes aumentar la capacidad");
                }
            } else {
                System.out.println("No tienes dinero suficiente");
            }
            return monedero;
        }
    }

    public Monedas comprarTanque(Monedas monedero) {
        if (this.rio) {
            if (monedero.comprobarPosible(150 * this.tanques.size())) {
                if (this.tanques.size() < 10) {
                    monedero.compra(150 * this.tanques.size());
                    this.tanques.add(new Tanque<Pez>(25));
                } else {
                    System.out.println("No es posible comprar un nuevo tanque, llegaste al maximo");
                }
            }else{
                System.out.println("No tienes dinero suciciente");
            }
            return monedero;
        } else {
            if (monedero.comprobarPosible(600 * this.tanques.size())) {
                if (this.tanques.size() < 10) {
                    monedero.compra(600 * this.tanques.size());
                    this.tanques.add(new Tanque<Pez>(100));
                } else {
                    System.out.println("No es posible comprar un nuevo tanque, llegaste al maximo");
                }
            }else{
                System.out.println("No tienes dinero suciciente");
            }
            return monedero;
        }
    }

    public void showStatus(){

    }
}