/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_proyecto_aa_2021;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Z170
 */
public class Game {
    
    //Constants
    private static final int CATEGORY = 0;
    private static final int CARD = 1;
    
    public ArrayList<Category> categories;
    public int[] solution;
    public ArrayList<int[]> suggestions;
    public ArrayList<int[]> markableCards;
    private boolean isSolved;
    
    private Game() {
    }
    
    public static Game getInstance() {
        return GameHolder.INSTANCE;
    }
    
    private static class GameHolder {

        private static final Game INSTANCE = new Game();
    }
    
    
    public boolean buildDeck(String[] pCategories, String[][] pCards) {
        if(pCategories.length == pCards.length) {
            
            this.categories = new ArrayList<>();
            for(String categoryName : pCategories)
            {
                this.categories.add(new Category(categoryName));
            }
            
            for(int idx = 0; idx < pCards.length; idx++)
            {
                Category tmpCategory = this.categories.get(idx);
                for(String cardName : pCards[idx]) {
                    tmpCategory.pushCard(new Card(cardName));
                }
            }
            /*for(Category cat : this.categories){
                cat.printCategory();
            }*/
            return true;
        }
        return false;
    }
    
    private void generateSolution(){
        this.solution = new int[this.categories.size()];
        Random rnd = new Random();
        
        for(int idx = 0; idx < this.categories.size(); idx++){
            int rndIndex = (int)(rnd.nextDouble() * this.categories.get(idx).getCards().size());
            this.solution[idx] = rndIndex;
        }
        
        for(int catIdx = 0; catIdx < this.solution.length; catIdx++){
            System.out.print(this.solution[catIdx]+" -> "+this.categories.get(catIdx).getCards().get(this.solution[catIdx]).getName()+"\n");
        }
    }
    
    private void generateMarkableCards(){
        this.markableCards = new ArrayList<>();
        int[] markableCard;
        for(int catIdx = 0; catIdx<this.categories.size(); catIdx++){
            Category tmpCategory = this.categories.get(catIdx);
            for(int cardIdx = 0; cardIdx<tmpCategory.getLength(); cardIdx++){
                if(cardIdx != this.solution[catIdx]){
                    markableCard = new int[]{catIdx, cardIdx};
                    this.markableCards.add(markableCard);
                }
            }
        }
        /*for(int[] card : this.markableCards){
            System.out.print(card[0]+" "+card[1]+"\n");
        }*/
    }
    
    private void markCard(){
        Random rnd = new Random();
        int rndIndex = (int)(rnd.nextDouble() * this.markableCards.size());
        if(this.markableCards.size() > 0){
            int[] randomMarkableCardIdx = this.markableCards.get(rndIndex);
            this.categories.get(randomMarkableCardIdx[CATEGORY]).getCards().get(randomMarkableCardIdx[CARD]).setIsMarked(true);
            this.markableCards.remove(rndIndex);
        }
        
        /*for(Category cat : this.categories){
                cat.printCategory();
            }*/
    }
    
    public void startBruteForceGame(){
        if(this.categories.size() > 0){
            this.isSolved = false;
            this.suggestions = new ArrayList<>();
            generateSolution();
            generateMarkableCards();
            int[] suggestion = new int[this.categories.size()];
            for(int catIdx = 0; catIdx < suggestion.length; catIdx++)
                suggestion[catIdx] = 0;
            
            bruteForceAux(0, suggestion, true);
        }
    }
    
