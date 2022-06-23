package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CharacterCardDeck {
    private ArrayList<CharacterCard> cards;
    private ArrayList<Student> studentsParameters;
    private Island islandParameter;


    public CharacterCardDeck(){
        this.cards = new ArrayList<>();
        Collections.addAll(this.cards, CharacterCard.values());
        this.studentsParameters = new ArrayList<>();
    }

    public ArrayList<CharacterCard> drawCards() {
        ArrayList<CharacterCard> playableCards = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            Random random = new Random();
            int rand = random.nextInt(0, this.cards.size());
            playableCards.add(this.cards.get(rand));
        }
        return playableCards;
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
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor()==color)){
                        colors.add(color);
                    }
                }
                choice = new Bard(CharacterCard.AMBASSADOR,game,colors);
            }
            case CENTAUR -> {
                choice = new Centaur(CharacterCard.AMBASSADOR,game);
            }
            case KNIGHT -> {
                choice = new Knight(CharacterCard.AMBASSADOR,game);
            }
            case MERCHANT -> {
                ArrayList<PawnColor> colors = new ArrayList<>();
                for(PawnColor color : PawnColor.values()){
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor()==color)){
                        colors.add(color);
                    }
                }
                choice = new Merchant(CharacterCard.AMBASSADOR,game,colors.get(0));
            }
            case POSTMAN -> {
                choice = new Postman(CharacterCard.AMBASSADOR,game);

            }
            case THIEF -> {
                ArrayList<PawnColor> colors = new ArrayList<>();
                for(PawnColor color : PawnColor.values()){
                    if(this.studentsParameters.stream().anyMatch(x->x.getColor()==color)){
                        colors.add(color);
                    }
                }
                choice = new Thief(CharacterCard.AMBASSADOR,game,colors.get(0));

            }
            case VILLAGER -> {
                 choice = new Villager(CharacterCard.AMBASSADOR,game);
            }
        }
        choice.payCardCost();
        choice.handle();
    }
}
