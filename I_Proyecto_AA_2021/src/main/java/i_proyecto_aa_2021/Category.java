/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_proyecto_aa_2021;

import java.util.ArrayList;

/**
 *
 * @author Z170
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

    public void pushCard(Card pCard) {
        this.cards.add(pCard);
    }
    
    public int getLength() {
        return this.cards.size();
    }
    
    public void printCategory(){
        String cardsStr = "[";
        for (Card tmpCard : this.cards){
            cardsStr += tmpCard.getName()+"state= "+tmpCard.getIsMarked()+", ";
        }
        cardsStr += "] \n";
        System.out.print(this.name+": "+cardsStr);
    }
    
}
