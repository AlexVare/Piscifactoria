package peces;

import java.util.Random;

import propiedades.PecesDatos;

public abstract class Pez {

    protected int edad = 0;
    protected boolean maduro = false;
    protected boolean vivo = true;
    protected boolean sexo = false;
    protected int ciclo = 0;
    protected PecesDatos datos;
    protected boolean alimentado = true;

    public Pez(boolean sexo) {
        this.sexo = sexo;
    }

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

    public void showStatus() {
        System.out.println("----------" + this.datos.getNombre() + "----------");
        System.out.println("Edad: " + this.edad + "días");
        if (this.sexo) {
            System.out.println("Sexo: H");
        } else {
            System.out.println("Sexo: M");
        }
        if (this.vivo) {
            System.out.println("Vivo: Si");
        } else {
            System.out.println("Vivo: No");
        }
        if (this.maduro) {
            System.out.println("Adulto: Si");
        } else {
            System.out.println("Adulto: No");
        }
        if (this.ciclo == 0) {
            System.out.println("Fértil: Si");
        } else {
            System.out.println("Fértil: No");
        }
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

    public boolean reproduccion() {
        if (this.maduro && this.edad % this.datos.getCiclo() == 0 && !this.sexo) {
            this.ciclo = this.datos.getCiclo();
            return true;
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

    public boolean isOptimo(){
            if(this.edad==this.datos.getOptimo()){
                return true;
            }else{
                return false;
            }
    }

    
}
