package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CharacterCardDeck {
    private final ArrayList<CharacterCard> cards;
    private final ArrayList<Student> studentsParameters;
    private Island islandParameter;


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
                choice = new Ambassador(CharacterCard.AMBASSADOR,game,this.islandParameter);
            }
            case BARD -> {
                ArrayList<PawnColor> colors = new ArrayList<>();
                for(PawnColor color : PawnColor.values()){
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor().equals(color))){
                        colors.add(color);
                    }
                }
                choice = new Bard(CharacterCard.BARD,game,colors);
            }
            case CENTAUR -> {
                choice = new Centaur(CharacterCard.CENTAUR,game);
            }
            case KNIGHT -> {
                choice = new Knight(CharacterCard.KNIGHT,game);
            }
            case MERCHANT -> {
                ArrayList<PawnColor> colors = new ArrayList<>();
                for(PawnColor color : PawnColor.values()){
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor()==color)){
                        colors.add(color);
                    }
                }
                choice = new Merchant(CharacterCard.MERCHANT,game,colors.get(0));
            }
            case POSTMAN -> {
                choice = new Postman(CharacterCard.POSTMAN,game);
            }
            case THIEF -> {
                ArrayList<PawnColor> colors = new ArrayList<>();
                for(PawnColor color : PawnColor.values()){
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor()==color)){
                        colors.add(color);
                    }
                }
                choice = new Thief(CharacterCard.THIEF,game,colors.get(0));
            }
            case VILLAGER -> {
                 choice = new Villager(CharacterCard.VILLAGER,game);
            }
        }
        choice.payCardCost();
        choice.handle();
        game.setCharacterCardBeenPlayed();
    }

    public void initDeck() {
        /*for (int i = 0; i<5; i++) {
            Random random = new Random();
            int rand = random.nextInt(0, this.cards.size());
            this.cards.remove(this.cards.get(rand));
        }*/
        //todo debug
        this.cards.clear();
        this.cards.add(CharacterCard.AMBASSADOR);
        this.cards.add(CharacterCard.BARD);
        this.cards.add(CharacterCard.POSTMAN);
    }

    public ArrayList<CharacterCard> getCards() {
        return this.cards;
    }

    public Island getIslandParameter(){
        return this.islandParameter;
    }
}
