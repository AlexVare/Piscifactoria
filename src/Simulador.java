public class Simulador {
    
    public int dias=0;
    public int npiscifactorias=0;
    public String nombreCompañia="";
    
    public static void main(String[] args) throws Exception {
        //Escribir main, menú y lógica  

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

    public String getNombreCompañia() {
        return nombreCompañia;
    }

    public void setNombreCompañia(String nombreCompañia) {
        this.nombreCompañia = nombreCompañia;
    }

    public static void init(){
        System.out.println("Escribe el nombre de la empresa: ");        
    }

    /**
     * 
     */
    public static void menu(){

    }

    /**
     * @return 
     */
    public static String menúPisc(){
        return "";
    }


}
