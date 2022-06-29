package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * Card Bard from the deck of CharacterCards
 * @author Davide Grazzani
 */
public class Bard extends CharacterCardInterface{
    private final ArrayList<PawnColor> colors;

    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     * @param colors is the arrayList of colors of the students moved
     */
    public Bard(CharacterCard card, ExpertGame game, ArrayList<PawnColor> colors) {
        super(card, game);
        this.colors = new ArrayList<>(colors);
        System.out.println("printo i colori del bardo ; size = "+colors.size());
        colors.forEach(x->System.out.println(x.toString()));
    }

    /**
     * Method that handles the effect of the card
     */
    @Override
    public void handle() {
        ExpertGamer gamer = this.getGame().getCurrentPlayer();
        for(int i = 0; i<colors.size() ; i+=2){
            System.out.println("Indice di controllo : "+i);
            final int index = i;
            Student student = gamer.getDashboard().getWaitingRoom().stream().filter(x->x.getColor().equals(colors.get(index))).findFirst().orElse(null);
            gamer.getDashboard().moveStudent(student);
            student = gamer.getDashboard().removeStudentFromHall(colors.get(i+1));
            ArrayList<Student> studs = new ArrayList<>();
            studs.add(student);
            gamer.getDashboard().addStudentsWaitingRoom(studs);
        }
    }
}
