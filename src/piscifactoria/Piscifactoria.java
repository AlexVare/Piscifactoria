package piscifactoria;

import java.util.ArrayList;
import java.util.Scanner;

import monedero.Monedas;
import peces.Pez;
import tanque.Tanque;

public class Piscifactoria {

    private final boolean rio;
    private String nombre = "";
    private ArrayList<Tanque<Pez>> tanques = new ArrayList<>();

    private int almacen;
    private int almacenMax;

    public Piscifactoria(boolean rio, String nombre) {
        this.rio = rio;
        if (this.rio) {
            this.nombre = nombre;
            this.tanques.add(new Tanque<Pez>(25));
            this.almacen = 25;
            this.almacenMax = 25;
        } else {
            this.nombre = nombre;
            this.tanques.add(new Tanque<Pez>(100));
            this.almacen = 100;
            this.almacenMax = 100;
        }
    }

    public boolean isRio() {
        return rio;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Tanque<Pez>> getTanques() {
        return tanques;
    }

    public void nuevoDia() {
        for (int i = 0; i < tanques.size(); i++) {
            if (almacen != 0) {
                this.almacen -= tanques.get(i).nuevoDiaComer(this.almacen);
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
                    System.out.println("No es posible comprar un nuevo tanque, llegaste al máximo");
                }
            } else {
                System.out.println("No tienes dinero suciciente");
            }
            return monedero;
        } else {
            if (monedero.comprobarPosible(600 * this.tanques.size())) {
                if (this.tanques.size() < 10) {
                    monedero.compra(600 * this.tanques.size());
                    this.tanques.add(new Tanque<Pez>(100));
                } else {
                    System.out.println("No es posible comprar un nuevo tanque, llegaste al máximo");
                }
            } else {
                System.out.println("No tienes dinero suciciente");
            }
            return monedero;
        }
    }

    public void showStatus() {

    }

    public Monedas nuevoPez(Monedas monedero) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        try {
            do {
                this.listTanks();
                opcion = Integer.parseInt(sc.nextLine());
                if (opcion < 1 || opcion > this.tanques.size()) {
                    System.out.println("Opción no válida, introduce uno de los valores mostrados");
                } else {
                    if (this.tanques.get(opcion).getPeces().size() != 0) {
                        try {
                            monedero = this.tanques.get(opcion).comprarPez(monedero);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }else{
                        this.opcionPez();
                    }
                }
            } while (opcion < 1 || opcion > this.tanques.size());
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida");
        }
        return monedero;
    }

    public void listTanks() {
        for (int i = 0; i < this.tanques.size(); i++) {
            if (this.tanques.get(i).getPeces().size() == 0) {
                System.out.println(i + ". Tanque vacío");
            } else {
                System.out.println((i + 1) + ". Pez:" + this.tanques.get(i).getPeces().get(0).getDatos().getNombre());
            }
        }
    }

    public void opcionPez(){
        if(this.rio){
            System.out.println("******Peces******");
            System.out.println("1.Pejerrey");
            System.out.println("2.Lucio del norte");
            System.out.println("3.Salmón chinook");
            System.out.println("4.Perca europea");
            System.out.println("5.Carpa");
            System.out.println("6.Dorada");
            System.out.println("7.Trucha arcoiris");
        }else{
            System.out.println("******Peces******");
            System.out.println("1.Róbalo");
            System.out.println("2.Caballa");
            System.out.println("3.Lenguado europeo");
            System.out.println("4.Sargo");
            System.out.println("5.Besugo");
            System.out.println("6.Dorada");
            System.out.println("7.Trucha arcoiris");
        }
    }
}