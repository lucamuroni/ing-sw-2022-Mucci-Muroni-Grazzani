package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

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


    public CharacterCardDeck(){
        this.cards = new ArrayList<>();
        Collections.addAll(this.cards, CharacterCard.values());
        this.studentsParameters = new ArrayList<>();
    }

    public void setParameters(ArrayList<Student> students, Island island){
        this.studentsParameters.clear();
        this.studentsParameters.addAll(students);
        this.islandParameter = island;
    }

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

    public void initDeck() {
        for (int i = 0; i<5; i++) {
            Random random = new Random();
            int rand = random.nextInt(0, this.cards.size());
            this.cards.remove(this.cards.get(rand));
        }
    }

    public ArrayList<CharacterCard> getCards() {
        return this.cards;
    }

    public Island getIslandParameter(){
        return this.islandParameter;
    }
}
