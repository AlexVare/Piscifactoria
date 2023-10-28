package piscifactoria;

import java.util.ArrayList;
import java.util.Scanner;

import monedero.Monedas;
import peces.Besugo;
import peces.Caballa;
import peces.Carpa;
import peces.Dorada;
import peces.LenguadoEuropeo;
import peces.LucioDelNorte;
import peces.Pejerrey;
import peces.PercaEuropea;
import peces.Pez;
import peces.Robalo;
import peces.SalmonChinook;
import peces.Sargo;
import peces.TruchaArcoiris;
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
        for (int i = 0; i < this.tanques.size(); i++) {
            if (almacen != 0) {
                this.almacen -= this.tanques.get(i).nuevoDiaComer(this.almacen);
                this.tanques.get(i).nuevoDiaReproduccion();
            }
            this.tanques.get(i).venderOptimos();
            System.out.println("Piscifactoria "+this.nombre+": "+ this.tanques.get(i).getVendidos() + " peces vendidos por " + this.tanques.get(i).getGanancias()+ " monedas");
        }
        this.gananciaDiaria();
    }

    public void gananciaDiaria(){
        int cantidad=0;
        int ganancia=0;
        for (Tanque<Pez> tanque : tanques) {
            cantidad+=tanque.getVendidos();
            ganancia+=tanque.getGanancias();
        }
        System.out.println("Piscifactoria "+this.nombre+": "+ cantidad + " peces vendidos por " + ganancia + " monedas totales");
    }

    public void upgradeFood() {
        if (this.rio) {
            if (Monedas.getInstancia().comprobarPosible(100)) {
                if (this.almacenMax < 250) {
                    Monedas.getInstancia().compra(100);
                    this.almacenMax += 25;
                } else {
                    System.out.println("No puedes aumentar la capacidad");
                }
            } else {
                System.out.println("No tienes dinero suficiente");
            }
        } else {
            if (Monedas.getInstancia().comprobarPosible(200)) {
                if (this.almacenMax < 1000) {
                    Monedas.getInstancia().compra(200);
                    this.almacenMax += 100;
                } else {
                    System.out.println("No puedes aumentar la capacidad");
                }
            } else {
                System.out.println("No tienes dinero suficiente");
            }
        }
    }

    public void comprarTanque() {
        if (this.rio) {
            if (Monedas.getInstancia().comprobarPosible(150 * this.tanques.size())) {
                if (this.tanques.size() < 10) {
                    Monedas.getInstancia().compra(150 * this.tanques.size());
                    this.tanques.add(new Tanque<Pez>(25));
                } else {
                    System.out.println("No es posible comprar un nuevo tanque, llegaste al máximo");
                }
            } else {
                System.out.println("No tienes dinero suciciente");
            }
        } else {
            if (Monedas.getInstancia().comprobarPosible(600 * this.tanques.size())) {
                if (this.tanques.size() < 10) {
                    Monedas.getInstancia().compra(600 * this.tanques.size());
                    this.tanques.add(new Tanque<Pez>(100));
                } else {
                    System.out.println("No es posible comprar un nuevo tanque, llegaste al máximo");
                }
            } else {
                System.out.println("No tienes dinero suciciente");
            }
        }
    }

    public void showStatus() {

    }

    public void nuevoPez() {
        Scanner sc = new Scanner(System.in);
        int opcion=0;
        int pez = 0;
        try {
            do {
                this.listTanks();
                opcion = Integer.parseInt(sc.nextLine());
                if (opcion < 0 || opcion > this.tanques.size()) {
                    System.out.println("Opción no válida, introduce uno de los valores mostrados");
                } else {
                    if (this.tanques.get(opcion).getPeces().size() != 0) {
                        try {
                             this.tanques.get(opcion).comprarPez();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        do {
                            this.opcionPez();
                            try {
                                pez = Integer.parseInt(sc.nextLine());
                                System.out.println(pez);
                                if (pez > 1 && pez < 7) {
                                    this.añadirPez(opcion, pez);
                                } else {
                                    System.out.println("Opción no válida, introduce una de las opciones mostradas");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Opción no valida, introduce una de las opciones mostradas");
                            }
                        } while (pez < 1 || pez > 7);
                    }
                }
            } while (opcion < 1 || opcion > this.tanques.size());
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida");
        }
    }

    public void listTanks() {
        for (int i = 0; i < this.tanques.size(); i++) {
            if (this.tanques.get(i).getPeces().size() == 0) {
                System.out.println(i + ". Tanque vacío");
            } else {
                if (!(this.tanques.get(i).getPeces().size() == this.tanques.get(i).getCapacidad())) {
                    System.out
                            .println((i + 1) + ". Pez:" + this.tanques.get(i).getPeces().get(0).getDatos().getNombre());
                }
            }
        }
    }

    public void opcionPez() {
        if (this.rio) {
            System.out.println("******Peces******");
            System.out.println("1.Pejerrey");
            System.out.println("2.Lucio del norte");
            System.out.println("3.Salmón chinook");
            System.out.println("4.Perca europea");
            System.out.println("5.Carpa");
            System.out.println("6.Dorada");
            System.out.println("7.Trucha arcoiris");
        } else {
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

    public void añadirPez(int tanque, int pez) {
        if (this.rio) {
            if (this.tanques.get(tanque).getPeces().size() < this.tanques.get(tanque).getCapacidad()) {
                switch (pez) {
                    case 1:
                        this.tanques.get(tanque).comprarPez(new Pejerrey(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 2:
                        this.tanques.get(tanque).comprarPez(new LucioDelNorte(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 3:
                        this.tanques.get(tanque).comprarPez(new SalmonChinook(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 4:
                        this.tanques.get(tanque).comprarPez(new PercaEuropea(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 5:
                        this.tanques.get(tanque).comprarPez(new Carpa(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 6:
                        this.tanques.get(tanque).comprarPez(new Dorada(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 7:
                         this.tanques.get(tanque).comprarPez(
                                new TruchaArcoiris(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("El tanque está lleno y no se puede agregar el pez");
            }
        } else {
            if (this.tanques.get(tanque).getPeces().size() < this.tanques.get(tanque).getCapacidad()) {
                switch (pez) {
                    case 1:
                        this.tanques.get(tanque).comprarPez(new Robalo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 2:
                        this.tanques.get(tanque).comprarPez(new Caballa(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 3:
                        this.tanques.get(tanque).comprarPez(new LenguadoEuropeo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 4:
                        this.tanques.get(tanque).comprarPez(new Sargo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 5:
                        this.tanques.get(tanque).comprarPez(new Besugo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 6:
                        this.tanques.get(tanque).comprarPez(new Dorada(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 7:
                        this.tanques.get(tanque).comprarPez(new TruchaArcoiris(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("El tanque está lleno y no se puede agregar el pez");
            }
        }
    }
}