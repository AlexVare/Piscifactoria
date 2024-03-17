package tanque;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import inputHelper.EscritorHelper;
import monedero.Monedas;
import peces.Pez;
import piscifactoria.Piscifactoria;
import stats.Stats;

/**
 * La clase Tanque representa un tanque de peces en la piscifactoría.
 * Permite gestionar los peces en el tanque, realizar operaciones como
 * alimentar, reproducir y vender.
 *
 * @param <T> El tipo de pez que se almacena en el tanque (debe extender la
 *            clase Pez).
 */
public class Tanque<T extends Pez> {

    ArrayList<Pez> peces = new ArrayList<>();
    int capacidad;
    int vendidos = 0;
    int ganancias = 0;
    int cadaveres = 0;
    ArrayList<Integer> muertos = new ArrayList<>();

    public Tanque(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getGanancias() {
        return ganancias;
    }

    public int getVendidos() {
        return vendidos;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ArrayList<Pez> getPeces() {
        return peces;
    }

    /**
     * Muestra el estado del tanque, incluyendo la ocupación, cantidad de peces
     * vivos, alimentados, adultos y género.
     */
    public void showStatus() {
        System.out.println("Ocupación: " + this.peces.size() + "/" + this.capacidad + " ("
                + this.porcentaje(this.peces.size(), this.capacidad) + "%)");
        System.out.println("Peces vivos: " + this.vivos() + "/" + this.peces.size() + " ("
                + this.porcentaje(this.vivos(), this.peces.size()) + "%)");
        System.out.println("Peces alimentados: " + this.alimentados() + "/" + this.vivos() + " ("
                + this.porcentaje(this.alimentados(), this.vivos()) + "%)");
        System.out.println("Peces adultos: " + this.adultos() + "/" + this.vivos() + " ("
                + this.porcentaje(this.adultos(), this.vivos()) + "%)");
        System.out.println("Hembras/Machos: " + this.hembras() + "/" + this.machos());
    }

    /**
     * Muestra el estado de cada pez en el tanque.
     */
    public void showFishStatus() {
        for (Pez pez : peces) {
            pez.showStatus();
        }
    }

    /**
     * Comprueba si hay peces muertos en el tanque y los registra.
     *
     * @return true si hay peces muertos, false en caso contrario.
     */
    public boolean hasDead() {
        if (muertos != null) {
            this.muertos.removeAll(muertos);
        }
        for (int i = 0; i < peces.size(); i++) {
            if (!peces.get(i).isVivo()) {
                this.muertos.add(i);
            }
        }
        if (this.muertos != null) {
            if (muertos.size() != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Realiza el proceso de crecimiento y alimentación de los peces en el tanque.
     *
     * @param comida La cantidad de comida disponible para los peces.
     * @return La cantidad de comida consumida.
     */
    public void nuevoDiaComer(Piscifactoria pisc, Boolean almacenCen) {
        for (Pez pez : peces) {
            if (pez.isVivo()) {
                pez.grow(this, pisc, almacenCen);
            }
        }
    }

    public boolean borrarMuerto() {
        Iterator<Pez> iterator = this.peces.iterator();

        while (iterator.hasNext()) {
            Pez objeto = iterator.next();
            if (objeto.isVivo() == false) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Realiza la reproducción entre peces en el tanque.
     */
    public void nuevoDiaReproduccion() {
        int capacidadDisponible = this.capacidad - this.peces.size();
        List<Pez> nuevosPeces = new ArrayList<>();

        for (Pez pez : peces) {

            if (pez.isVivo() && capacidadDisponible > 0) {
                if (pez.reproduccion()) {
                    int huevos = pez.getDatos().getHuevos();

                    if (huevos <= capacidadDisponible) {
                        for (int i = 0; i < huevos; i++) {
                            Pez nuevoPez = this.crearNuevaInstancia(pez.getClass());
                            nuevosPeces.add(nuevoPez);
                            Stats.getInstancia().registrarNacimiento(nuevoPez.getDatos().getNombre());
                            capacidadDisponible--;
                        }
                    } else {
                        for (int i = 0; i < capacidadDisponible; i++) {
                            Pez nPez = this.crearNuevaInstancia(pez.getClass());
                            nuevosPeces.add(nPez);
                            Stats.getInstancia().registrarNacimiento(nPez.getDatos().getNombre());
                            capacidadDisponible--;
                        }
                    }
                }
            }
        }
        peces.addAll(nuevosPeces);
    }

    /**
     * Elimina los peces muertos del tanque.
     */
    public void limpiarTanque() {
        if (this.hasDead()) {
            Iterator<Integer> iterator = muertos.iterator();
            while (iterator.hasNext()) {
                int muerto = iterator.next();
                this.peces.remove((int) muerto);
                iterator.remove();
            }
        }
    }

    /**
     * Vacia completamente el tanque, eliminando todos los peces.
     */
    public void vaciarTanque() {
        this.peces.removeAll(peces);
    }

    /**
     * Devuelve la cantidad de peces machos en el tanque.
     *
     * @return La cantidad de peces machos vivos.
     */
    public int machos() {
        int machos = 0;
        for (Pez pez : peces) {
            if (pez.isSexo() && pez.isVivo()) {
                machos++;
            }
        }
        return machos;
    }

    /**
     * Devuelve la cantidad de peces hembras en el tanque.
     *
     * @return La cantidad de peces hembras vivas.
     */
    public int hembras() {
        int hembras = 0;
        for (Pez pez : peces) {
            if (!pez.isSexo() && pez.isVivo()) {
                hembras++;
            }
        }
        return hembras;
    }

    /**
     * Determina si se debe crear un nuevo pez macho o hembra en función de la
     * proporción en el tanque.
     *
     * @return true si se debe crear un nuevo pez macho, false si se debe crear un
     *         nuevo pez hembra.
     */
    public boolean sexoNuevoPez() {
        if (this.machos() == 0 && this.hembras() == 0) {
            return true;
        }
        if (this.machos() < this.hembras()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Crea una nueva instancia de un pez.
     *
     * @param tipoDePez La clase del tipo de pez que se desea crear.
     * @return Una nueva instancia de pez del tipo especificado.
     */
    public Pez crearNuevaInstancia(Class<? extends Pez> tipoDePez) {
        try {
            Constructor<? extends Pez> constructor = tipoDePez.getDeclaredConstructor(boolean.class);
            return constructor.newInstance(this.sexoNuevoPez());
        } catch (Exception e) {
            EscritorHelper.getEscritorHelper("").addError("Error al generar un nuevo pez" + "\n");
            return null;
        }
    }

    /**
     * Compra un nuevo pez y lo agrega al tanque.
     * Método utilizado cuando ya hay peces en el tanque.
     */
    public void comprarPez() {
        Pez npez = this.crearNuevaInstancia(this.peces.get(0).getClass());
        if (Monedas.getInstancia().comprobarPosible(npez.getDatos().getCoste())) {
            Monedas.getInstancia().compra(npez.getDatos().getCoste());
            this.peces.add(npez);
        } else {
            System.out.println("No tienes monedas suficientes");
        }
    }

    /**
     * Compra un pez específico y lo agrega al tanque.
     *
     * @param pez El pez que se desea comprar y agregar al tanque.
     */
    public void comprarPez(Pez pez) {

        if (Monedas.getInstancia().comprobarPosible(pez.getDatos().getCoste())) {
            Monedas.getInstancia().compra(pez.getDatos().getCoste());
            this.peces.add(pez);
            EscritorHelper.getEscritorHelper("").addTrans(pez.getDatos().getNombre() + "(" + pez.getSexo() + ")"
                    + " comprado por " + pez.getDatos().getCoste() + ". Añadido al tanque de la piscifactoria " + "\n");
        } else {
            System.out.println("No tienes monedas suficientes");
        }
    }

    /**
     * Vende los peces que tienen propiedades óptimas y registra las ganancias.
     */
    public void venderOptimos() {
        Iterator<Pez> iterator = this.peces.iterator();
        this.vendidos = 0;
        this.ganancias = 0;
        while (iterator.hasNext()) {
            Pez pez = iterator.next();
            if (pez.isOptimo() && pez.isVivo()) {
                Monedas.getInstancia().venta(pez.getDatos().getMonedas());
                Stats.getInstancia().registrarVenta(pez.getDatos().getNombre(), pez.getDatos().getMonedas());
                this.vendidos++;
                this.ganancias += pez.getDatos().getMonedas();
                iterator.remove();
            }
        }
    }

    /**
     * Vende los peces adultos en el tanque y registra las ganancias.
     */
    public void venderAdultos() {
        Iterator<Pez> iterator = this.peces.iterator();
        this.vendidos = 0;
        this.ganancias = 0;
        while (iterator.hasNext()) {
            Pez pez = iterator.next();
            if (pez.isMaduro() && pez.isVivo()) {
                Monedas.getInstancia().venta(pez.getDatos().getMonedas() / 2);
                Stats.getInstancia().registrarVenta(pez.getDatos().getNombre(), pez.getDatos().getMonedas() / 2);
                this.vendidos++;
                this.ganancias += pez.getDatos().getMonedas() / 2;
                iterator.remove();
            }
        }
        EscritorHelper.getEscritorHelper("")
                .addTrans("Vendidos " + this.vendidos + "peces de forma manual por " + ganancias + " monedas" + "\n");
    }

    /**
     * Vende los peces adultos en el tanque y registra las ganancias.
     * 
     * @param cantidad cantidad necesaria para vender
     * @return cantidad de peces vendidos
     */
    public int venderPedido(int cantidad) {
        Iterator<Pez> iterator = this.peces.iterator();
        this.vendidos = 0;
        this.ganancias = 0;
        while (iterator.hasNext() && vendidos < cantidad) {
            Pez pez = iterator.next();
            if (pez.isMaduro() && pez.isVivo()) {
                Monedas.getInstancia().venta(pez.getDatos().getMonedas());
                Stats.getInstancia().registrarVenta(pez.getDatos().getNombre(), pez.getDatos().getMonedas());
                this.vendidos++;
                this.ganancias += pez.getDatos().getMonedas();
                iterator.remove();
            }
        }
        return vendidos;
    }

    /**
     * Devuelve la cantidad de peces vivos en el tanque.
     *
     * @return La cantidad de peces vivos.
     */
    public int vivos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Devuelve la cantidad de peces alimentados en el tanque.
     *
     * @return La cantidad de peces alimentados.
     */
    public int alimentados() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isAlimentado() && pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Devuelve la cantidad de peces adultos en el tanque.
     *
     * @return La cantidad de peces adultos.
     */
    public int adultos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isMaduro() && pez.isVivo()) {
                cantidad++;
            }
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
}