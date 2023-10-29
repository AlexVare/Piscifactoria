package tanque;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monedero.Monedas;
import peces.Pez;
import stats.Stats;

public class Tanque<T extends Pez> {

    ArrayList<Pez> peces = new ArrayList<>();
    int capacidad;
    int vendidos = 0;
    int ganancias = 0;
    ArrayList<Integer> muertos=new ArrayList<>();

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

    public void showFishStatus() {
        for (Pez pez : peces) {
            pez.showStatus();
        }
    }

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
        }else{
            return false;
        }
    }

    public int nuevoDiaComer(int comida) {
        int resto = comida;
        int cadaveres = 0;

        if(this.hasDead()){
        cadaveres = this.muertos.size();
        }
        for (Pez pez : peces) {
            if (pez.isVivo()) {
                if (cadaveres!=0) {
                    if (pez.eliminarPez()) {
                        cadaveres--;
                    }
                    pez.grow(resto, true);
                } else {
                    resto -= pez.grow(resto, false);
                }
            }
        }
        if (this.muertos.size() != 0) {
            for (int i = muertos.size(); i > cadaveres; i--) {
                this.peces.remove((int) this.muertos.get(i));
            }
        }
        return resto;
    }

    // public void nuevoDiaReproduccion() {
    //     for (Pez pez : peces) {
    //         int espacio = this.capacidad - this.peces.size();
    //         if (pez.isVivo()) {
    //             if (espacio > 0) {
    //                 if (pez.isMaduro() && pez.reproduccion()) {
    //                     int huevos = pez.getDatos().getHuevos();
    //                     if (huevos <= espacio) {
    //                         try {
    //                             this.nuevoPez(huevos);
    //                         } catch (Exception e) {
    //                             System.out.println("Error al introducir el pez");
    //                         }
    //                     } else {
    //                         try {
    //                             this.nuevoPez(espacio);
    //                         } catch (Exception e) {
    //                             System.out.println("Error al introducir el pez");
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
   public void nuevoDiaReproduccion() {
    int capacidadDisponible = this.capacidad - this.peces.size();
    
    List<Pez> nuevosPeces = new ArrayList<>();
    
    for (Pez pez : peces) {
        if (pez.isVivo() && capacidadDisponible > 0) {
            if (pez.isMaduro() && pez.reproduccion()) {
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
                        Pez nuevoPez = this.crearNuevaInstancia(pez.getClass());
                        nuevosPeces.add(nuevoPez);
                        Stats.getInstancia().registrarNacimiento(nuevoPez.getDatos().getNombre());
                        capacidadDisponible--;
                    }
                }
            }
        }
    }
    
    peces.addAll(nuevosPeces);
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
        if(this.machos()==0&&this.hembras()==0){
            return true;
        }
        if (this.machos() < this.hembras()) {
            return true;
        } else {
            return false;
        }
    }

    public Pez crearNuevaInstancia(Class<? extends Pez> tipoDePez) {
        try {
            Constructor<? extends Pez> constructor = tipoDePez.getDeclaredConstructor(boolean.class);
            return constructor.newInstance(this.sexoNuevoPez());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void comprarPez() {
        Pez npez = this.crearNuevaInstancia(this.peces.get(0).getClass());
        if (Monedas.getInstancia().comprobarPosible(npez.getDatos().getCoste())) {
            Monedas.getInstancia().compra(npez.getDatos().getCoste());
            this.peces.add(npez);
        } else {
            System.out.println("No tienes monedas suficientes");
        }
    }

    public void comprarPez(Pez pez) {

        if (Monedas.getInstancia().comprobarPosible(pez.getDatos().getCoste())) {
            Monedas.getInstancia().compra(pez.getDatos().getCoste());
            this.peces.add(pez);
        } else {
            System.out.println("No tienes monedas suficientes");
        }
    }

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

    public int vivos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int alimentados() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isAlimentado()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int adultos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isMaduro()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public double porcentaje(int numero1, int numero2) {
        if (numero2 == 0) {
            return 0.0;
        }

        double porcentaje = (double) numero1 / numero2 * 100;
        porcentaje = Math.round(porcentaje * 10) / 10.0;
        return porcentaje;
    }
}