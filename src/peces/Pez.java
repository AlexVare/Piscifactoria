package peces;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;
import tanque.Tanque;

public class Pez {
    
    protected int edad=0;
    protected boolean fertil=false;
    protected boolean vivo=true;
    protected boolean sexo=false;

    public Pez() {
    }

    /**
     * 
     * 
     */
    public String showStatus(){
        return "";
    }
    
    public boolean comer(){
        return true;
    }

    public void grow(){
        Random muerte=new Random();
        if(!this.comer()){
            if(muerte.nextBoolean()){
                this.vivo=false;
            }
        }
        if(this.vivo==true){
            this.edad++;
        }
    }

    public int getEdad() {
        return edad;
    }

    public boolean isFertil() {
        return fertil;
    }

    public boolean isVivo() {
        return vivo;
    }

    public boolean isSexo() {
        return sexo;
    }

    public boolean comerPez(){
        Random comer = new Random();
        if(comer.nextBoolean()){
            return true;
        }else{
            return false;
        }
    }
}
