package peces;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class TruchaArcoiris extends Carnivoro {
    private final PecesDatos datos = AlmacenPropiedades.TRUCHA_ARCOIRIS;

    public PecesDatos getDatos() {
        return datos;
    }

    public TruchaArcoiris(boolean sexo) {
        this.sexo = sexo;
        this.ciclo = this.datos.getCiclo();
    }

    public static void datos() {
        PecesDatos datos = AlmacenPropiedades.TRUCHA_ARCOIRIS;

        System.out.println("------------------------------");
        System.out.println("Nombre común: " + datos.getNombre());
        System.out.println("Nombre científico: " + datos.getCientifico());
        System.out.println("Tipo: " + datos.getTipo());
        System.out.println("Coste: " + datos.getCoste());
        System.out.println("Precio venta: " + datos.getMonedas());
        System.out.println("Huevos: " + datos.getHuevos());
        System.out.println("Ciclo: " + datos.getCiclo());
        System.out.println("Madurez: " + datos.getMadurez());
        System.out.println("Óptimo: " + datos.getOptimo());

    }

    public boolean isAlimentado() {
        return alimentado;
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
            System.out.println("Sexo: M");
        } else {
            System.out.println("Sexo: H");
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
        if (this.alimentado) {
            System.out.println("Alimentado: Si");
        } else {
            System.out.println("Alimentado: No");
        }
    }

    public void comprobarMadurez() {
        if (this.edad >= this.datos.getMadurez()) {
            this.setMaduro(true);

        } else {
            this.setMaduro(false);
        }
    }

    public boolean isOptimo() {
        if (this.edad == this.datos.getOptimo()) {
            return true;
        } else {
            return false;
        }
    }
}
