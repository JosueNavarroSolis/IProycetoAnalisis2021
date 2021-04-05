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
public class Card {
    
    private String name;
    private boolean isMarked;
    
    public Card(String pName){
        this.setName(pName);
        this.setIsMarked(false);
    }
    
    public String getName(){
        return this.name;
    }
    
    public boolean getIsMarked(){
        return this.isMarked;
    }
    
    private void setName(String pName){
        this.name = pName;
    }
    
    protected void setIsMarked(boolean state){
        this.isMarked = state;
    }
    
}
