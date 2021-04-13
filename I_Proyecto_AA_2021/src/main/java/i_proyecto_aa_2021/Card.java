package i_proyecto_aa_2021;

/**
 * Class that has the necessary attributes to make a card
 * for the game.
 * @author Hans
 */
public class Card {
    
    private String name;
    private boolean isMarked;
    
    /**
     * Constructor method that creates a card instance
     * set as not marked.
     * @param pName Name that will be given to the new card instance.
     */
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
