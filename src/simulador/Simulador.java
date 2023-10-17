package simulador;

import java.util.ArrayList;

import monedero.Monedas;
import piscifactoria.Piscifactoria;

public class Simulador {

    private int dias = 0;
    private int npiscifactorias = 0;
    private String nombreCompa = "";
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<Piscifactoria>();
    private Monedas monedas;

    public Simulador() {

    }

    public static void main(String[] args) throws Exception {
        Simulador simulador = new Simulador();

        simulador.init();

    }

    public int getDias() {
        return dias;
    }

    public void aumentarDias() {
        this.dias++;
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

    public void init() {
        this.monedas= new Monedas(100);
        this.piscifactorias.add(new Piscifactoria(true));
    }

    public void menu() {
        
    }

    public void menuPisc() {

    }

}
