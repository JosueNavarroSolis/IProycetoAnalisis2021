/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_proyecto_aa_2021;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author Z170
 */
public class DriverMain {
    
    public Game gameInst;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game gameInst = Game.getInstance();
        
        String[] categories = {"Sospechosos", "Arma", "Motivo", "Parte del cuerpo", "Lugar"};
        String[][] cards = {{"Amigo/Amiga", "Novio/Novia", "Vecino/Vecina", "Mensajero", "Extraño"},
                            {"Pistola", "Cuchillo", "Machete", "Pala", "Bate", "Botella", "Tubo", "Cuerda"},
                            {"Venganza", "Celos", "Dinero", "Accidente", "Drogas", "Robo"},
                            {"Cabeza", "Pecho", "Abdomen", "Espalda", "Piernas", "Brazos"},
                            {"Sala", "Comedor", "Baño", "Terraza", "Cuarto", "Garage", "Patio", "Balcón", "Cocina"}};
        
        gameInst.buildDeck(categories, cards);
        Instant start1 = Instant.now();
        gameInst.startBruteForceGame();
        Instant end1 = Instant.now();
        Duration timeElapsed1 = Duration.between(start1, end1);
        gameInst.printSuggestions();
        /*Iterator<List<List<Integer>>> i = gameInst.restrictions.iterator();
        while(i.hasNext()){
            System.out.print(i.next());
        }*/
        System.out.print("\nTiempo de ejecucion (s:ms:ns):"+"\n"+timeElapsed1.toSecondsPart()+":"+timeElapsed1.toMillisPart()+":"+timeElapsed1.toNanosPart());
        
        //TIEMPOS DE CORRIDA
        int amountOfRuns = 10000;
        Instant startTest = Instant.now();
        for(int i = 0; i < amountOfRuns; i++){
            gameInst.buildDeck(categories, cards);
            gameInst.startBruteForceGame();
        }
        Instant endTest = Instant.now();
        Duration testTime = Duration.between(startTest, endTest);
        System.out.print("\nTiempo de ejecucion total (s:ms:ns):"+"\n"+testTime.toSecondsPart()+":"+testTime.toMillisPart()+":"+testTime.toNanosPart());
        Duration averageTime = testTime.dividedBy(amountOfRuns);
        System.out.print("\nTiempo de ejecucion promedio (s:ms:ns):"+"\n"+averageTime.toSecondsPart()+":"+averageTime.toMillisPart()+":"+averageTime.toNanosPart());
    }
    public void start() {
        // TODO code application logic here
        gameInst = Game.getInstance();
        
        String[] categories = {"Sospechosos", "Arma", "Motivo", "Parte del cuerpo", "Lugar"};
        String[][] cards = {{"Amigo/Amiga", "Novio/Novia", "Vecino/Vecina", "Mensajero", "Extraño"},
                            {"Pistola", "Cuchillo", "Machete", "Pala", "Bate", "Botella", "Tubo", "Cuerda"},
                            {"Venganza", "Celos", "Dinero", "Accidente", "Drogas", "Robo"},
                            {"Cabeza", "Pecho", "Abdomen", "Espalda", "Piernas", "Brazos"},
                            {"Sala", "Comedor", "Baño", "Terraza", "Cuarto", "Garage", "Patio", "Balcón", "Cocina"}};
        
        gameInst.buildDeck(categories, cards);
        gameInst.startBruteForceGame();
        gameInst.printSuggestions();
    }
    
}
