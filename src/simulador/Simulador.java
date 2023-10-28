package simulador;

import java.util.ArrayList;
import java.util.Scanner;

import almacenCentral.AlmacenCentral;
import estadisticas.Estadisticas;
import monedero.Monedas;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Simulador {

    private int dias = 0;
    private String nombreCompa = "";
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<Piscifactoria>();
    private Monedas monedas = new Monedas(0);
    private AlmacenCentral almacenCentral = null;
    private static Scanner sc = new Scanner(System.in);

    public Simulador() {

    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        int salida = 0;

        try {
            simulador.init();
            do {
                System.out.println("Dia: " + simulador.getDias());
                if (simulador.getDias() == 0) {
                    System.out.println("Bienvenido a " + simulador.getNombreCompa() + ", que desea hacer?");
                } else {
                    System.out.println("Bienvenido de nuevo a " + simulador.getNombreCompa() + ", que desea hacer?");
                }
                simulador.menu();
                try {
                    salida = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Argumento inválido, introduce el entero correspondiente a la opción deseada");
                }
                switch (salida) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:

                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                    case 12:

                        break;
                    case 13:

                        break;
                    case 98:

                        break;
                    case 99:
                        simulador.monedas.setCantidad(1000);
                        break;
                    default:

                        break;
                }
            } while (salida != 14);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
    }

    public int getDias() {
        return dias;
    }

    public int getPiscifactorias() {
        return this.piscifactorias.size();
    }

    public String getNombreCompa() {
        return nombreCompa;
    }

    public void setNombreCompa(String nombreCompa) {
        this.nombreCompa = nombreCompa;
    }

    public void init() {
        System.out.println("Introduce el nombre de la compañía: ");
        String nombre = sc.nextLine();
        String nombreP = nombrePisc();
        this.monedas.setCantidad(100);
        this.setNombreCompa(nombre);
        this.piscifactorias.add(new Piscifactoria(true, nombreP));
    }

    public void menu() {
        System.out.println("******Menú******");
        System.out.println("1. Estado general");
        System.out.println("2. Estado piscifactoría");
        System.out.println("3. Estado tanques");
        System.out.println("4. Informes");
        System.out.println("5. Ictiopedia");
        System.out.println("6. Pasar día");
        System.out.println("7. Comprar comida");
        System.out.println("8. Comprar peces");
        System.out.println("9. Vender peces");
        System.out.println("10. Limpiar tanques");
        System.out.println("11. Vaciar tanques");
        System.out.println("12. Mejorar");
        System.out.println("13. Pasar varios dias");
        System.out.println("14. Salir");
    }

    public void menuPisc() {

    }

    public void selecPisc() {
        for (int i = 0; i < this.piscifactorias.size(); i++) {
            System.out.println((i + 1) + ". " + this.piscifactorias.get(i).getNombre());
        }
    }

    public void añadirPez(int piscifactoria, int tanque) {

    }

    public void menuMejoras() {
        int opcion = 0;
        System.out.println("******Mejoras******");
        System.out.println("1. Comprar edificios");
        System.out.println("2. Mejorar edificios");
        System.out.println("3. Cancelar");
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Argumento inválido, retrocediendo al menú principal");
        }
        switch (opcion) {
            case 1:
                System.out.println("******Comprar edificios******");
                System.out.println("1. Piscifactoria");
                if (almacenCentral == null) {
                    System.out.println("2. Almacén central");
                }
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1:
                        this.monedas = nuevaPisc(tipoPisc(), monedas);
                        break;
                    case 2:
                        if (almacenCentral == null) {
                            this.monedas = comprarAlmacen(monedas);
                        } else {
                            System.out.println();
                        }

                        break;
                    default:
                        System.out.println("Opción no válida, retrocediendo al menú principal");
                        break;
                }
                break;
            case 2:
                System.out.println("******Mejorar edificios******");
                System.out.println("1. Piscifactoria");
                if (almacenCentral != null) {
                    System.out.println("2. Almacen central");
                }
                break;
            case 3:
                break;
            default:
                System.out.println("Opción no válida, retrocediendo al menú principal");
                break;
        }
    }

    public boolean tipoPisc() {
        int salida = 0;
        boolean cosa = true;
        do {
            System.out.println("Selecciona el tipo de piscifactoria");
            System.out.println("1.Río");
            System.out.println("2.Mar");
            salida = Integer.parseInt(sc.nextLine());
            if (salida == 1) {
                cosa = true;
            } else if (salida == 2) {
                cosa = false;
            } else {
                System.out.println("Opción no valida, vuelve a introducir tu elección");
            }
        } while (salida != 1 && salida != 2);
        return cosa;
    }

    public int mar() {
        int numero = 0;
        for (Piscifactoria piscifactoria : piscifactorias) {
            if (!piscifactoria.isRio()) {
                numero++;
            }
        }
        return numero;
    }

    public int rio() {
        int numero = 0;
        for (Piscifactoria piscifactoria : piscifactorias) {
            if (piscifactoria.isRio()) {
                numero++;
            }
        }
        return numero;
    }

    public Monedas nuevaPisc(boolean rio, Monedas monedero) {
        if (rio) {
            if (monedero.comprobarPosible(this.rio() * 500)) {
                monedero.compra(this.rio() * 500);
                this.piscifactorias.add(new Piscifactoria(rio, nombrePisc()));
            } else {
                System.out.println("No tienes suficientes monedas");
            }
        } else {
            if (monedero.comprobarPosible(this.mar() * 500)) {
                monedero.compra(this.mar() * 500);
                this.piscifactorias.add(new Piscifactoria(rio, nombrePisc()));
            } else {
                System.out.println("No tienes suficientes monedas");
            }
        }
        return monedero;
    }

    public String nombrePisc() {
        System.out.println("Introduce el nombre de la piscifactoria");
        return sc.nextLine();
    }

    public Monedas comprarAlmacen(Monedas monedero) {
        if (monedero.comprobarPosible(2000)) {
            monedero.compra(2000);
            this.almacenCentral = new AlmacenCentral();
        } else {
            System.out.println("No tienes suficientes monedas");
        }
        return monedero;
    }
}
