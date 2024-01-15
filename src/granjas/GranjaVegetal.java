package granjas;

public class GranjaVegetal {
    
    private static GranjaVegetal instance=null;
    private int ciclo=0;

    private GranjaVegetal(){
        this.ciclo=3;
    }

    public static GranjaVegetal getInstance(){
        if(instance==null){
            instance= new GranjaVegetal();
        }
        return instance;
    }

    
}
