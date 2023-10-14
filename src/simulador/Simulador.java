package simulador;
import java.util.ArrayList;

import piscifactoria.Piscifactoria;

public class Simulador {
    
    private int dias=0;
    private int npiscifactorias=0;
    private String nombreCompa="";
    private ArrayList<Piscifactoria> piscifactorias= new ArrayList<Piscifactoria>();
    
    public Simulador() {
    }

    public static void main(String[] args) throws Exception {
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

    public static void init(){    

    }

    /**
     * 
     */
    public static void menu(){
        
    }

    public static void menuPisc(){
        
    }

}
