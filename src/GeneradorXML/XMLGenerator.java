package GeneradorXML;

public class XMLGenerator {
    
    private static XMLGenerator instance=null;

    private XMLGenerator(){
    }

    public static XMLGenerator getInstance(){
        if(instance==null){
            instance= new XMLGenerator();
        }
        return instance;
    }

    public void XMLMonedas(){
        
    }
}
