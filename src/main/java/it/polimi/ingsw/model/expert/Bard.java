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
        this.colors = new ArrayList<>(colors);
    }

    @Override
    public void handle() {
        ExpertGamer gamer = this.getGame().getCurrentPlayer();
        for(int i = 0; i<colors.size() ; i=i+2){
            final Integer index = i;
            Student student = gamer.getDashboard().getWaitingRoom().stream().filter(x->x.getColor().equals(colors.get(index))).findFirst().orElse(null);
            gamer.getDashboard().getWaitingRoom().remove(student);
            gamer.getDashboard().moveStudent(student);
            student = gamer.getDashboard().removeStudentFromHall(colors.get(i+1));
            ArrayList<Student> studs = new ArrayList<>();
            studs.add(student);
            gamer.getDashboard().addStudentsWaitingRoom(studs);
        }
    }

}
