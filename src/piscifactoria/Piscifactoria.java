package piscifactoria;

import java.io.Console;
import java.util.ArrayList;

import inputHelper.EscritorHelper;
import monedero.Monedas;
import peces.ArenqueAtlantico;
import peces.Besugo;
import peces.Caballa;
import peces.Carpa;
import peces.CarpaPlateada;
import peces.Dorada;
import peces.Pejerrey;
import peces.Pez;
import peces.Robalo;
import peces.SalmonChinook;
import peces.Sargo;
import peces.TilapiaNilo;
import peces.TruchaArcoiris;
import tanque.Tanque;

/**
 * La clase Piscifactoria representa una instalación de cría de peces con varios
 * tanques.
 */
public class Piscifactoria {

    private final boolean rio;
    private String nombre = "";
    private ArrayList<Tanque<Pez>> tanques = new ArrayList<>();
    private int almacen;
    private int almacenMax;

    /**
     * Constructor de la clase Piscifactoria.
     *
     * @param rio    Un valor booleano que indica si la piscifactoria está ubicada
     *               en un río o en el mar.
     * @param nombre El nombre de la piscifactoria.
     */
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

    public int getAlmacenMax() {
        return almacenMax;
    }

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int cant) {
        this.almacen = cant;
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

    public void addTanque() {
        if (this.rio) {
            this.tanques.add(new Tanque<>(25));
        } else {
            this.tanques.add(new Tanque<>(100));
        }
    }

    /**
     * Realiza las acciones correspondientes para avanzar un día en la
     * piscifactoria, incluyendo alimentar a los peces, permitir la reproducción y
     * realizar ventas.
     */
    public void nuevoDia(Boolean almacenCen) {
        for (int i = 0; i < this.tanques.size(); i++) {
            if (this.almacen != 0) {
                this.tanques.get(i).nuevoDiaComer(this, almacenCen);
                this.tanques.get(i).nuevoDiaReproduccion();
            }
            this.tanques.get(i).venderOptimos();
            System.out.println("Piscifactoria " + this.nombre + ": " + this.tanques.get(i).getVendidos()
                    + " peces vendidos por " + this.tanques.get(i).getGanancias() + " monedas");
        }
        this.gananciaDiaria();
    }

    /**
     * Vende todos los peces adultos en los tanques de la piscifactoria.
     */
    public void venderAdultos() {
        for (Tanque<Pez> tanque : tanques) {
            tanque.venderAdultos();
        }
    }

    /**
     * Calcula la ganancia diaria total de la piscifactoria y la muestra en la
     * consola.
     */
    public void gananciaDiaria() {
        int cantidad = 0;
        int ganancia = 0;
        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.getVendidos();
            ganancia += tanque.getGanancias();
        }
        System.out.println("Piscifactoria " + this.nombre + ": " + cantidad + " peces vendidos por " + ganancia
                + " monedas totales");
    }

    /**
     * Realiza una actualización de la capacidad de almacenamiento de comida en
     * función de la ubicación de la piscifactoria.
     * Para ubicaciones en río, la capacidad se incrementa en 25 unidades.
     * Para ubicaciones maritimas, la capacidad se incrementa en 100 unidades.
     */
    public void upgradeFood() {
        if (this.rio) {
            if (Monedas.getInstancia().comprobarPosible(100)) {
                if (this.almacenMax < 250) {
                    Monedas.getInstancia().compra(100);
                    this.almacenMax += 25;
                    EscritorHelper.getEscritorHelper("")
                            .addTrans("Mejorada la piscifactoría " + this.nombre
                                    + " aumentando su capacidad de comida hasta un total de " + this.almacenMax
                                    + "por un total de 100 monedas");
                    EscritorHelper.getEscritorHelper("").addLogs("Mejorada la piscifactoría " + this.nombre
                            + " aumentando su capacidad de comida");
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
                    EscritorHelper.getEscritorHelper("")
                            .addTrans("Mejorada la piscifactoría " + this.nombre
                                    + " aumentando su capacidad de comida hasta un total de " + this.almacenMax
                                    + "por un total de 200 monedas");
                    EscritorHelper.getEscritorHelper("").addLogs("Mejorada la piscifactoría " + this.nombre
                            + " aumentando su capacidad de comida");
                } else {
                    System.out.println("No puedes aumentar la capacidad");
                }
            } else {
                System.out.println("No tienes dinero suficiente");
            }
        }
    }

    /**
     * Permite comprar un nuevo tanque de peces en la piscifactoria.
     */
    public void comprarTanque() {
        if (this.rio) {
            if (Monedas.getInstancia().comprobarPosible(150 * this.tanques.size())) {
                if (this.tanques.size() < 10) {
                    Monedas.getInstancia().compra(150 * this.tanques.size());
                    EscritorHelper.getEscritorHelper("")
                            .addTrans("Comprado un tanque número " + (this.tanques.size() + 1) + "de la piscifactoria "
                                    + this.nombre);
                    EscritorHelper.getEscritorHelper("").addLogs("Comprado un tanque para la piscifactoria "
                            + this.nombre);
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
                    EscritorHelper.getEscritorHelper("")
                            .addTrans("Comprado un tanque número " + (this.tanques.size() + 1) + "de la piscifactoria "
                                    + this.nombre);
                    EscritorHelper.getEscritorHelper("").addLogs("Comprado un tanque para la piscifactoria "
                            + this.nombre);
                    this.tanques.add(new Tanque<Pez>(100));
                } else {
                    System.out.println("No es posible comprar un nuevo tanque, llegaste al máximo");
                }
            } else {
                System.out.println("No tienes dinero suciciente");
            }
        }
    }

    /**
     * Muestra el estado actual de la piscifactoria en la consola, incluyendo
     * información sobre la ocupación,
     * peces vivos, peces alimentados, peces adultos, hembras/machos y el
     * almacenamiento de comida.
     */
    public void showStatus() {
        System.out.println("==========" + this.nombre + "==========");
        System.out.println("Tanques: " + this.tanques.size());
        System.out.println("Ocupacion: " + this.pecesTotales() + "/" + this.capacidadTotal() + " ("
                + this.porcentaje(this.pecesTotales(), this.capacidadTotal()) + "%)");
        System.out.println("Peces vivos: " + this.vivosTotales() + "/" + this.pecesTotales() + " ("
                + this.porcentaje(this.vivosTotales(), this.pecesTotales()) + "%)");
        System.out.println("Peces alimentados: " + this.alimentadosTotales() + "/" + this.vivosTotales() + " ("
                + this.porcentaje(this.alimentadosTotales(), this.vivosTotales()) + "%)");
        System.out.println("Peces adultos: " + this.adultosTotales() + "/" + this.vivosTotales() + " ("
                + this.porcentaje(this.adultosTotales(), this.vivosTotales()) + "%)");
        System.out.println("Hembras/Machos: " + this.hembrasTotales() + "/" + this.machosTotales());
        System.out.println("almacen de comida actual: " + this.almacen + "/" + this.almacenMax + " ("
                + this.porcentaje(this.almacen, this.almacenMax) + "%)");
    }

    /**
     * Permite al jugador elegir un tanque para agregar un nuevo pez, ya sea
     * comprando un pez o eligiendo uno de los peces disponibles.
     */
    public void nuevoPez() {
        Console c = System.console();
        int opcion = 0;
        int pez = 0;
        boolean salida = false;
        try {
            do {
                this.listTanks();
                opcion = Integer.parseInt(c.readLine());
                if (opcion < 0 || opcion > this.tanques.size()) {
                    System.out.println("Opción no válida, introduce uno de los valores mostrados");
                } else {
                    if (this.tanques.get(opcion).getPeces().size() != 0) {
                        try {
                            this.tanques.get(opcion).comprarPez();
                            salida = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        do {
                            this.opcionPez();
                            try {
                                pez = Integer.parseInt(c.readLine());
                                System.out.println(pez);
                                if (pez > 0 && pez < 8) {
                                    this.añadirPez(opcion, pez);
                                    salida = true;
                                } else {
                                    System.out.println("Opción no válida, introduce una de las opciones mostradas");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Opción no valida, introduce una de las opciones mostradas");
                            }
                        } while (pez < 1 || pez > 7);
                    }
                }
            } while (!salida);
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida");
        }
    }

    /**
     * Muestra en la consola la lista de tanques disponibles en la piscifactoria.
     */
    public void listTanks() {
        for (int i = 0; i < this.tanques.size(); i++) {
            if (this.tanques.get(i).getPeces().size() == 0) {
                System.out.println(i + ". Tanque vacío");
            } else {
                System.out.println(i + ". Pez:" + this.tanques.get(i).getPeces().get(0).getDatos().getNombre());
            }
        }
    }

    /**
     * Muestra en la consola las opciones disponibles para agregar un nuevo pez en
     * funcion del tipo de piscifactoría.
     */
    public void opcionPez() {
        if (this.rio) {
            System.out.println("******Peces******");
            System.out.println("1.Pejerrey");
            System.out.println("2.Carpa plateada");
            System.out.println("3.Salmón chinook");
            System.out.println("4.Tilapia del Nilo");
            System.out.println("5.Carpa");
            System.out.println("6.Dorada");
            System.out.println("7.Trucha arcoiris");
        } else {
            System.out.println("******Peces******");
            System.out.println("1.Róbalo");
            System.out.println("2.Caballa");
            System.out.println("3.Arenque del Atlántico");
            System.out.println("4.Sargo");
            System.out.println("5.Besugo");
            System.out.println("6.Dorada");
            System.out.println("7.Trucha arcoiris");
        }
    }

    /**
     * Agrega un nuevo pez al tanque especificado en función de la elección del
     * jugador.
     *
     * @param tanque El índice del tanque al que se agregará el nuevo pez.
     * @param pez    El índice del tipo de pez a agregar.
     */
    public void añadirPez(int tanque, int pez) {
        if (this.rio) {
            if (this.tanques.get(tanque).getPeces().size() < this.tanques.get(tanque).getCapacidad()) {
                switch (pez) {
                    case 1:
                        this.tanques.get(tanque).comprarPez(new Pejerrey(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 2:
                        this.tanques.get(tanque).comprarPez(new CarpaPlateada(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 3:
                        this.tanques.get(tanque).comprarPez(new SalmonChinook(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 4:
                        this.tanques.get(tanque).comprarPez(new TilapiaNilo(this.tanques.get(tanque).sexoNuevoPez()));
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
                        this.tanques.get(tanque)
                                .comprarPez(new ArenqueAtlantico(this.tanques.get(tanque).sexoNuevoPez()));
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
                        this.tanques.get(tanque)
                                .comprarPez(new TruchaArcoiris(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("El tanque está lleno y no se puede agregar el pez");
            }
        }
    }

    /**
     * Calcula la cantidad total de peces vivos en la piscifactoria.
     *
     * @return La cantidad total de peces vivos.
     */
    public int vivosTotales() {
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.vivos();
        }
        return cantidad;
    }

    /**
     * Calcula la cantidad total de peces alimentados en la piscifactoria.
     *
     * @return La cantidad total de peces alimentados.
     */
    public int alimentadosTotales() {
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.alimentados();
        }
        return cantidad;
    }

    /**
     * Calcula la cantidad total de peces adultos en la piscifactoria.
     *
     * @return La cantidad total de peces adultos.
     */
    public int adultosTotales() {
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.adultos();
        }
        return cantidad;
    }

    /**
     * Calcula la cantidad total de machos en la piscifactoria.
     *
     * @return La cantidad total de machos.
     */
    public int machosTotales() {
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.machos();
        }
        return cantidad;
    }

    /**
     * Calcula la cantidad total de hembras en la piscifactoria.
     *
     * @return La cantidad total de hembras.
     */
    public int hembrasTotales() {
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.hembras();
        }
        return cantidad;
    }

    /**
     * Calcula la cantidad total de peces en la piscifactoria.
     *
     * @return La cantidad total de peces.
     */
    public int pecesTotales() {
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.getPeces().size();
        }
        return cantidad;
    }

    /**
     * Calcula la capacidad total de almacenamiento de peces en la piscifactoria.
     *
     * @return La capacidad total de almacenamiento de peces.
     */
    public int capacidadTotal() {
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.getCapacidad();
        }
        return cantidad;
    }

    /**
     * Calcula el porcentaje en función de dos números enteros.
     *
     * @param numero1 El primer número.
     * @param numero2 El segundo número (denominador).
     * @return El porcentaje calculado.
     */
    public double porcentaje(int numero1, int numero2) {
        if (numero2 == 0) {
            return 0.0;
        }

        double porcentaje = (double) numero1 / numero2 * 100;
        porcentaje = Math.round(porcentaje * 10) / 10.0;
        return porcentaje;
    }

    /**
     * Limpia los tanques de peces en la piscifactoria, eliminando los peces
     * muertos.
     */
    public void limpiarTanques() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).limpiarTanque();
            EscritorHelper.getEscritorHelper("")
                    .addTrans("Limpiado el tanque " + i + " de la piscifactoria " + this.nombre);
            EscritorHelper.getEscritorHelper("")
                    .addLogs("Limpiado el tanque " + i + " de la piscifactoria " + this.nombre);

        }
    }

    /**
     * Vacia los tanques de peces en la piscifactoria.
     */
    public void vaciarTanques() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).vaciarTanque();
            EscritorHelper.getEscritorHelper("")
                    .addTrans("Vaciado el tanque " + i + " de la piscifactoria " + this.nombre);
            EscritorHelper.getEscritorHelper("")
                    .addLogs("Vaciado el tanque " + i + " de la piscifactoria " + this.nombre);

        }
    }

    /**
     * Método para comprar más comida para el almacén
     * 
     * @param cantidad Cantidad de comida a agregar
     */
    public void agregarComida(int cantidad) {
        int costo;
        if (cantidad <= 25) {
            costo = cantidad;
        } else {
            costo = cantidad - (cantidad / 25) * 5;
        }

        if (Monedas.getInstancia().comprobarPosible(costo)) {
            this.almacen += cantidad;
            Monedas.getInstancia().compra(costo);
            if (this.almacen > this.almacenMax) {
                this.almacen = this.almacenMax;
            }
            System.out.println("Añadida " + cantidad + " de comida");
            EscritorHelper.getEscritorHelper("").addTrans(
                    cantidad + "de comida comprada por " + costo + ". Se almacena en la piscifactoria " + this.nombre);
            EscritorHelper.getEscritorHelper("")
                    .addLogs(cantidad + "de comida comprada. Se almacena en la piscifactoria " + this.nombre);
        } else {
            System.out.println("No tienes suficientes monedas para agregar comida.");
        }
    }

    public void setTanques(ArrayList<Tanque<Pez>> tanques) {
        this.tanques = tanques;
    }
}