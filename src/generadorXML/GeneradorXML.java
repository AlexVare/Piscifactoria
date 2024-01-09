package generadorXML;

public class GeneradorXML {

    
        private static GeneradorXML instance=null;
    
        private GeneradorXML(){
        }
    
        public static GeneradorXML getInstance(){
            if(instance==null){
                instance= new GeneradorXML();
            }
            return instance;
        }
    
        public void XMLMonedas(){
            
        }
}
