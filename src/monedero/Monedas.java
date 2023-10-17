package monedero;

public class Monedas {

    private int cantidad;

    public Monedas(int cantidad) {
        this.cantidad = cantidad;
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
}
