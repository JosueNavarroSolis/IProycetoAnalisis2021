package i_proyecto_aa_2021;

import java.util.ArrayList;

/**
 * Class that has the necessary attributes and methods for
 * making categories that contain cards for the game.
 * @author Hans
 */
public class Category {

    private String name;
    private ArrayList<Card> cards;

    
    public Category(String pName) {
        this.setName(pName);
        this.cards = new ArrayList<>();
    }
    
    public String getName() {
        return this.name;
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    private void setName(String name) {
        this.name = name;
    }

    /**
     * Method for adding card objects to the category instance.
     * @param pCard Card instance to be added to the category.
     */
    public void pushCard(Card pCard) {
        this.cards.add(pCard);
    }
    
    /**
     * Method to get the amount of cards in the category instance.
     * @return number of cards in category.
     */
    public int getLength() {
        return this.cards.size();
    }
    
    /**
     * Method for printing all the cards in the category.
     */
    public void printCategory(){
        String cardsStr = "[";
        for (Card tmpCard : this.cards){
            cardsStr += tmpCard.getName()+"state= "+tmpCard.getIsMarked()+", ";
        }
        cardsStr += "] \n";
        System.out.print(this.name+": "+cardsStr);
    }
    
}
