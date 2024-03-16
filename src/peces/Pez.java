package peces;

import java.util.Random;

import piscifactoria.Piscifactoria;
import propiedades.PecesDatos;
import tanque.Tanque;

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

    /**
     * Obtiene la edad del pez.
     *
     * @return La edad actual del pez.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del pez.
     *
     * @param edad La nueva edad del pez.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Verifica si el pez ha alcanzado la madurez sexual.
     *
     * @return `true` si el pez es maduro, `false` si no lo es.
     */
    public boolean isMaduro() {
        return maduro;
    }

    /**
     * Establece el estado de madurez sexual del pez.
     *
     * @param fertil `true` si el pez es maduro, `false` si no lo es.
     */
    public void setMaduro(boolean fertil) {
        this.maduro = fertil;
    }

    /**
     * Verifica si el pez está vivo.
     *
     * @return `true` si el pez está vivo, `false` si no lo está.
     */
    public boolean isVivo() {
        return vivo;
    }

    /**
     * Establece el estado vital del pez.
     *
     * @param vivo `true` si el pez está vivo, `false` si no lo está.
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * Obtiene el sexo del pez como un booleano.
     *
     * @return `true` si el pez es macho, `false` si es hembra.
     */
    public boolean isSexo() {
        return sexo;
    }

    /**
     * Obtiene el sexo del pez como una cadena de texto.
     *
     * @return "Macho" si el pez es macho, "Hembra" si es hembra.
     */
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
    public void comer(Tanque tanque, Piscifactoria pisc, Boolean almacenCen) {
    }

    /**
     * Controla el crecimiento del pez en función de la comida proporcionada.
     *
     * @param tanque Tanque al que pertenece el pez.
     * @param pisc   Piscifactoria en la que se encuentra.
     */
    public void grow(Tanque tanque, Piscifactoria pisc, Boolean almacenCen) {
        Random random = new Random();

        if (this.vivo) {
            comer(tanque, pisc, almacenCen);
            // Verificar probabilidad de muerte antes de la madurez
            if (!this.maduro && random.nextDouble() < 0.05) {
                this.vivo = false; // El 5% de probabilidad de morir
            }
            // Resto de la lógica cuando no muere
            if (!this.alimentado) {
                this.morision();
            }
            if (this.vivo) {
                this.edad++;
                System.out.println(this.edad);
                this.comprobarMadurez(this.edad);
            }
        }
        this.alimentado = false;
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

    /*
     * Método para comprobar si el pez es maduro o no
     */
    public void comprobarMadurez(int años) {
        if (años >= this.datos.getMadurez()) {
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
