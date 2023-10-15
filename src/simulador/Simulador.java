package simulador;

import java.util.ArrayList;

import monedero.Monedas;
import piscifactoria.Piscifactoria;

public class Simulador {

    private int dias = 0;
    private int npiscifactorias = 0;
    private String nombreCompa = "";
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<Piscifactoria>();

    public Simulador() {
    }

    public static void main(String[] args) throws Exception {
        Piscifactoria pisc = null;
        Monedas monedero = null;
        init(pisc, monedero);
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getPiscifactorias() {
        return npiscifactorias;
    }

    public void setPiscifactorias(int piscifactorias) {
        this.npiscifactorias = piscifactorias;
    }

    public String getNombreCompan() {
        return nombreCompa;
    }

    public void setNombreCompan(String nombreCompa) {
        this.nombreCompa = nombreCompa;
    }

    public static void init(Piscifactoria pisc, Monedas monedero) {
        pisc = new Piscifactoria(true);
        monedero = new Monedas(100);
    }

    /**
     * 
     */
    public void menu() {

    }

    public void menuPisc() {

    }

}
