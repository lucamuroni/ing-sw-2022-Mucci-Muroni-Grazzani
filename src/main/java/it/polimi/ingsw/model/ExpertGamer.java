package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that represents a player in the expert's game version
 */
public class ExpertGamer extends Gamer {
    private ExpertDashboard dashboard;

    /**
     * Class constructor that calls the upper class constructor
     * @param token represent the unique number associate with a player
     * @param username represent the name choose by the player that will be displayed
     */
    public ExpertGamer(int token, String username) {
        super(token, username);
    }

    /**
     * This override use an expert dashboard instead of a normal one
     * @param students represents the students taken by the player from the bag at the start of the game
     * @param towers represents the towers that every player must have (the number of towers depends on how
     */
    @Override
    public void initGamer(ArrayList<Student> students, int towers) {
        AssistantCardDeck d;
        dashboard = new ExpertDashboard(students, towers);
        d = getDeck();
        d = new AssistantCardDeck();
    }
}
