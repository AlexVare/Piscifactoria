package simulador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import almacenCentral.AlmacenCentral;
import inputHelper.EscritorHelper;
import inputHelper.InputHelper;
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
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import rewards.Crear;
import rewards.Eliminar;
import stats.Stats;
import tanque.Tanque;

public class Simulador {

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        int salida = 0;
        int[] extras = { 97, 98, 99 };
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
                salida = InputHelper.inputMenu(1, 15, extras);
                System.out.println(salida);
                switch (salida) {
                    case 1:
                        simulador.showGeneralStatus();
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
                        simulador.nuevoDia(1);
                        break;
                    case 7:
                        simulador.addFood();
                        break;
                    case 8:
                        simulador.añadirPez();
                        break;
                    case 9:
                        simulador.venderPeces();
                        break;
                    case 10:
                        simulador.limpiarTanques();
                        break;
                    case 11:
                        simulador.vaciarTanques();
                        break;
                    case 12:
                        simulador.upgrade();
                        break;
                    case 13:
                        simulador.cobrarRecompensas();
                        break;
                    case 14:
                        int pasar;
                        System.out.println("Cuantos días quieres avanzar?");
                        pasar = InputHelper.inputOption(1, 100);
                        simulador.nuevoDia(pasar);
                        break;
                    case 15:
                        EscritorHelper.getEscritorHelper(nombreCompa).addLogs("Cierre de la partida");
                        simulador.save();
                        break;
                    case 97:
                        Crear.addAlmacen("A");
                        Crear.addAlmacen("B");
                        Crear.addAlmacen("C");
                        Crear.addAlmacen("D");
                        Crear.darComida(1);
                        Crear.darComida(2);
                        Crear.darComida(3);
                        Crear.darComida(4);
                        Crear.darComida(5);
                        Crear.darMonedas(1);
                        Crear.darMonedas(2);
                        Crear.darMonedas(3);
                        Crear.darMonedas(4);
                        Crear.darMonedas(5);
                        Crear.addPisci("A", "m");
                        Crear.addPisci("B", "m");
                        Crear.addPisci("A", "r");
                        Crear.addPisci("B", "r");
                        Crear.addTanque("m");
                        Crear.addTanque("r");
                        break;
                    case 98:
                        break;
                    case 99:
                        Monedas.getInstancia().agregarMonedos(1000);
                        EscritorHelper.getEscritorHelper(nombreCompa)
                                .addTrans("Añadidas 1000 monedas a través de la opción oculta. Monedas actuales:"
                                        + Monedas.getInstancia().getCantidad());
                        EscritorHelper.getEscritorHelper(nombreCompa)
                                .addLogs("Añadidas 1000 monedas a través de la opción oculta.");
                        break;
                    default:

                        break;
                }
            } while (salida != 15);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } finally {
            InputHelper.close();
            EscritorHelper.getEscritorHelper(nombreCompa).cerrarStreams();
        }
    }

    private final String[] peces = { AlmacenPropiedades.BESUGO.getNombre(), AlmacenPropiedades.PEJERREY.getNombre(),
            AlmacenPropiedades.CARPA.getNombre(), AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
            AlmacenPropiedades.CARPA_PLATEADA.getNombre(), AlmacenPropiedades.TILAPIA_NILO.getNombre(),
            AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
            AlmacenPropiedades.DORADA.getNombre(), AlmacenPropiedades.TRUCHA_ARCOIRIS.getNombre() };
    private int dias = 0;
    private static String nombreCompa = "";
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<Piscifactoria>();
    private boolean almacenCentral = false;

    public Simulador() {
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
        Simulador.nombreCompa = nombreCompa;
    }

    /**
     * Inicializa el simulador configurando el nombre de la compañía, creando una
     * piscifactoría inicial y otros elementos del juego.
     */
    public void init() {

        try {
            File saves = new File("Saves");
            if (saves.exists() && saves.listFiles().length != 0) {
                File[] files = saves.listFiles();
                System.out.println("----------Partidas disponibles---------");
                System.out.println("0. Nueva Partida");

                for (int ind = 0; ind < files.length; ind++) {
                    System.out.println((ind + 1) + "." + files[ind].getName());
                }
                System.out.println("Que partida quieres cargar?");
                int opcion = InputHelper.inputOption(0, files.length);
                if (opcion > 0) {
                    String partida = files[opcion - 1].getName();
                    this.load(partida);
                } else {
                    this.newGame();
                }
            } else {
                this.newGame();
            }

        } catch (SecurityException e) {
            EscritorHelper.getEscritorHelper(nombreCompa).addError("Error en el acceso a los archivos de guardado");
        }
    }

    public void newGame() {
        System.out.println("Introduce el nombre de la compañía: ");
        String nombre = InputHelper.inputTexto();
        System.out.println("Introduce el nombre de la primera piscifactoría: ");
        String nombreP = InputHelper.inputTexto();
        Monedas.getInstancia();
        Stats.getInstancia(peces);
        EscritorHelper.getEscritorHelper(nombre);
        this.setNombreCompa(nombre);
        this.piscifactorias.add(new Piscifactoria(true, nombreP));
        EscritorHelper.getEscritorHelper(nombre).addFirstLines(nombre, nombreP);
    }

    /**
     * Muestra el menú principal del juego.
     */
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
        System.out.println("13. Cobrar recompensas");
        System.out.println("14. Pasar varios días");
        System.out.println("15. Salir");
    }

    /**
     * Muestra el menú de selección de piscifactorías y permite al usuario
     * seleccionar una opción.
     *
     * @param salida El número que representa la salida a la que el usuario desea
     *               volver.
     */
    public void menuPisc(int salida) {
        int contador = 1;
        System.out.println("Seleccione una opción:");
        System.out.println("--------------------- Piscifactorías ---------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for (Piscifactoria pisc : piscifactorias) {
            System.out.println(contador + ".-" + pisc.getNombre() + " [" + pisc.vivosTotales() + "/"
                    + pisc.pecesTotales() + "/" + pisc.capacidadTotal() + "]");
        }

        int indice = InputHelper.inputOption(1, piscifactorias.size());
        if (indice == 0) {
        } else if (indice < 1 || indice > this.piscifactorias.size()) {
            System.out.println("Opoción inválida, retornando al menú principal");
        } else {
            if (salida == 2) {
                this.piscifactorias.get(indice - 1).showStatus();
            } else if (salida == 3) {
                this.piscifactorias.get(indice - 1).listTanks();
                int tanque = InputHelper.inputOption(0, piscifactorias.get(indice - 1).getTanques().size());
                if (tanque < 0 || tanque > this.piscifactorias.get(indice - 1).getTanques().size()) {
                    System.out.println("Opción no válida, retornando al menú principal");
                } else {
                    System.out.println("=============== Tanque " + tanque + "===============");
                    this.piscifactorias.get(indice - 1).getTanques().get(tanque).showStatus();
                    this.piscifactorias.get(indice - 1).getTanques().get(tanque).showFishStatus();
                }
            }
        }
    }

    /**
     * Muestra el estado general de las piscifactorías, los días transcurridos y las
     * monedas disponibles. De haber un Almacén central, muestra su estado también.
     */
    public void showGeneralStatus() {
        for (Piscifactoria pisc : piscifactorias) {
            pisc.showStatus();
        }
        System.out.println("Dia: " + this.dias);
        System.out.println(Monedas.getInstancia().getCantidad() + " monedas disponibles");
        if (almacenCentral == true) {
            System.out.println("Almacen central: " + AlmacenCentral.getInstance().getCapacidad() + "/"
                    + AlmacenCentral.getInstance().getCapacidadMax() + " ("
                    + this.piscifactorias.get(0).porcentaje(AlmacenCentral.getInstance().getCapacidad(),
                            AlmacenCentral.getInstance().getCapacidadMax())
                    + "%) comida vegetal");
        }
    }

    /**
     * Muestra las opciones de selección de piscifactorías disponibles.
     */
    public void selecPisc() {
        for (int i = 0; i < this.piscifactorias.size(); i++) {
            System.out.println((i + 1) + ". " + this.piscifactorias.get(i).getNombre());
        }
    }

    /**
     * Verifica la existencia de archivos de recompensas y muestra los resultados.
     * Se crean valores booleanos en un array para cada tipo de recompensa,
     * indicando si el archivo correspondiente existe o no.
     * Luego, se muestra el estado de las recompensas en la consola.
     */
    public void cobrarRecompensas() {

        boolean[] recompensas = new boolean[20];

        recompensas[0] = new File("recompensas/almacen_a.xml").exists();
        recompensas[1] = new File("recompensas/almacen_b.xml").exists();
        recompensas[2] = new File("recompensas/almacen_c.xml").exists();
        recompensas[3] = new File("recompensas/almacen_d.xml").exists();
        recompensas[4] = new File("recompensas/comida_1.xml").exists();
        recompensas[5] = new File("recompensas/comida_2.xml").exists();
        recompensas[6] = new File("recompensas/comida_3.xml").exists();
        recompensas[7] = new File("recompensas/comida_4.xml").exists();
        recompensas[8] = new File("recompensas/comida_5.xml").exists();
        recompensas[9] = new File("recompensas/monedas_1.xml").exists();
        recompensas[10] = new File("recompensas/monedas_2.xml").exists();
        recompensas[11] = new File("recompensas/monedas_3.xml").exists();
        recompensas[12] = new File("recompensas/monedas_4.xml").exists();
        recompensas[13] = new File("recompensas/monedas_5.xml").exists();
        recompensas[14] = new File("recompensas/pisci_m_a.xml").exists();
        recompensas[15] = new File("recompensas/pisci_m_b.xml").exists();
        recompensas[16] = new File("recompensas/pisci_r_a.xml").exists();
        recompensas[17] = new File("recompensas/pisci_r_b.xml").exists();
        recompensas[18] = new File("recompensas/tanque_m.xml").exists();
        recompensas[19] = new File("recompensas/tanque_r.xml").exists();

        mostrar(recompensas);

    }

    /**
     * Muestra el estado de las recompensas en la consola y proporciona opciones
     * para reclamar cada recompensa.
     * 
     * @param recompensas Un array de booleanos que indica la existencia de cada
     *                    tipo de recompensa.
     */
    public void mostrar(boolean[] recompensas) {

        String almacenA = "x";
        String almacenB = "x";
        String almacenC = "x";
        String almacenD = "x";

        String PiscMA = "x";
        String PiscMB = "x";

        String PiscRA = "x";
        String PiscRB = "x";

        almacenA = (recompensas[0]) ? "A" : almacenA;
        almacenB = (recompensas[1]) ? "B" : almacenB;
        almacenC = (recompensas[2]) ? "C" : almacenC;
        almacenD = (recompensas[3]) ? "D" : almacenD;

        PiscMA = (recompensas[14]) ? "A" : PiscMA;
        PiscMB = (recompensas[15]) ? "B" : PiscMB;

        PiscRA = (recompensas[16]) ? "A" : PiscRA;
        PiscRB = (recompensas[17]) ? "B" : PiscRB;

        if (almacenA.contains("x") || almacenB.contains("x") || almacenC.contains("x") || almacenD.contains("x")) {
            System.out.println("Faltan partes de almacén central: " + almacenA + almacenB + almacenC + almacenD);
        } else {
            System.out.println("1. Reclamar almacén " + almacenA + almacenB + almacenC + almacenD + ":");
        }
        System.out.print((recompensas[4]) ? "2. Reclamar comida I\n" : "");
        System.out.print((recompensas[5]) ? "3. Reclamar comida II\n" : "");
        System.out.print((recompensas[6]) ? "4. Reclamar comida III\n" : "");
        System.out.print((recompensas[7]) ? "5. Reclamar comida IV\n" : "");
        System.out.print((recompensas[8]) ? "6. Reclamar comida V\n" : "");
        System.out.print((recompensas[9]) ? "7. Reclamar monedas I\n" : "");
        System.out.print((recompensas[10]) ? "8. Reclamar monedas II\n" : "");
        System.out.print((recompensas[11]) ? "9. Reclamar monedas III\n" : "");
        System.out.print((recompensas[12]) ? "10. Reclamar monedas IV\n" : "");
        System.out.print((recompensas[13]) ? "11. Reclamar monedas V" : "");
        if (PiscMA.contains("x") || PiscMB.contains("x")) {
            System.out.println("Faltan partes de la piscifactoría marina: " + PiscMA + PiscMB);
        } else {
            System.out.println("12. Reclamar piscifactoría de mar " + PiscMA + PiscMB + ":");
        }
        if (PiscRA.contains("x") || PiscRB.contains("x")) {
            System.out.print("Faltan partes de la piscifactoria fluvial: " + PiscRA + PiscRB);
        } else {
            System.out.println("13. Reclamar piscifactoría de río " + PiscRA + PiscRB + ":");
        }
        System.out.print((recompensas[18]) ? "14. Reclamar tanque de mar" : "");
        System.out.print((recompensas[19]) ? "15. Reclamar tanque de río" : "");
        System.out.println("\n 0. Salir");

        seleccionarOpcion(recompensas);

    }

    /**
     * Permite al usuario seleccionar una opción para reclamar una recompensa
     * específica.
     * 
     * @param recompensas Un array de booleanos que indica la existencia de cada
     *                    tipo de recompensa.
     */
    public void seleccionarOpcion(boolean[] recompensas) {

        boolean control = true;
        int opcion;

        do {
            System.out.print("Selecciona una opción: ");
            opcion = InputHelper.inputOption(0, 15);

            switch (opcion) {
                case 1:
                    if (Eliminar.obtenerDatos("recompensas/almacen_a.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/almacen_b.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/almacen_c.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/almacen_d.xml") >= 1) {
                        System.out.println("Reclamando almacén...");
                        if (AlmacenCentral.getInstance() == null) {
                            Eliminar.dismnuirRecompensas("recompensas/almacen_a.xml");
                            Eliminar.dismnuirRecompensas("recompensas/almacen_b.xml");
                            Eliminar.dismnuirRecompensas("recompensas/almacen_c.xml");
                            Eliminar.dismnuirRecompensas("recompensas/almacen_d.xml");
                            Eliminar.obtenerDatos("recompensas/almacen_a.xml");
                            if (Eliminar.obtenerDatos("recompensas/almacen_a.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_a.xml");
                            }
                            if (Eliminar.obtenerDatos("recompensas/almacen_b.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_b.xml");
                            }
                            if (Eliminar.obtenerDatos("recompensas/almacen_c.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_c.xml");
                            }
                            if (Eliminar.obtenerDatos("recompensas/almacen_d.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_d.xml");
                            }
                        } else {
                            System.out.println("Ya tienes el almacén central");
                        }
                    }
                    control = false;
                    break;
                case 2:
                    if (Eliminar.obtenerDatos("recompensas/comida_1.xml") >= 1) {
                        System.out.println("Reclamando comida I...");
                        int restante1 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante1 < 50) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante1));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 50));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_1.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_1.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_1.xml");
                        }
                    }
                    control = false;
                    break;
                case 3:
                    if (Eliminar.obtenerDatos("recompensas/comida_2.xml") >= 1) {
                        System.out.println("Reclamando comida II...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 100));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_2.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_2.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_2.xml");
                        }
                    }
                    control = false;
                    break;
                case 4:
                    if (Eliminar.obtenerDatos("recompensas/comida_3.xml") >= 1) {
                        System.out.println("Reclamando comida III...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 250));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_3.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_3.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_3.xml");
                        }
                    }
                    control = false;
                    break;
                case 5:
                    if (Eliminar.obtenerDatos("recompensas/comida_4.xml") >= 1) {
                        System.out.println("Reclamando comida IV...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 500));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_4.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_4.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_4.xml");
                        }
                    }
                    control = false;
                    break;
                case 6:
                    if (Eliminar.obtenerDatos("recompensas/comida_5.xml") >= 1) {
                        System.out.println("Reclamando comida V...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 1000));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_5.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_5.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_5.xml");
                        }
                    }
                    control = false;
                    break;
                case 7:
                    if (Eliminar.obtenerDatos("recompensas/monedas_1.xml") >= 1) {
                        System.out.println("Reclamando monedas I...");
                        Monedas.getInstancia().setCantidad(Monedas.getInstancia().getCantidad() + 100);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_1.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_1.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_1.xml");
                    }
                    control = false;
                    break;
                case 8:
                    if (Eliminar.obtenerDatos("recompensas/monedas_2.xml") >= 1) {
                        System.out.println("Reclamando monedas II...");
                        Monedas.getInstancia().setCantidad(Monedas.getInstancia().getCantidad() + 300);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_2.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_2.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_2.xml");
                    }
                    control = false;
                    break;
                case 9:
                    if (Eliminar.obtenerDatos("recompensas/monedas_3.xml") >= 1) {
                        System.out.println("Reclamando monedas III...");
                        Monedas.getInstancia().setCantidad(Monedas.getInstancia().getCantidad() + 500);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_3.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_3.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_3.xml");
                    }
                    control = false;
                    break;
                case 10:
                    if (Eliminar.obtenerDatos("recompensas/monedas_4.xml") >= 1) {
                        System.out.println("Reclamando monedas IV...");
                        Monedas.getInstancia().setCantidad(Monedas.getInstancia().getCantidad() + 750);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_4.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_4.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_4.xml");
                    }
                    control = false;
                    break;
                case 11:
                    if (Eliminar.obtenerDatos("recompensas/monedas_5.xml") >= 1) {
                        System.out.println("Reclamando monedas V...");
                        Monedas.getInstancia().setCantidad(Monedas.getInstancia().getCantidad() + 1000);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_5.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_5.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_5.xml");
                    }
                    control = false;
                    break;
                case 12:
                    if (Eliminar.obtenerDatos("recompensas/pisci_m_a.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/pisci_m_b.xml") >= 1) {
                        System.out.println("Reclamando piscifactoría de mar...");
                        System.out.print("Nombre de la Piscifactoria: ");
                        String nombrePiscMar = InputHelper.inputTexto();
                        piscifactorias.add(new Piscifactoria(false, nombrePiscMar));
                    }
                    Eliminar.dismnuirRecompensas("recompensas/pisci_m_a.xml");
                    Eliminar.dismnuirRecompensas("recompensas/pisci_m_b.xml");
                    if (Eliminar.obtenerDatos("recompensas/pisci_m_a.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_m_a.xml");
                    }
                    if (Eliminar.obtenerDatos("recompensas/pisci_m_b.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_m_b.xml");
                    }
                    control = false;
                    break;
                case 13:
                    if (Eliminar.obtenerDatos("recompensas/pisci_r_a.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/pisci_r_b.xml") >= 1) {
                        System.out.println("Reclamando piscifactoría de río...");
                        System.out.print("Nombre de la Piscifactoria: ");
                        String nombrePiscMar = InputHelper.inputTexto();
                        piscifactorias.add(new Piscifactoria(true, nombrePiscMar));
                    }
                    Eliminar.dismnuirRecompensas("recompensas/pisci_r_a.xml");
                    Eliminar.dismnuirRecompensas("recompensas/pisci_r_b.xml");
                    if (Eliminar.obtenerDatos("recompensas/pisci_r_a.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_r_a.xml");
                    }
                    if (Eliminar.obtenerDatos("recompensas/pisci_r_b.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_r_b.xml");
                    }
                    control = false;
                    break;
                case 14:
                    if (Eliminar.obtenerDatos("recompensas/tanque_m.xml") >= 1) {
                        System.out.println("Reclamando tanque de mar...");

                        this.selecPisc();
                        Piscifactoria pisc = this.piscifactorias
                                .get(InputHelper.inputOption(0, piscifactorias.size()));
                        if (pisc.getTanques().size() < 10) {
                            pisc.addTanque();
                            ;
                        }
                    }
                    Eliminar.dismnuirRecompensas("recompensas/tanque_m.xml");
                    if (Eliminar.obtenerDatos("recompensas/tanque_m.xml") < 1) {
                        Eliminar.borrarXML("recompensas/tanque_m.xml");
                    }
                    control = false;
                    break;
                case 15:
                    if (Eliminar.obtenerDatos("recompensas/tanque_r.xml") >= 1) {
                        System.out.println("Reclamando tanque de río...");

                        this.selecPisc();
                        Piscifactoria pisc = this.piscifactorias
                                .get(InputHelper.inputOption(0, piscifactorias.size()));
                        if (pisc.getTanques().size() < 10) {
                            pisc.addTanque();
                            ;
                        }
                    }
                    Eliminar.dismnuirRecompensas("recompensas/tanque_r.xml");
                    if (Eliminar.obtenerDatos("recompensas/tanque_r.xml") < 1) {
                        Eliminar.borrarXML("recompensas/tanque_r.xml");
                    }
                    control = false;
                    break;
                default:
                    System.out.println("Selecciona una opción válida :(");
            }
        } while (control);
    }

    /**
     * Limpia los peces muertos de todas las piscifactorías.
     */
    public void limpiarTanques() {
        for (Piscifactoria pisc : piscifactorias) {
            pisc.limpiarTanques();
        }
    }

    /**
     * Vacia los tanques de todas las piscifactorías.
     */
    public void vaciarTanques() {
        for (Piscifactoria pisc : piscifactorias) {
            pisc.vaciarTanques();
        }
    }

    /***
     * Método para comprar comida
     * 
     */
    public void addFood() {
        if (!almacenCentral) {
            this.selecPisc();
            int pisc = InputHelper.inputOption(0, piscifactorias.size());

            System.out.println("Opciones de comida:");
            System.out.println("1. Añadir 5");
            System.out.println("2. Añadir 10");
            System.out.println("3. Añadir 25");
            System.out.println("4. Llenar");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int opcion = InputHelper.inputOption(1, 5);

            switch (opcion) {
                case 1:
                    this.piscifactorias.get(pisc - 1).agregarComida(5);
                    break;
                case 2:
                    this.piscifactorias.get(pisc - 1).agregarComida(10);
                    break;
                case 3:
                    this.piscifactorias.get(pisc - 1).agregarComida(25);
                    break;
                case 4:
                    this.piscifactorias.get(pisc - 1).agregarComida(this.piscifactorias.get(pisc - 1).getAlmacenMax()
                            - this.piscifactorias.get(pisc - 1).getAlmacen());
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("Opciones de comida:");
            System.out.println("1. Añadir 5");
            System.out.println("2. Añadir 10");
            System.out.println("3. Añadir 25");
            System.out.println("4. Llenar");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int opcion = InputHelper.inputOption(1, 5);
            switch (opcion) {
                case 1:
                    AlmacenCentral.getInstance().comprarComida(5);
                    break;
                case 2:
                    AlmacenCentral.getInstance().comprarComida(10);
                    break;
                case 3:
                    AlmacenCentral.getInstance().comprarComida(25);
                    break;
                case 4:
                    AlmacenCentral.getInstance().comprarComida(AlmacenCentral.getInstance().getCapacidadMax()
                            - AlmacenCentral.getInstance().getCapacidad());
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Permite al usuario seleccionar una piscifactoría y añadir un pez a un tanque
     * en esa piscifactoría.
     */
    public void añadirPez() {
        int pisc = 0;
        boolean salida = false;
        do {
            this.selecPisc();

            pisc = InputHelper.inputOption(0, piscifactorias.size());
            if (pisc < 0 || pisc > this.piscifactorias.size()) {
                System.out.println("Índice incorrecto, inserta un valor de los indicados");
            } else {
                this.piscifactorias.get(pisc - 1).nuevoPez();
                salida = true;
            }

        } while (!salida);
    }

    /**
     * Permite al usuario avanzar un número específico de días en la simulación.
     *
     * @param dias La cantidad de días que se avanzarán en la simulación.
     */
    public void nuevoDia(int dias) {
        for (int i = 0; i < dias; i++) {
            for (Piscifactoria pisc : piscifactorias) {
                pisc.nuevoDia(this.almacenCentral);
            }
            EscritorHelper.getEscritorHelper("")
                    .addTrans("Fin del día" + this.dias);
            EscritorHelper.getEscritorHelper(nombreCompa).addLogs("Fin del día" + this.dias);
            EscritorHelper.getEscritorHelper("")
                    .addTrans("---------------------------------------");
            this.dias++;
            EscritorHelper.getEscritorHelper("")
                    .addTrans("Inicio del día " + this.dias);
        }
    }

    /**
     * Muestra el menú de actualización de edificios, permitiendo al usuario comprar
     * o mejorar edificios, o cancelar la operación.
     */
    public void upgrade() {
        int opcion = mostrarMenuMejoras();
        switch (opcion) {
            case 1:
                comprarEdificios();
                break;
            case 2:
                mejorarEdificios();
                break;
            case 3:
                break;
            default:
                System.out.println("Opción no válida, retrocediendo al menú principal");
                break;
        }
    }

    private int mostrarMenuMejoras() {
        System.out.println("******Mejoras******");
        System.out.println("1. Comprar edificios");
        System.out.println("2. Mejorar edificios");
        System.out.println("3. Cancelar");
        int salida = InputHelper.inputOption(1, 3);
        if (salida == 3) {
            return 3;
        } else {
            return salida; // Opción por defecto al producirse una excepción
        }
    }

    private void comprarEdificios() {
        System.out.println("******Comprar edificios******");
        System.out.println("1. Piscifactoría");
        if (!almacenCentral) {
            System.out.println("2. Almacén central");
        }
        int opcion = InputHelper.inputOption(1, 2);
        switch (opcion) {
            case 1:
                comprarPiscifactoria();
                break;
            case 2:
                if (!almacenCentral) {
                    comprarAlmacenCentral();
                }
                break;
            default:
                System.out.println("Opción no válida, retrocediendo al menú principal");
                break;
        }
    }

    private void comprarPiscifactoria() {
        boolean tipo = tipoPisc();
        nuevaPisc(tipo);
    }

    private void comprarAlmacenCentral() {
        if (!almacenCentral) {

            comprarAlmacen();
        } else {
            System.out.println("Opción no válida, retrocediendo al menú principal");
        }
    }

    private void mejorarEdificios() {
        System.out.println("******Mejorar edificios******");
        System.out.println("1. Piscifactoría");
        if (almacenCentral) {
            System.out.println("2. Almacen central");
        }
        int opcion = InputHelper.inputOption(1, 2);
        switch (opcion) {
            case 1:
                mejorarPiscifactoria();
                break;
            case 2:
                if (almacenCentral) {
                    mejorarAlmacenCentral();
                }
                break;
            default:
                System.out.println("Opción no válida, retrocediendo al menú principal");
                break;
        }
    }

    private void mejorarPiscifactoria() {
        int pisc = seleccionarPiscifactoria();
        int opcion = 0;
        do {
            try {
                opcion = mostrarMenuMejoraPiscifactoria();
                switch (opcion) {
                    case 1:
                        piscifactorias.get(pisc - 1).comprarTanque();
                        opcion = 3;
                        break;
                    case 2:
                        piscifactorias.get(pisc - 1).upgradeFood();
                        opcion = 3;
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Opción no válida, inserta un valor de los indicados");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida, inserta un valor de los indicados");
            }
        } while (opcion != 3);
    }

    private int seleccionarPiscifactoria() {
        int pisc = 0;
        do {
            mostrarPiscifactorias();
            try {
                pisc = InputHelper.inputOption(1, piscifactorias.size());
                if (pisc < 1 || pisc > piscifactorias.size()) {
                    System.out.println("Índice incorrecto, inserta un valor de los indicados");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida, inserta un valor de los indicados");
            }
        } while (pisc < 1 || pisc > piscifactorias.size());
        return pisc;
    }

    private void mostrarPiscifactorias() {
        for (int i = 0; i < piscifactorias.size(); i++) {
            System.out.println((i + 1) + ". " + piscifactorias.get(i).getNombre());
        }
    }

    private int mostrarMenuMejoraPiscifactoria() {
        System.out.println("******Mejorar piscifactoría******");
        System.out.println("1. Comprar tanque");
        System.out.println("2. Aumentar almacén");
        System.out.println("3. Cancelar");
        return InputHelper.inputOption(1, 3);
    }

    private void mejorarAlmacenCentral() {
        if (almacenCentral) {
            AlmacenCentral.getInstance().upgrade();
        } else {
            System.out.println("Opción no válida, retrocediendo al menú principal");
        }
    }

    /**
     * Permite al usuario seleccionar el tipo de piscifactoría (río o mar) y
     * devuelve un valor booleano correspondiente.
     *
     * @return true si se selecciona río, false si se selecciona mar.
     */
    public boolean tipoPisc() {
        int salida = 0;
        boolean cosa = true;
        do {
            System.out.println("Selecciona el tipo de piscifactoría");
            System.out.println("1.Río");
            System.out.println("2.Mar");

            salida = InputHelper.inputOption(1, 2);

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

    /**
     * Calcula el número de piscifactorías de tipo "mar".
     *
     * @return Número de piscifactorías de tipo "mar".
     */
    public int mar() {
        int numero = 0;
        for (Piscifactoria piscifactoria : piscifactorias) {
            if (!piscifactoria.isRio()) {
                numero++;
            }
        }
        return numero;
    }

    /**
     * Calcula el número de piscifactorías de tipo "río".
     *
     * @return Número de piscifactorías de tipo "río".
     */
    public int rio() {
        int numero = 0;
        for (Piscifactoria piscifactoria : piscifactorias) {
            if (piscifactoria.isRio()) {
                numero++;
            }
        }
        return numero;
    }

    /**
     * Crea una nueva piscifactoría, ya sea de tipo río o mar, dependiendo del valor
     * de la variable "rio".
     * Si no hay piscifactorías de tipo mar, se asume un costo de 500 monedas.
     *
     * @param rio true si la piscifactoría es de tipo río, false si es de tipo mar.
     */
    public void nuevaPisc(boolean rio) {
        if (rio) {
            if (Monedas.getInstancia().comprobarPosible(this.rio() * 500)) {
                Monedas.getInstancia().compra(this.rio() * 500);
                String nompreP = nombrePisc();
                EscritorHelper.getEscritorHelper("")
                        .addTrans("Comprada la piscifactoria de río " + nompreP + "por " + (this.rio() * 500)
                                + "monedas");
                EscritorHelper.getEscritorHelper("").addLogs("Comprada la piscifactoria de río " + nompreP);
                this.piscifactorias.add(new Piscifactoria(rio, nompreP));
            } else {
                System.out.println("No tienes suficientes monedas");
            }
        } else {
            if (this.mar() == 0) {
                if (Monedas.getInstancia().comprobarPosible(500)) {
                    Monedas.getInstancia().compra(500);
                    String nompreP = nombrePisc();
                    EscritorHelper.getEscritorHelper("")
                            .addTrans("Comprada la piscifactoria de mar " + nompreP + "por 500 monedas");
                    EscritorHelper.getEscritorHelper("").addLogs("Comprada la piscifactoria de mar " + nompreP);
                    this.piscifactorias.add(new Piscifactoria(rio, nompreP));
                } else {
                    System.out.println("No tienes suficientes monedas");
                }
            } else {
                if (Monedas.getInstancia().comprobarPosible(this.mar() * 500)) {
                    Monedas.getInstancia().compra(this.mar() * 500);
                    String nompreP = nombrePisc();
                    EscritorHelper.getEscritorHelper("")
                            .addTrans("Comprada la piscifactoria de mar " + nompreP + "por " + (this.mar() * 500)
                                    + "monedas");
                    EscritorHelper.getEscritorHelper("").addLogs("Comprada la piscifactoria de mar " + nompreP);

                    this.piscifactorias.add(new Piscifactoria(rio, nompreP));
                } else {
                    System.out.println("No tienes suficientes monedas");
                }
            }
        }
    }

    /**
     * Solicita al usuario introducir el nombre de una piscifactoría y devuelve la
     * entrada del usuario como una cadena de texto.
     *
     * @return El nombre de la piscifactoría ingresado por el usuario.
     */
    public String nombrePisc() {
        System.out.println("Introduce el nombre de la piscifactoría");
        return InputHelper.inputTexto();
    }

    /**
     * Permite al usuario comprar un almacén central si aún no ha sido adquirido.
     * Comprueba si hay suficientes monedas y realiza la compra si es posible.
     * Si la compra se realiza con éxito, se activa el almacén central y se
     * establece el indicador "almacenCentral" en true.
     */
    public void comprarAlmacen() {
        if (Monedas.getInstancia().comprobarPosible(2000)) {
            Monedas.getInstancia().compra(2000);
            AlmacenCentral.getInstance();
            EscritorHelper.getEscritorHelper("")
                    .addTrans("Comprado el almacén central");
            this.almacenCentral = true;
        } else {
            System.out.println("No tienes suficientes monedas");
        }
    }

    /**
     * Muestra información detallada sobre las especies de peces disponibles,
     * permitiendo al usuario seleccionar una para obtener más detalles.
     */
    public void showIctio() {
        int opcion = 0;
        do {
            for (int i = 0; i < peces.length; i++) {
                System.out.println((i + 1) + ". " + peces[i]);
            }
            try {
                opcion = InputHelper.inputOption(1, peces.length);
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
                            CarpaPlateada.datos();
                            break;
                        case 6:
                            TilapiaNilo.datos();
                            break;
                        case 7:
                            Robalo.datos();
                            break;
                        case 8:
                            Caballa.datos();
                            break;
                        case 9:
                            ArenqueAtlantico.datos();
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

    /**
     * Vende peces adultos en todas las piscifactorías.
     * Luego, muestra las estadísticas relacionadas con la venta de peces.
     */
    public void venderPeces() {
        for (Piscifactoria pisc : piscifactorias) {
            pisc.venderAdultos();
        }
        Stats.getInstancia().mostrar();
    }

    public void save() {
        guardar();
        System.out.println("Save");
        EscritorHelper.getEscritorHelper(nombreCompa).addLogs("Partida guardada");
        System.exit(0);
    }

    /**
     * Guarda el estado actual del una partida en un archivo JSON.
     * Los datos guardados incluyen la información de la empresa, día, monedas,
     * estadísticas y detalles de las piscifactorías,
     * tanques y peces en el sistema.
     */
    public void guardar() {
        JsonObject estructuraJson = new JsonObject();
        JsonArray pecesImplementados = new JsonArray();
        pecesImplementados.add(AlmacenPropiedades.TILAPIA_NILO.getNombre());
        pecesImplementados.add(AlmacenPropiedades.LUCIO_NORTE.getNombre());
        pecesImplementados.add(AlmacenPropiedades.CORVINA.getNombre());
        pecesImplementados.add(AlmacenPropiedades.SALMON_CHINOOK.getNombre());
        pecesImplementados.add(AlmacenPropiedades.PEJERREY.getNombre());

        pecesImplementados.add(AlmacenPropiedades.LENGUADO_EUROPEO.getNombre());
        pecesImplementados.add(AlmacenPropiedades.CABALLA.getNombre());
        pecesImplementados.add(AlmacenPropiedades.ROBALO.getNombre());
        pecesImplementados.add(AlmacenPropiedades.LUBINA_EUROPEA.getNombre());
        pecesImplementados.add(AlmacenPropiedades.BESUGO.getNombre());

        pecesImplementados.add(AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre());
        pecesImplementados.add(AlmacenPropiedades.SALMON_ATLANTICO.getNombre());

        estructuraJson.add("implementados", pecesImplementados);
        // Añadir campos al objeto JSON
        estructuraJson.addProperty("empresa", this.getNombreCompa());
        estructuraJson.addProperty("dia", dias);
        estructuraJson.addProperty("monedas", Monedas.getInstancia().getCantidad());

        // Añadir el objeto "edificios"
        JsonObject edificiosObjeto = new JsonObject();
        JsonObject almacenObjeto = new JsonObject();
        JsonObject comidaObjeto = new JsonObject();
        if (AlmacenCentral.getInstance() != null) {
            almacenObjeto.addProperty("disponible", true);
            almacenObjeto.addProperty("capacidad", AlmacenCentral.getInstance().getCapacidadMax());
            comidaObjeto.addProperty("general", AlmacenCentral.getInstance().getCapacidad());
            ;
        } else {
            almacenObjeto.addProperty("disponible", false);
            almacenObjeto.addProperty("capacidad", 200);
            comidaObjeto.addProperty("general", 0);
        }
        almacenObjeto.add("comida", comidaObjeto);
        edificiosObjeto.add("almacen", almacenObjeto);
        estructuraJson.add("edificios", edificiosObjeto);

        // Añadir el array "piscifactorias"
        JsonArray piscifactoriasArray = new JsonArray();
        for (Piscifactoria pisc : piscifactorias) {
            JsonObject piscifactoriaObjeto = new JsonObject();
            piscifactoriaObjeto.addProperty("nombre", pisc.getNombre());
            if (pisc instanceof Piscifactoria) {
                piscifactoriaObjeto.addProperty("tipo", true);
            } else {
                piscifactoriaObjeto.addProperty("tipo", false);
            }
            piscifactoriaObjeto.addProperty("capacidad", pisc.getAlmacenMax());
            JsonObject comidaPiscifactoriaObjeto = new JsonObject();
            comidaPiscifactoriaObjeto.addProperty("general", pisc.getAlmacen());
            piscifactoriaObjeto.add("comida", comidaPiscifactoriaObjeto);

            // Añadir el array "tanques" dentro de "piscifactorias"
            JsonArray tanquesArray = new JsonArray();
            for (Tanque<? extends Pez> tanque : pisc.getTanques()) {
                JsonObject tanqueObjeto = new JsonObject();
                if (!tanque.getPeces().isEmpty()) {
                    tanqueObjeto.addProperty("pez", tanque.getPeces().get(0).getDatos().getNombre());
                    tanqueObjeto.addProperty("num", tanque.getPeces().size());
                    JsonObject datosObjeto = new JsonObject();
                    datosObjeto.addProperty("vivos", tanque.vivos());
                    datosObjeto.addProperty("maduros", tanque.adultos());
                    tanqueObjeto.add("datos", datosObjeto);

                    // Añadir el array "peces" dentro de "tanques"
                    JsonArray pecesArray = new JsonArray();
                    for (Pez pez : tanque.getPeces()) {
                        JsonObject pezObjeto = new JsonObject();
                        pezObjeto.addProperty("edad", pez.getEdad());
                        if (pez.isSexo()) {
                            pezObjeto.addProperty("sexo", true);
                        } else {
                            pezObjeto.addProperty("sexo", false);
                        }
                        if (pez.isVivo()) {
                            pezObjeto.addProperty("vivo", true);
                        } else {
                            pezObjeto.addProperty("vivo", false);
                        }
                        if (pez.isMaduro()) {
                            pezObjeto.addProperty("maduro", true);
                        } else {
                            pezObjeto.addProperty("maduro", false);
                        }
                        pecesArray.add(pezObjeto);
                    }
                    tanqueObjeto.add("peces", pecesArray);
                } else {
                    tanqueObjeto.addProperty("pez", "");
                    tanqueObjeto.addProperty("num", 0);
                    JsonObject datosObjeto = new JsonObject();
                    datosObjeto.addProperty("vivos", 0);
                    datosObjeto.addProperty("maduros", 0);
                    tanqueObjeto.add("datos", datosObjeto);

                }
                tanquesArray.add(tanqueObjeto);
            }
            piscifactoriaObjeto.add("tanques", tanquesArray);
            piscifactoriasArray.add(piscifactoriaObjeto);
        }

        estructuraJson.add("piscifactorias", piscifactoriasArray);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(estructuraJson);
        String nombreArchivo = "saves" + File.separator + this.getNombreCompa() + ".save";

        // Crear un objeto File con la ruta del archivo
        File archivo = new File(nombreArchivo);

        try (FileWriter writer = new FileWriter(archivo)) {
            // Escribir el JSON en el archivo
            writer.write(json);

            // Log para indicar que el sistema ha sido guardado
            EscritorHelper.getEscritorHelper(nombreCompa).addLogs("Partida guardada en: " + nombreArchivo + ".save");
        } catch (IOException e) {
            // Manejar la excepción en caso de error al escribir en el archivo
            e.printStackTrace();
        }
    }

    /**
     * Carga una partida previamente guardada desde un archivo JSON.
     * 
     * @param partida Nombre del archivo de guardado.
     */
    public void load(String partida) {
        JsonReader reader = null;
        JsonObject jsonObject = null;
        File file = new File("saves/" + partida);
        try {
            reader = new JsonReader(new BufferedReader(new FileReader(file)));
            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            String empresa = jsonObject.getAsJsonPrimitive("empresa").getAsString();
            int dia = jsonObject.getAsJsonPrimitive("dia").getAsInt();
            int monedas = jsonObject.getAsJsonPrimitive("monedas").getAsInt();
            Simulador.nombreCompa = empresa;
            this.dias = dia;
            Monedas.getInstancia().setCantidad(monedas);
            Stats.getInstancia(peces);
            // Extraer datos del objeto "edificios"
            JsonObject edificiosObjeto = jsonObject.getAsJsonObject("edificios");
            JsonObject almacenObjeto = edificiosObjeto.getAsJsonObject("almacen");
            boolean almacenDisponible = almacenObjeto.getAsJsonPrimitive("disponible").getAsBoolean();
            int almacenCapacidad = almacenObjeto.getAsJsonPrimitive("capacidad").getAsInt();
            int almacenComidaGeneral = almacenObjeto.getAsJsonObject("comida").getAsJsonPrimitive("general")
                    .getAsInt();
            if (almacenDisponible) {
                AlmacenCentral.getInstance().setCapacidadMax(almacenCapacidad);
                AlmacenCentral.getInstance().setCapacidad(almacenComidaGeneral);
            }
            // Extraer datos del array "piscifactorias"
            JsonArray piscifactoriasArray = jsonObject.getAsJsonArray("piscifactorias");

            for (int i = 0; i < piscifactoriasArray.size(); i++) {
                JsonObject piscifactoriaObjeto = piscifactoriasArray.get(i).getAsJsonObject();
                String nombrePiscifactoria = piscifactoriaObjeto.getAsJsonPrimitive("nombre").getAsString();
                boolean tipoPiscifactoria = piscifactoriaObjeto.getAsJsonPrimitive("tipo").getAsBoolean();
                int capacidadPiscifactoria = piscifactoriaObjeto.getAsJsonPrimitive("capacidad").getAsInt();
                int comidaGeneralPiscifactoria = piscifactoriaObjeto.getAsJsonObject("comida")
                        .getAsJsonPrimitive("general").getAsInt();
                Piscifactoria piscAux;
                if (tipoPiscifactoria) {
                    piscAux = new Piscifactoria(tipoPiscifactoria, nombrePiscifactoria);
                } else {
                    piscAux = new Piscifactoria(tipoPiscifactoria, nombrePiscifactoria);
                }
                // Extraer datos del array "tanques"
                JsonArray tanquesArray = piscifactoriaObjeto.getAsJsonArray("tanques");
                ArrayList<Tanque<Pez>> tanquesAux = new ArrayList<>();
                for (int idx = 0; idx < tanquesArray.size(); idx++) {
                    JsonObject tanqueObjeto = tanquesArray.get(idx).getAsJsonObject();
                    int numTanque = tanqueObjeto.getAsJsonPrimitive("num").getAsInt();
                    String nombrePez = tanqueObjeto.getAsJsonPrimitive("pez").getAsString();
                    Tanque tanqueAux;
                    if (tipoPiscifactoria) {
                        tanqueAux = new Tanque(25);
                    } else {
                        tanqueAux = new Tanque(100);
                    }
                    // Extraer datos del array "peces"
                    JsonArray peces = tanqueObjeto.getAsJsonArray("peces");
                    if (peces != null) {
                        for (int k = 0; k < peces.size(); k++) {
                            JsonObject pezObjeto = peces.get(k).getAsJsonObject();
                            int edadPez = pezObjeto.getAsJsonPrimitive("edad").getAsInt();
                            boolean sexo = pezObjeto.getAsJsonPrimitive("sexo").getAsBoolean();
                            boolean vivo = pezObjeto.getAsJsonPrimitive("vivo").getAsBoolean();
                            boolean maduro = pezObjeto.getAsJsonPrimitive("maduro").getAsBoolean();
                            Pez pez = null;
                            switch (nombrePez) {
                                case "Pejerrey":
                                    pez = new Pejerrey(sexo);
                                    break;
                                case "Carpa plateada":
                                    pez = new CarpaPlateada(sexo);
                                    break;
                                case "Salmón chinook":
                                    pez = new SalmonChinook(sexo);
                                    break;
                                case "Tilapìa del Nilo":
                                    pez = new TilapiaNilo(sexo);
                                    break;
                                case "Carpa":
                                    pez = new Carpa(sexo);
                                    break;
                                case "Róbalo":
                                    pez = new Robalo(sexo);
                                    break;
                                case "Caballa":
                                    pez = new Caballa(sexo);
                                    break;
                                case "Arenque del Atlántico":
                                    pez = new ArenqueAtlantico(sexo);
                                    break;
                                case "Sargo":
                                    pez = new Sargo(sexo);
                                    break;
                                case "Besugo":
                                    pez = new Besugo(sexo);
                                    break;
                                case "Dorada":
                                    pez = new Dorada(sexo);
                                    break;
                                case "Trucha arcoiris":
                                    pez = new TruchaArcoiris(sexo);
                                    break;
                            }
                            if (pez != null) {
                                pez.setMaduro(maduro);
                                pez.setEdad(edadPez);
                                pez.setVivo(vivo);
                                tanqueAux.getPeces().add(pez);
                            }

                        }
                    }
                    tanquesAux.add(tanqueAux);
                }
                piscAux.setTanques(tanquesAux);
                piscifactorias.add(piscAux);
            }
            EscritorHelper.getEscritorHelper(nombreCompa).addLogs("Sistema generado con éxito");
        } catch (FileNotFoundException ex) {
            EscritorHelper.getEscritorHelper(nombreCompa)
                    .addError("Error al leer la partida guardada" + ex.getMessage());

        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                EscritorHelper.getEscritorHelper(nombreCompa).addError("Error al cerrar el lector" + e.getMessage());
            }
        }
    }
}