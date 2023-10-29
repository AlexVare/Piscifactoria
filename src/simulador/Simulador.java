package simulador;

import java.util.ArrayList;
import java.util.Scanner;

import almacenCentral.AlmacenCentral;
import monedero.Monedas;
import peces.Besugo;
import peces.Caballa;
import peces.Carpa;
import peces.Dorada;
import peces.LenguadoEuropeo;
import peces.LucioDelNorte;
import peces.Pejerrey;
import peces.PercaEuropea;
import peces.Robalo;
import peces.SalmonChinook;
import peces.Sargo;
import peces.TruchaArcoiris;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import stats.Stats;
import tanque.Tanque;

public class Simulador {

    private final String[] peces = { AlmacenPropiedades.BESUGO.getNombre(), AlmacenPropiedades.PEJERREY.getNombre(),
            AlmacenPropiedades.CARPA.getNombre(), AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
            AlmacenPropiedades.LUCIO_NORTE.getNombre(), AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
            AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
            AlmacenPropiedades.LENGUADO_EUROPEO.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
            AlmacenPropiedades.DORADA.getNombre(), AlmacenPropiedades.TRUCHA_ARCOIRIS.getNombre() };
    private int dias = 0;
    private String nombreCompa = "";
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<Piscifactoria>();
    private boolean almacenCentral = false;
    private static Scanner sc = new Scanner(System.in);

