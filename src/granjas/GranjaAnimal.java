package granjas;

public class GranjaAnimal {
    
    private static GranjaAnimal instance=null;
    private int ciclo=0;

    private GranjaAnimal(){
        this.ciclo=3;
    }

    public static GranjaAnimal getInstance(){
        if(instance==null){
            instance= new GranjaAnimal();
        }
        return instance;
    }

    
}