    private void bruteForceAux(int catIdx, int[] currentSuggestion, boolean isSuggestable){
        if(!this.isSolved){
            if(isSuggestable){
                makeSuggestion(currentSuggestion);
                if(checkSolve(currentSuggestion)){
                    this.isSolved = true;
                }
                markCard();
            }
            
            MarkedState suggestionMarkedState = getSuggestionMarkedState(currentSuggestion, catIdx);
            int[] newSuggestion = currentSuggestion.clone();
            switch(suggestionMarkedState)
            {
                case BEFORE_INDEX:
                    break;
                case ON_INDEX:
                    if(newSuggestion[catIdx]+1 < this.categories.get(catIdx).getLength()){
                        newSuggestion[catIdx] += 1;
                        bruteForceAux(catIdx, newSuggestion, true);
                    }
                    break;
                case ON_AFTER_INDEX:
                    if(newSuggestion[catIdx]+1 < this.categories.get(catIdx).getLength()){
                        newSuggestion[catIdx] += 1;
                        ArrayList<Integer> markedAfterIdx = getIndexesAfter(newSuggestion, catIdx);
                        for(Integer markedIdx : markedAfterIdx){
                            if(newSuggestion[markedIdx]+1 < this.categories.get(markedIdx).getLength()){
                                newSuggestion[markedIdx] += 1;
                            }
                            else return;
                        }
                        bruteForceAux(catIdx, newSuggestion, true);
                    }
                    break;
                case AFTER_INDEX:
                    ArrayList<Integer> markedAfterIdx = getIndexesAfter(newSuggestion, catIdx);
                    for(Integer markedIdx : markedAfterIdx){
                        if(newSuggestion[markedIdx]+1 < this.categories.get(markedIdx).getLength()){
                            newSuggestion[markedIdx] += 1;
                        }
                        else return;
                    }
                    bruteForceAux(catIdx, newSuggestion, true);
                    break;
                case NONE:
                    if(catIdx+1 < this.categories.size()){
                        bruteForceAux(catIdx+1, currentSuggestion, false);
                    }
                    if(newSuggestion[catIdx]+1 < this.categories.get(catIdx).getLength()){
                        newSuggestion[catIdx] += 1;
                        bruteForceAux(catIdx, newSuggestion, true);
                    }
            }
        }
    }
    
    private void makeSuggestion(int[] suggestion){
        this.suggestions.add(suggestion);
    }
    
    private boolean checkSolve(int[] suggestion){
        for(int catIdx = 0; catIdx < suggestion.length; catIdx++){
            if(this.solution[catIdx] != suggestion[catIdx])
                return false;
        }
        return true;
    }
    
    private MarkedState getSuggestionMarkedState(int[] suggestion, int currentIdx){
        int catIdx = 0;
        while(catIdx < currentIdx){
            if(this.categories.get(catIdx).getCards().get(suggestion[catIdx]).getIsMarked()){
                return MarkedState.BEFORE_INDEX;
            }
            catIdx++;
        }
        boolean currentIdxIsMarked = this.categories.get(catIdx).getCards().get(suggestion[catIdx]).getIsMarked();
        catIdx++;
        while(catIdx < this.categories.size()){
            if(this.categories.get(catIdx).getCards().get(suggestion[catIdx]).getIsMarked()){
                if(currentIdxIsMarked)
                    return MarkedState.ON_AFTER_INDEX;
                return MarkedState.AFTER_INDEX;
            }
            catIdx++;
        }
        if(currentIdxIsMarked){
            return MarkedState.ON_INDEX;
        }
        
        return MarkedState.NONE;
    }
    
    private ArrayList<Integer> getIndexesAfter(int[] suggestion, int currentIdx){
        ArrayList<Integer> idxList = new ArrayList<>();
        for(int catIdx = currentIdx+1; catIdx < suggestion.length; catIdx++){
            if(this.categories.get(catIdx).getCards().get(suggestion[catIdx]).getIsMarked()){
                idxList.add(catIdx);
            }
        }
        
        return idxList;
    }
    
    public void printSuggestions(){
        for(int[] suggestion : this.suggestions){
            System.out.print("\n");
            for(int catIdx = 0; catIdx < suggestion.length; catIdx++){
                System.out.print(this.categories.get(catIdx).getCards().get(suggestion[catIdx]).getName() + " ");
            }
        }
    }
}
