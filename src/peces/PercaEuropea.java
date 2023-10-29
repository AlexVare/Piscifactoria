package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class PercaEuropea extends Activo{
    
    private final PecesDatos datos = AlmacenPropiedades.PERCA_EUROPEA;

    public PecesDatos getDatos() {
        return datos;
    }

    public PercaEuropea(boolean sexo){
        this.sexo=sexo;
        this.ciclo=this.datos.getCiclo();
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
    public boolean isOptimo() {
        return super.isOptimo();
    }

    @Override
    public boolean reproduccion() {
        return super.reproduccion();
    }
    public static void datos(){
        PecesDatos datos = AlmacenPropiedades.PERCA_EUROPEA;
        
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
