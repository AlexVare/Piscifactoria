package peces;

import java.util.Random;

public class Pez {

    protected int edad = 0;
    protected boolean fertil = false;
    protected boolean vivo = true;
    protected boolean sexo = false;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isFertil() {
        return fertil;
    }

    public void setFertil(boolean fertil) {
        this.fertil = fertil;
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

    public String showStatus() {
        return "";
    }

    public int comer(int comida) {
        if (comida != 0) {
            return 1;
        } else {
            return 3;
        }
    }

    public int grow(int comida, boolean comido) {
        if (comido) {
            if (this.vivo == true) {
                this.edad++;
                this.comprobarMadurez();
            }
            return 0;
        } else {
            int com = this.comer(comida);
            if (com == 3) {
                this.morision();
            }
            if (this.vivo == true) {
                this.edad++;
                this.comprobarMadurez();
                if(com!=3){
                    return com;
                }
            }
            return 0;
        }
    }

    public boolean eliminarPez() {
        Random comer = new Random();
        if (comer.nextBoolean()) {
            return true;
        } else {
            return false;
        }
    }

    public void morision() {
        Random muerte = new Random();
        if (muerte.nextBoolean()) {
            this.setVivo(false);
        }
    }

    public void comprobarFertilidad() {
    }

    public void comprobarMadurez() {
    }
}
