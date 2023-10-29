package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Besugo extends Pez{
    
    public static void datos(){
        PecesDatos datos = AlmacenPropiedades.BESUGO;
        
        System.out.println("------------------------------");
        System.out.println("Nombre común: "+datos.getNombre());
        System.out.println("Nombre científico: "+ datos.getCientifico());
        System.out.println("Tipo: "+datos.getTipo());
        System.out.println("Coste: "+datos.getCoste());
        System.out.println("Precio venta: "+datos.getMonedas());
        System.out.println("Huevos: "+datos.getHuevos());
        System.out.println("Ciclo: "+datos.getCiclo());
        System.out.println("Madurez: "+datos.getMadurez());
        System.out.println("Óptimo: "+datos.getOptimo());
    }

    private final PecesDatos datos = AlmacenPropiedades.BESUGO;

    public Besugo(boolean sexo){
        this.sexo=sexo;
        this.ciclo=this.datos.getCiclo();
    }

    public PecesDatos getDatos() {
        return datos;
    }

    @Override
    public int comer(int comida) {
        return super.comer(comida);
    }

    @Override
    public void comprobarMadurez() {
        super.comprobarMadurez();
    }

    @Override
    public boolean eliminarPez() {
        return super.eliminarPez();
    }

    @Override
    public int getEdad() {
        return super.getEdad();
    }

    @Override
    public String getSexo() {
        return super.getSexo();
    }

    @Override
    public int grow(int comida, boolean comido) {
        return super.grow(comida, comido);
    }

    @Override
    public boolean isAlimentado() {
        return super.isAlimentado();
    }

    @Override
    public boolean isMaduro() {
        return super.isMaduro();
    }

    @Override
    public boolean isOptimo() {
        return super.isOptimo();
    }

    @Override
    public boolean isSexo() {
        return super.isSexo();
    }

    @Override
    public boolean isVivo() {
        return super.isVivo();
    }

    @Override
    public void morision() {
        super.morision();
    }

    @Override
    public boolean reproduccion() {
        return super.reproduccion();
    }

    @Override
    public void setEdad(int edad) {
        super.setEdad(edad);
    }

    @Override
    public void setMaduro(boolean fertil) {
        super.setMaduro(fertil);
    }

    @Override
    public void setVivo(boolean vivo) {
        super.setVivo(vivo);
    }

    @Override
    public void showStatus() {
        System.out.println("----------" + this.datos.getNombre() + "----------");
        System.out.println("Edad: " + this.edad + " días");
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
}
