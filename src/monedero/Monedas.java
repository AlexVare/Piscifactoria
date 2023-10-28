package monedero;

public class Monedas {

    private int cantidad;

    private static Monedas instance=null;

    private Monedas(int cantidad) {
        this.cantidad = cantidad;
    }

    public static Monedas getInstancia() {
        if (instance == null) {
            instance = new Monedas(100);
        }
        return instance;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean comprobarPosible(int precio) {
        if (cantidad >= precio) {
            return true;
        } else {
            return false;
        }
    }

    public void compra(int precio){
        this.cantidad-=precio;
    }

    public void venta(int precio){
        this.cantidad+=precio;
    }
}