    public Simulador() {

    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        int salida = 0;

        try {
            simulador.init();
            do {
                salida = 0;
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
                        simulador.menuPisc(salida);
                        break;
                    case 2:
                        simulador.menuPisc(salida);
                        break;
                    case 3:
                        simulador.menuPisc(salida);
                        break;
                    case 4:
                        Stats.getInstancia().mostrar();
                        break;
                    case 5:
                        simulador.showIctio();
                        break;
                    case 6:
                        simulador.nuevoDia();
                        break;
                    case 7:

                        break;
                    case 8:
                        simulador.añadirPez();
                        break;
                    case 9:

                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                    case 12:
                        simulador.upgrade();
                        break;
                    case 13:

                        break;
                    case 98:

                        break;
                    case 99:
                        Monedas.getInstancia().setCantidad(1000);
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
        Monedas.getInstancia();
        Stats.getInstancia(peces);
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
        System.out.println("13. Pasar varios días");
        System.out.println("14. Salir");
    }

    public void menuPisc(int salida) {
        int contador = 1;
        System.out.println("Seleccione una opción:");
        System.out.println("--------------------- Piscifactorías ---------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for (Piscifactoria pisc : piscifactorias) {
            System.out.println(contador + ".-" + pisc.getNombre() + " [" + pisc.vivosTotales() + "/"
                    + pisc.pecesTotales() + "/" + pisc.capacidadTotal() + "]");
        }
        if (salida != 1) {
            try {
                int indice = Integer.parseInt(sc.nextLine());
                if (indice == 0) {

                } else if (indice < 1 || indice > this.piscifactorias.size()) {
                    System.out.println("Opoción inválida, retornando al menú principal");
                } else {
                    if (salida == 2) {
                        this.piscifactorias.get(indice-1).showStatus();
                    } else if (salida == 3) {
                        this.piscifactorias.get(indice-1).listTanks();
                        int tanque = Integer.parseInt(sc.nextLine());
                        if (tanque < 0 || tanque > this.piscifactorias.get(indice-1).getTanques().size()) {
                            System.out.println("Opción no válida, retornando al menú principal");
                        } else {
                            System.out.println("=============== Tanque " + tanque + "===============");
                            this.piscifactorias.get(indice-1).getTanques().get(tanque).showStatus();
                            this.piscifactorias.get(indice-1).getTanques().get(tanque).showFishStatus();
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida");
            }
        }
    }


    public void selecPisc() {
        for (int i = 0; i < this.piscifactorias.size(); i++) {
            System.out.println((i + 1) + ". " + this.piscifactorias.get(i).getNombre());
        }
    }

    public void añadirPez() {
        int pisc = 0;
        boolean salida=false;
        do {
            this.selecPisc();
            try {
                pisc = Integer.parseInt(sc.nextLine());
                if (pisc < 0 || pisc > this.piscifactorias.size()) {
                    System.out.println("Índice incorrecto, inserta un valor de los indicados");
                } else {
                    this.piscifactorias.get(pisc-1).nuevoPez();
                    salida=true;
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Argumento inválido, retrocediendo al menú principal");
                salida=true;
            }
        } while (!salida);
    }
    public void nuevoDia(){
        for (Piscifactoria pisc : piscifactorias) {
            pisc.nuevoDia();
        }
        this.dias++;
    }
    public void upgrade() {
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
                System.out.println("1. Piscifactoría");
                if (almacenCentral == false) {
                    System.out.println("2. Almacén central");
                }
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1:
                        boolean tipo = tipoPisc();
                        nuevaPisc(tipo);
                        break;
                    case 2:
                        if (almacenCentral == false) {
                            comprarAlmacen();
                        } else {
                            System.out.println("Opción no válida, retrocediendo al menú principal");
                        }
                        break;
                    default:
                        System.out.println("Opción no válida, retrocediendo al menú principal");
                        break;
                }
                break;
            case 2:
                System.out.println("******Mejorar edificios******");
                System.out.println("1. Piscifactoría");
                if (almacenCentral == true) {
                    System.out.println("2. Almacen central");
                }
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1:
                        int pisc = 0;
                        do {
                            this.selecPisc();
                            try {
                                pisc = Integer.parseInt(sc.nextLine());
                                if (pisc < 1 || pisc > this.piscifactorias.size()) {
                                    System.out.println("Índice incorrecto, inserta un valor de los indicados");
                                } else {
                                    do {
                                        System.out.println("******Mejorar piscifactoría******");
                                        System.out.println("1. Comprar tanque");
                                        System.out.println("2. Aumentar almacén");
                                        System.out.println("3. Cancelar");
                                        opcion = Integer.parseInt(sc.nextLine());
                                        switch (opcion) {
                                            case 1:
                                                this.piscifactorias.get(pisc - 1).comprarTanque();
                                                break;
                                            case 2:
                                                this.piscifactorias.get(pisc - 1).upgradeFood();
                                                break;
                                            case 3:
                                                opcion = 2;
                                                break;
                                            default:
                                                System.out
                                                        .println("Opción no válida, inserta un valor de los indicados");
                                                break;
                                        }
                                    } while (opcion != 1 || opcion != 2 || opcion != 3);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Opción no válida, inserta un valor de los indicados");
                            }
                        } while (pisc < 1 || pisc > this.piscifactorias.size());
                        break;
                    case 2:
                        if (almacenCentral == true) {
                            AlmacenCentral.getInstance().upgrade();
                        } else {
                            System.out.println("Opción no válida, retrocediendo al menú principal");
                        }
                        break;
                    default:
                        System.out.println("Opción no válida, retrocediendo al menú principal");
                        break;
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
            System.out.println("Selecciona el tipo de piscifactoría");
            System.out.println("1.Río");
            System.out.println("2.Mar");
            try {
                salida = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Argumento inválido");
            }
            if (salida == 1) {
                cosa = true;
            } else if (salida == 2) {
                cosa = false;
            } else {
                System.out.println("Opción no válida, vuelve a introducir tu elección");
            }
        } while (salida < 1 || salida > 2);
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

    public void nuevaPisc(boolean rio) {
        if (rio) {
            if (Monedas.getInstancia().comprobarPosible(this.rio() * 500)) {
                Monedas.getInstancia().compra(this.rio() * 500);
                String nompreP = nombrePisc();
                this.piscifactorias.add(new Piscifactoria(rio, nompreP));
            } else {
                System.out.println("No tienes suficientes monedas");
            }
        } else {
            if (this.mar() == 0) {
                if (Monedas.getInstancia().comprobarPosible(1 * 500)) {
                    Monedas.getInstancia().compra(this.mar() * 500);
                    String nompreP = nombrePisc();
                    this.piscifactorias.add(new Piscifactoria(rio, nompreP));
                } else {
                    System.out.println("No tienes suficientes monedas");
                }
            } else {
                if (Monedas.getInstancia().comprobarPosible(this.mar() * 500)) {
                    Monedas.getInstancia().compra(this.mar() * 500);
                    String nompreP = nombrePisc();
                    this.piscifactorias.add(new Piscifactoria(rio, nompreP));
                } else {
                    System.out.println("No tienes suficientes monedas");
                }
            }
        }
    }

    public String nombrePisc() {
        System.out.println("Introduce el nombre de la piscifactoría");
        return sc.nextLine();
    }

    public void comprarAlmacen() {
        if (Monedas.getInstancia().comprobarPosible(2000)) {
            Monedas.getInstancia().compra(2000);
            AlmacenCentral.getInstance();
            this.almacenCentral = true;
        } else {
            System.out.println("No tienes suficientes monedas");
        }
    }

    public void showIctio() {
        int opcion = 0;
        do {
            for (int i = 0; i < peces.length; i++) {
                System.out.println((i + 1) + ". " + peces[i]);
            }
            try {
                opcion = Integer.parseInt(sc.nextLine());
                if (opcion < 1 || opcion > peces.length) {
                    System.out.println("Opción no válida");
                } else {
                    switch (opcion) {
                        case 1:
                            Besugo.datos();
                            break;
                        case 2:
                            Pejerrey.datos();
                            break;
                        case 3:
                            Carpa.datos();
                            break;
                        case 4:
                            SalmonChinook.datos();
                            break;
                        case 5:
                            LucioDelNorte.datos();
                            break;
                        case 6:
                            PercaEuropea.datos();
                            break;
                        case 7:
                            Robalo.datos();
                            break;
                        case 8:
                            Caballa.datos();
                            break;
                        case 9:
                            LenguadoEuropeo.datos();
                            break;
                        case 10:
                            Sargo.datos();
                            break;
                        case 11:
                            Dorada.datos();
                            break;
                        case 12:
                            TruchaArcoiris.datos();
                            break;
                        default:
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida");
            }
        } while (opcion < 1 || opcion > peces.length);
    }
}
