package tanque;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import estadisticas.Estadisticas;
import monedero.Monedas;
import peces.Pez;

public class Tanque<T extends Pez> {

    ArrayList<Pez> peces = new ArrayList<>();
    int capacidad;
    ArrayList<Integer> muertos;

    public Tanque(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList<Pez> getPeces() {
        return peces;
    }

    public boolean hasDead() {
        this.muertos.removeAll(muertos);
        for (int i = 0; i < peces.size(); i++) {
            if (!peces.get(i).isVivo()) {
                this.muertos.add(i);
            }
        }
        if (muertos.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int nuevoDiaComer(int comida) {
        int resto = comida;
        int cadaveres = 0;

        this.hasDead();
        cadaveres = this.muertos.size();
        for (Pez pez : peces) {
            if (pez.isVivo()) {
                if (this.muertos.size() != 0) {
                    if (pez.eliminarPez()) {
                        cadaveres--;
                    }
                    pez.grow(resto, true);
                } else {
                    resto -= pez.grow(resto, false);
                }
            }
        }
        // Quitar peces comidos
        if (this.muertos.size() != 0) {
            for (int i = muertos.size(); i > cadaveres; i--) {
                this.peces.remove((int) this.muertos.get(i));
            }
        }
        return resto;
    }

    public void nuevoDiaReproduccion() {
        for (Pez pez : peces) {
            int espacio = this.capacidad - this.peces.size();
            if (pez.isVivo()) {
                if (espacio > 0) {
                    if (pez.isMaduro() && pez.reproduccion()) {
                        int huevos = pez.getDatos().getHuevos();
                        if (huevos <= espacio) {
                            try {
                                this.nuevoPez(huevos);
                            } catch (Exception e) {
                                System.out.println("Error al introducir el pez");
                            }
                        } else {
                            try {
                                this.nuevoPez(espacio);
                            } catch (Exception e) {
                                System.out.println("Error al introducir el pez");
                            }
                        }
                    }
                }
            }
        }
    }

    public void limpiarTanque() {
        this.hasDead();
        for (Integer muerto : muertos) {
            this.peces.remove((int) muerto);
        }
    }

    public void vaciarTanque() {
        this.peces.removeAll(peces);
    }

    public void nuevoPez(int cantidad) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        if (this.peces.size() != 0) {
            for (int i = 0; i < cantidad; i++) {
                Pez npez = this.peces.get(1).getClass().getDeclaredConstructor().newInstance(this.sexoNuevoPez());
                this.peces.add(npez);
            }
        }
    }

    public int machos() {
        int machos = 0;
        for (Pez pez : peces) {
            if (pez.isSexo() && pez.isVivo()) {
                machos++;
            }
        }
        return machos;
    }

    public int hembras() {
        int hembras = 0;
        for (Pez pez : peces) {
            if (!pez.isSexo() && pez.isVivo()) {
                hembras++;
            }
        }
        return hembras;
    }

    public boolean sexoNuevoPez() {
        if (this.machos() >= this.hembras()) {
            return false;
        } else {
            return true;
        }
    }

    public Monedas comprarPez(Monedas monedero) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Pez npez = this.peces.get(1).getClass().getDeclaredConstructor().newInstance(this.sexoNuevoPez());
        if (monedero.comprobarPosible(npez.getDatos().getCoste())) {
            monedero.compra(npez.getDatos().getCoste());
            this.peces.add(npez);
            
        }
        return monedero;
    }

    public Monedas comprarPez(Monedas monedero, Pez pez) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Pez npez = pez.getClass().getDeclaredConstructor().newInstance(this.sexoNuevoPez());
        if (monedero.comprobarPosible(npez.getDatos().getCoste())) {
            monedero.compra(npez.getDatos().getCoste());
            this.peces.add(npez);
        }
        return monedero;
    }
}