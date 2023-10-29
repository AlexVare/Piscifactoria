package peces;

import java.util.Random;

import propiedades.PecesDatos;

/**
 * Clase abstracta que representa a un pez en la simulación.
 */
public abstract class Pez {

    protected int edad = 0;
    protected boolean maduro = false;
    protected boolean vivo = true;
    protected boolean sexo = false;
    protected int ciclo = 0;
    protected PecesDatos datos;
    protected boolean alimentado = true;

    public boolean isAlimentado() {
        return alimentado;
    }

    /**
     * Obtiene los datos específicos del pez.
     *
     * @return Datos específicos del pez.
     */
    public PecesDatos getDatos() {
        return datos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isMaduro() {
        return maduro;
    }

    public void setMaduro(boolean fertil) {
        this.maduro = fertil;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isSexo() {
        return sexo;
    }

    public String getSexo() {
        if (this.sexo) {
            return "Macho";
        } else {
            return "Hembra";
        }
    }

    /**
     * Muestra el estado actual del pez.
     */
    public void showStatus() {
    }

    /**
     * Verifica si el pez pudo comer en base a la cantidad de comida proporcionada.
     *
     * @param comida Cantidad de comida disponible.
     * @return Cantidad de comida consumida, en caso de ser 3 el pez no comió.
     */
    public int comer(int comida) {
        if (comida != 0) {
            return 1;
        } else {
            return 3;
        }
    }

    /**
     * Controla el crecimiento del pez en función de la comida proporcionada.
     *
     * @param comida Cantidad de comida disponible.
     * @param comido Indica si el pez ha sido alimentado por un pez muerto.
     * @return Cantidad de alimento consumido.
     */
    public int grow(int comida, boolean comido) {
        if (comido) {
            if (this.vivo == true) {
                this.edad++;
                this.alimentado = true;
                this.comprobarMadurez();
            }
            return 0;
        } else {
            int com = this.comer(comida);
            if (com == 3) {
                this.alimentado = false;
                this.morision();
            }
            if (this.vivo == true) {
                this.edad++;
                this.comprobarMadurez();
                if (com != 3) {
                    this.alimentado = true;
                    return com;
                }
            }
            return 0;
        }
    }

    /**
     * Verifica si el pez muerto debe ser eliminado.
     *
     * @return true si el pez debe ser eliminado, false en caso contrario.
     */
    public boolean eliminarPez() {
        Random comer = new Random();
        if (comer.nextBoolean()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba la muerte del pez en base a un 50% de probabilidades.
     */
    public void morision() {
        Random muerte = new Random();
        if (muerte.nextBoolean()) {
            this.setVivo(false);
        }
    }

    /**
     * Verifica si el pez es apto para la reproducción.
     *
     * @return true si el pez es apto para reproducirse, false en caso contrario.
     */
    public boolean reproduccion() {
        if (this.maduro && this.edad % this.datos.getCiclo() == 0) {
            if (!this.sexo) {
                this.ciclo = this.datos.getCiclo();
                return true;
            } else {
                return false;
            }

        } else {
            this.ciclo--;
            return false;
        }
    }

    public void comprobarMadurez() {
        if (this.edad >= this.datos.getMadurez()) {
            this.setMaduro(true);

        } else {
            this.setMaduro(false);
        }
    }

    /**
     * Verifica si el pez está en su estado óptimo para la venta.
     *
     * @return true si el pez está en su estado óptimo, false en caso contrario.
     */
    public boolean isOptimo() {
        if (this.edad == this.datos.getOptimo()) {
            return true;
        } else {
            return false;
        }
    }

}
