package simulador;

import java.util.ArrayList;
import java.util.Scanner;

import monedero.Monedas;
import piscifactoria.Piscifactoria;

public class Simulador {

    private int dias = 0;
    private int npiscifactorias = 0;
    private String nombreCompa = "";
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<Piscifactoria>();
    private Monedas monedas= new Monedas(0);

    public Simulador() {

    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        Scanner sc = new Scanner(System.in);
        int salida = 0;

        try {
            System.out.println("Introduce el nombre de la compañía: ");
            String nombre = sc.nextLine();
            simulador.init(nombre);

            do {
                if (simulador.getDias() == 0) {
                    System.out.println("Bienvenido a " + simulador.getNombreCompa() + ", que desea hacer?");
                } else {
                    System.out.println("Bienvenido de nuevo a " + simulador.getNombreCompa() + ", que desea hacer?");
                }
                simulador.menu();
                salida = Integer.parseInt(sc.nextLine());

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
        return npiscifactorias;
    }

    public void setPiscifactorias(int piscifactorias) {
        this.npiscifactorias = piscifactorias;
    }

    public String getNombreCompa() {
        return nombreCompa;
    }

    public void setNombreCompa(String nombreCompa) {
        this.nombreCompa = nombreCompa;
    }

    public void init(String nombre) {
        this.monedas.setCantidad(100);
        this.piscifactorias.add(new Piscifactoria(true));
        this.setNombreCompa(nombre);
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
}
