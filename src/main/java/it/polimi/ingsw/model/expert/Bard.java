package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bard extends CharacterCardInterface{

    private ArrayList<PawnColor> colors;

    public Bard(CharacterCard card, ExpertGame game, ArrayList<PawnColor> colors) {
        super(card, game);
        colors = new ArrayList<>(colors);
    }

    @Override
    public void handle() {
        //TODO fix del bardo
        ExpertGamer gamer = this.getGame().getCurrentPlayer();
        for(PawnColor color: this.colors){
            Student s = gamer.getDashboard().getWaitingRoom().stream().filter(x-> x.getColor() == color).findFirst().orElse(null);
            gamer.getDashboard().getWaitingRoom().remove(s);
            this.getGame().getBag().pushStudent(s);
            gamer.getDashboard().getWaitingRoom().add(this.getGame().getBag().pullStudents(1).get(0));
        }
    }

}
