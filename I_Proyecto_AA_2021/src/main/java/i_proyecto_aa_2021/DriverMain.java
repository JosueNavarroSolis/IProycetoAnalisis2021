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
        gameInst.startBacktrackingGame(3);
        Instant end1 = Instant.now();
        Duration timeElapsed1 = Duration.between(start1, end1);
        System.out.print("\n");
        
        //USO DE LOS TOSTRING
        //Solucion
        for(String str : gameInst.solutionToStr()){
            System.out.print(str+" ");
        }
        System.out.print("\n");
        //Sugerencias
        for (String[] strList : gameInst.suggestionsToString()){
            for(String str : strList){
                System.out.print(str+" ");
            }
            System.out.print("\n");
        }
        //Restricciones
        for (String[] strList : gameInst.restrictionsToString()){
            for(String str : strList){
                System.out.print(str+" ");
            }
            System.out.print("\n");
        }
        //Marcadas
        for (String str: gameInst.markedCardsToString()){
            System.out.print(str);
            System.out.print("\n");
        }
        
        System.out.print("\nTiempo de ejecucion (s:ms:ns):"+"\n"+timeElapsed1.toSecondsPart()+":"+timeElapsed1.toMillisPart()+":"+timeElapsed1.toNanosPart());
        
        /*
        //TIEMPOS DE CORRIDA
        int amountOfRuns = 10000;
        Instant startBFTest = Instant.now();
        for(int i = 0; i < amountOfRuns; i++){
            gameInst.buildDeck(categories, cards);
            gameInst.startBruteForceGame();
        }
        Instant endBFTest = Instant.now();
        Duration testTimeBF = Duration.between(startBFTest, endBFTest);
        System.out.print("\nTiempo de ejecucion total (s:ms:ns):"+"\n"+testTimeBF.toSecondsPart()+":"+testTimeBF.toMillisPart()+":"+testTimeBF.toNanosPart());
        Duration averageTimeBF = testTimeBF.dividedBy(amountOfRuns);
        System.out.print("\nTiempo de ejecucion promedio (s:ms:ns):"+"\n"+averageTimeBF.toSecondsPart()+":"+averageTimeBF.toMillisPart()+":"+averageTimeBF.toNanosPart());
        
        Instant startBTTest = Instant.now();
        for(int i = 0; i < amountOfRuns; i++){
            gameInst.buildDeck(categories, cards);
            gameInst.startBacktrackingGame(3);
        }
        Instant endBTTest = Instant.now();
        Duration testTimeBT = Duration.between(startBTTest, endBTTest);
        System.out.print("\nTiempo de ejecucion total (s:ms:ns):"+"\n"+testTimeBT.toSecondsPart()+":"+testTimeBT.toMillisPart()+":"+testTimeBT.toNanosPart());
        Duration averageTimeBT = testTimeBT.dividedBy(amountOfRuns);
        System.out.print("\nTiempo de ejecucion promedio (s:ms:ns):"+"\n"+averageTimeBT.toSecondsPart()+":"+averageTimeBT.toMillisPart()+":"+averageTimeBT.toNanosPart());
    
        */
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
        Instant start1 = Instant.now();
        gameInst.startBruteForceGame();
        Instant end1 = Instant.now();
        Duration timeElapsed1 = Duration.between(start1, end1);
        //gameInst.printSuggestions();
    }
    
}
