/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_proyecto_aa_2021;

/**
 *
 * @author Z170
 */
public class DriverMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Game gameInst = Game.getInstance();
        
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
