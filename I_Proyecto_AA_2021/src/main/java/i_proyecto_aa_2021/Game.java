/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_proyecto_aa_2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This is a singleton class that contains the game logic
 * and provides attributes with information about the game
 * configuration and results.
 * @author Z170
 */
public class Game {
    
    //Constants for markableCards and restrictions list array items
    private static final int CATEGORY = 0;
    private static final int CARD = 1;
    
    public ArrayList<Category> categories;
    public int[] solution; //contains the solution for a game
    public ArrayList<int[]> suggestions;
    public Set<List<List<Integer>>> restrictions;
    private ArrayList<int[]> markableCards; //contains the indexes of all markable cards (not marked or not part of solution)
    public ArrayList<int[]> markedHistory;
    private boolean isSolved; //false until solution is found
    
    private Game() {
    }
    
    public static Game getInstance() {
        return GameHolder.INSTANCE;
    }
    
    private static class GameHolder {

        private static final Game INSTANCE = new Game();
    }
    
    /**
     * Method that builds the Category and Card instances required to play.
     * Each category must correspond to an array in the cards list.
     * @param pCategories Array with category names.
     * @param pCards List of arrays that have the card names for each category.
     * @return true if the amount of categories is equal to the amount of card arrays.
     *         false otherwise and the deck is not built.
     */
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
            return true;
        }
        return false;
    }
    
    /**
     * Method that generates a random solution from the available cards
     * and stores it in the solution attribute.
     */
    private void generateSolution(){
        this.solution = new int[this.categories.size()];
        Random rnd = new Random();
        
        for(int idx = 0; idx < this.categories.size(); idx++){
            int rndIndex = (int)(rnd.nextDouble() * this.categories.get(idx).getCards().size());
            this.solution[idx] = rndIndex;
        }
        
        /*for(int catIdx = 0; catIdx < this.solution.length; catIdx++){
            System.out.print(this.solution[catIdx]+" -> "+this.categories.get(catIdx).getCards().get(this.solution[catIdx]).getName()+"\n");
        }*/
    }
    
    /**
     * Method that generates a requested amount of random pair restrictions,
     * the restrictions are stored in the restrictions attribute.
     * @param amount Quantity of restrictions to be generated.
     */
    private void generateRestrictions(int amount){
        this.restrictions = new HashSet<>();
        Random rnd = new Random();
        while(this.restrictions.size() < amount){
            int firstRndCategory = (int)(rnd.nextDouble() * this.categories.size());
            int firstRndCard = (int)(rnd.nextDouble() * this.categories.get(firstRndCategory).getLength());
            int secondRndCategory = (int)(rnd.nextDouble() * this.categories.size());
            
            while(firstRndCategory == secondRndCategory){
                secondRndCategory = (int)(rnd.nextDouble() * this.categories.size());
            }
            int secondRndCard = (int)(rnd.nextDouble() * this.categories.get(secondRndCategory).getLength());
            
            if(validateRestriction(firstRndCategory, firstRndCard, secondRndCategory, secondRndCard)){
                if(firstRndCategory > secondRndCategory){
                    int tmpCategory = firstRndCategory;
                    int tmpCard = firstRndCard;
                    firstRndCategory = secondRndCategory;
                    firstRndCard = secondRndCard;
                    secondRndCategory = tmpCategory;
                    secondRndCard = tmpCard;
                }
                
                List<Integer> firstCardIndex = new ArrayList<>();
                firstCardIndex.add(firstRndCategory); firstCardIndex.add(firstRndCard);
                List<Integer> secondCardIndex = new ArrayList<>();
                secondCardIndex.add(secondRndCategory); secondCardIndex.add(secondRndCard);
                List<List<Integer>> restriction = new ArrayList();
                restriction.add(firstCardIndex); restriction.add(secondCardIndex);
            
                this.restrictions.add(restriction);
            }
        }
    }
    
    /**
     * Method that validates a restriction isn't part of the solution.
     * @param firstCategory first restriction pair category
     * @param firstCard first restriction pair card
     * @param secondCategory second restriction pair category
     * @param secondCard second restriction pair card
     * @return true if the restriction is not part of the solution. false otherwise.
     */
    private boolean validateRestriction(int firstCategory, int firstCard, int secondCategory, int secondCard){
        if((firstCard == this.solution[firstCategory]) && (secondCard == this.solution[secondCategory])){
            return false;
        }
        return true;
    }
    
    /**
     * Method that generates the list with all markable cards before game begins.
     */
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
    }
    
    /**
     * Method that picks a random card from markable cards and sets its corresponding
     * card instance as marked.
     */
    private void markCard(){
        Random rnd = new Random();
        int rndIndex = (int)(rnd.nextDouble() * this.markableCards.size());
        if(this.markableCards.size() > 0){
            int[] randomMarkableCardIdx = this.markableCards.get(rndIndex);
            this.categories.get(randomMarkableCardIdx[CATEGORY]).getCards().get(randomMarkableCardIdx[CARD]).setIsMarked(true);
            this.markedHistory.add(randomMarkableCardIdx);
            this.markableCards.remove(rndIndex);
        }
    }
    
    /**
     * Procedure that initializes a brute force game and calls the recursive
     * method bruteForceAux.
     */
    public void startBruteForceGame(){
        if(this.categories.size() > 0){
            this.isSolved = false;
            this.suggestions = new ArrayList<>();
            this.markedHistory = new ArrayList<>();
            generateSolution();
            generateMarkableCards();
            int[] suggestion = new int[this.categories.size()];
            for(int catIdx = 0; catIdx < suggestion.length; catIdx++)
                suggestion[catIdx] = 0;
            bruteForceAux(0, suggestion, true);
        }
    }
    
    /**
     * Recursive method that contains the logic of the brute force algorithm
     * for solving the game. This method is called recursively until the solution
     * is found.
     * @param catIdx current category index.
     * @param currentSuggestion array with the current combination of cards to be suggested
     * @param isSuggestable value that determines wether the current suggestion should be suggested.
     */
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
    
    public void startBacktrackingGame(int amountOfRestrictions){
        if(this.categories.size() > 0){
            this.isSolved = false;
            this.suggestions = new ArrayList<>();
            this.markedHistory = new ArrayList<>();
            generateSolution();
            generateMarkableCards();
            generateRestrictions(amountOfRestrictions);
            int[] suggestion = new int[this.categories.size()];
            for(int catIdx = 0; catIdx < suggestion.length; catIdx++)
                suggestion[catIdx] = 0;
            backtrackingAux(0, suggestion, true);
        }
    }
    
    private void backtrackingAux(int catIdx, int[] currentSuggestion, boolean isSuggestable){
        if(!this.isSolved){
            if(isSuggestable){
                makeSuggestion(currentSuggestion);
                if(checkSolve(currentSuggestion)){
                    this.isSolved = true;
                    return;
                }
                markCard();
            }
            
            MarkedState suggestionMarkedState = getSuggestionMarkedState(currentSuggestion, catIdx);
            int[] newSuggestion = currentSuggestion.clone();
            switch(suggestionMarkedState)
            {
                case BEFORE_INDEX:
                    return;
                case ON_INDEX:
                    if(newSuggestion[catIdx]+1 < this.categories.get(catIdx).getLength()){
                        newSuggestion[catIdx] += 1;
                    }
                    else
                        return;
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
                    }
                    else 
                        return;
                    break;
                case AFTER_INDEX:
                    ArrayList<Integer> markedAfterIdx = getIndexesAfter(newSuggestion, catIdx);
                    for(Integer markedIdx : markedAfterIdx){
                        if(newSuggestion[markedIdx]+1 < this.categories.get(markedIdx).getLength()){
                            newSuggestion[markedIdx] += 1;
                        }
                        else return;
                    }
                    break;
                default:
                    break;
            }
            
            RestrictionState suggestionRestrictionState = getSuggestionRestrictionState(newSuggestion, catIdx);
            ArrayList<Integer> suggestionRestrictionIndexes = getRestrictionsAfterIdx(newSuggestion, catIdx);
            
            switch(suggestionRestrictionState){
                case BEFORE_INDEX:
                    return;
                case ON_AFTER_INDEX:
                    for(Integer restrictionIdx : suggestionRestrictionIndexes){
                        if(newSuggestion[restrictionIdx]+1 < this.categories.get(restrictionIdx).getLength()){
                            int[] restrictionSuggestion = newSuggestion.clone();
                            restrictionSuggestion[restrictionIdx] += 1;
                            backtrackingAux(catIdx, restrictionSuggestion, true);
                        }
                    }
                    break;
                case NONE:
                    if(suggestionMarkedState == MarkedState.NONE){
                        if(catIdx+1 < this.categories.size()){
                            backtrackingAux(catIdx+1, currentSuggestion, false);
                        }
                        if(newSuggestion[catIdx]+1 < this.categories.get(catIdx).getLength()){
                            newSuggestion[catIdx] += 1;
                            backtrackingAux(catIdx, newSuggestion, true);
                        }
                    } else {
                        backtrackingAux(catIdx, newSuggestion, true);
                    }
                    
                    break;
            }
        }
    }
    
    /**
     * Method for adding a suggestion.
     * @param suggestion suggestion to be added.
     */
    private void makeSuggestion(int[] suggestion){
        this.suggestions.add(suggestion);
    }
     
    /**
     * Method that checks if the suggestion matches with the solution.
     * @param suggestion
     * @return 
     */
    private boolean checkSolve(int[] suggestion){
        for(int catIdx = 0; catIdx < suggestion.length; catIdx++){
            if(this.solution[catIdx] != suggestion[catIdx])
                return false;
        }
        return true;
    }
    
    private RestrictionState getSuggestionRestrictionState(int[] suggestion, int currentIdx){
        RestrictionState suggestionRestrictionState = RestrictionState.NONE;
        Iterator<List<List<Integer>>> i = this.restrictions.iterator();
        while(i.hasNext()){
            List<List<Integer>> currentRestriction = i.next();
            List<Integer> firstCard = currentRestriction.get(0);
            List<Integer> secondCard = currentRestriction.get(1);
            if((firstCard.get(CARD) == suggestion[firstCard.get(CATEGORY)])
              &&(secondCard.get(CARD) == suggestion[secondCard.get(CATEGORY)])){
                if((firstCard.get(CATEGORY) < currentIdx) && (secondCard.get(CATEGORY) < currentIdx)){
                    suggestionRestrictionState = RestrictionState.BEFORE_INDEX;
                    break;
                } else {
                    suggestionRestrictionState = RestrictionState.ON_AFTER_INDEX;
                }
            }
        }
        return suggestionRestrictionState;
    }
    
    private ArrayList<Integer> getRestrictionsAfterIdx(int[] suggestion, int currentIdx){
        ArrayList<Integer> restrictionIndexes = new ArrayList<>();
        Iterator<List<List<Integer>>> i = this.restrictions.iterator();
        while(i.hasNext()){
            List<List<Integer>> currentRestriction = i.next();
            List<Integer> firstCard = currentRestriction.get(0);
            List<Integer> secondCard = currentRestriction.get(1);
            if((firstCard.get(CARD) == suggestion[firstCard.get(CATEGORY)])
              &&(secondCard.get(CARD) == suggestion[secondCard.get(CATEGORY)])){
                if((!restrictionIndexes.contains(firstCard.get(CATEGORY)))
                    && (firstCard.get(CATEGORY)>= currentIdx)){
                    restrictionIndexes.add(firstCard.get(CATEGORY));
                }
                if((!restrictionIndexes.contains(secondCard.get(CATEGORY)))
                    && (secondCard.get(CATEGORY)>= currentIdx)){
                    restrictionIndexes.add(secondCard.get(CATEGORY));
                }
            }
        }
        
        return restrictionIndexes;
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
    
    public ArrayList<String[]> suggestionsToString(){
        ArrayList<String[]> suggestionsStrList = new ArrayList<>();
        for(int[] suggestion : this.suggestions){
            String[] suggestionStr = new String[5];
            for(int catIdx = 0; catIdx < suggestion.length; catIdx++){
                suggestionStr[catIdx] = this.categories.get(catIdx).getCards().get(suggestion[catIdx]).getName();
            }
            suggestionsStrList.add(suggestionStr);
        }
        
        return suggestionsStrList;
    }
    
    public String[] solutionToStr(){
        String[] solutionStr = new String[this.solution.length];
        for(int catIdx = 0; catIdx < this.solution.length; catIdx++){
            solutionStr[catIdx] = this.categories.get(catIdx).getCards().get(this.solution[catIdx]).getName();
        }
        return solutionStr;
    }
    
    public ArrayList<String[]> restrictionsToString(){
        ArrayList<String[]> restrictionsStrList = new ArrayList<>();
        Iterator<List<List<Integer>>> i = restrictions.iterator();
        while(i.hasNext()){
            List<List<Integer>> restriction = i.next();
            List<Integer> firstCard = restriction.get(0);
            List<Integer> secondCard = restriction.get(1);
            String[] restrictionStr = new String[2];
            restrictionStr[0] = this.categories.get(firstCard.get(CATEGORY)).getCards().get(firstCard.get(CARD)).getName();
            restrictionStr[1] = this.categories.get(secondCard.get(CATEGORY)).getCards().get(secondCard.get(CARD)).getName();
            restrictionsStrList.add(restrictionStr);
        }
        return restrictionsStrList;
    }
    
    
    public ArrayList<String> markedCardsToString(){
        ArrayList<String> markedCardsStrList = new ArrayList<>();
        
        for(int[] markedCard : this.markedHistory){
            String cardName = this.categories.get(markedCard[CATEGORY]).getCards().get(markedCard[CARD]).getName();
            markedCardsStrList.add(cardName);
        }
        
        return markedCardsStrList;
    }
            
    public void printSuggestions(){
        int counter = 1;
        for(int[] suggestion : this.suggestions){
            System.out.print("\n"+counter+". ");
            counter++;
            for(int catIdx = 0; catIdx < suggestion.length; catIdx++){
                System.out.print(this.categories.get(catIdx).getCards().get(suggestion[catIdx]).getName() + " ");
            }
        }
    }
}
