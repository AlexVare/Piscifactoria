package stats;

import estadisticas.Estadisticas;

public class Stats extends Estadisticas{

    private static Stats instance=null;

    private Stats(String[] nombres){
        super(nombres);
    }
    
    public static Stats getInstancia(String[] nombres) {
        if (instance == null) {
            instance = new Stats(nombres);
        }
        return instance;
    }

    public static Stats getInstancia(){
        return instance;
    }

}
