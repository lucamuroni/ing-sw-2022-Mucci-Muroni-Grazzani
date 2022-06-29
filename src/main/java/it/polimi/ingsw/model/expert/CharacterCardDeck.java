package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class that represents the deck of playable cards
 */
public class CharacterCardDeck {
    private final ArrayList<CharacterCard> cards;
    private final ArrayList<Student> studentsParameters;
    private Island islandParameter;
    private int ambassadorUsage = 0;
    private int bardUsage = 0;
    private int centaurUsage = 0;
    private int knightUsage = 0;
    private int merchantUsage = 0;
    private int postmanUsage = 0;
    private int thiefUsage = 0;
    private int villagerUsage = 0;

    /**
     * Constructor of the class
     */
    public CharacterCardDeck(){
        this.cards = new ArrayList<>();
        Collections.addAll(this.cards, CharacterCard.values());
        this.studentsParameters = new ArrayList<>();
    }

    /**
     * Method used to set the parameters used by some cards
     * @param students an arrayList of students
     * @param island is an island
     */
    public void setParameters(ArrayList<Student> students, Island island){
        this.studentsParameters.clear();
        this.studentsParameters.addAll(students);
        this.islandParameter = island;
    }

    /**
     * Method used to play the effect of a card
     * @param card is the card that must be played
     * @param game is the current game
     */
    public void playCard(CharacterCard card, ExpertGame game){
        CharacterCardInterface choice = null;
        switch (card){
            case AMBASSADOR -> {
                choice = new Ambassador(CharacterCard.AMBASSADOR,game);
                choice.payCardCost(ambassadorUsage);
                ambassadorUsage++;
            }
            case BARD -> {
                ArrayList<PawnColor> colors = this.studentsParameters.stream().map(x->x.getColor()).collect(Collectors.toCollection(ArrayList::new));
                choice = new Bard(CharacterCard.BARD,game,colors);
                choice.payCardCost(bardUsage);
                bardUsage++;
            }
            case CENTAUR -> {
                choice = new Centaur(CharacterCard.CENTAUR,game);
                choice.payCardCost(centaurUsage);
                centaurUsage++;
            }
            case KNIGHT -> {
                choice = new Knight(CharacterCard.KNIGHT,game);
                choice.payCardCost(knightUsage);
                knightUsage++;
            }
            case MERCHANT -> {
                ArrayList<PawnColor> colors = new ArrayList<>();
                for(PawnColor color : PawnColor.values()){
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor()==color)){
                        colors.add(color);
                    }
                }
                choice = new Merchant(CharacterCard.MERCHANT,game,colors.get(0));
                choice.payCardCost(merchantUsage);
                merchantUsage++;
            }
            case POSTMAN -> {
                choice = new Postman(CharacterCard.POSTMAN,game);
                choice.payCardCost(postmanUsage);
                postmanUsage++;
            }
            case THIEF -> {
                ArrayList<PawnColor> colors = new ArrayList<>();
                for(PawnColor color : PawnColor.values()){
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor()==color)){
                        colors.add(color);
                    }
                }
                choice = new Thief(CharacterCard.THIEF,game,colors.get(0));
                choice.payCardCost(thiefUsage);
                thiefUsage++;
            }
            case VILLAGER -> {
                 choice = new Villager(CharacterCard.VILLAGER,game);
                choice.payCardCost(villagerUsage);
                villagerUsage++;
            }
        }
        choice.handle();
        game.setCharacterCardBeenPlayed();
    }

    /**
     * Method used to init the deck
     */
    public void initDeck() {
        for (int i = 0; i<5; i++) {
            Random random = new Random();
            int rand = random.nextInt(0, this.cards.size());
            this.cards.remove(this.cards.get(rand));
        }
    }

    /**
     * Method that returns the cards of the dekc
     * @return the cards of the deck
     */
    public ArrayList<CharacterCard> getCards() {
        return this.cards;
    }

    /**
     * Method used to get the island if the card chosen is Ambassador
     * @return the island
     */
    public Island getIslandParameter(){
        return this.islandParameter;
    }
}
