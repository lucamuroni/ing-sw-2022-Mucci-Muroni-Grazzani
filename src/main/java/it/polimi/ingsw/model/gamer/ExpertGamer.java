package it.polimi.ingsw.model.gamer;

import it.polimi.ingsw.model.AssistantCardDeck;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.dashboard.ExpertDashboard;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that represents a player in the expert's game version
 */
public class ExpertGamer extends Gamer {
    private ExpertDashboard expertDashboard;

    /**
     * Class constructor that calls the upper class constructor
     * @param token represent the unique number associate with a player
     * @param username represent the name choose by the player that will be displayed
     */
    public ExpertGamer(int token, String username) {
        super(token, username);
    }

    /**
     * This override uses an ExpertDashboard instead of a Dashboard
     * @param cloud is the cloud choose by the player
     */
    @Override
    public void selectCloud(Cloud cloud) {
        expertDashboard.addStudentsWaitingRoom(cloud.pullStudent());
    }

    /**
     * This override uses an ExpertDashboard instead of a Dashboard
     * @param students represents the students taken by the player from the bag at the start of the game
     * @param towers represents the towers that every player must have (the number of towers depends on how
     */
    @Override
    public void initGamer(ArrayList<Student> students, int towers) {
        //AssistantCardDeck deck = getDeck();
        deck = new AssistantCardDeck();
        expertDashboard = new ExpertDashboard(students, towers);
    }

    /**
     * Getter method
     * @return the dashboard of the gamer
     */
    @Override
    public ExpertDashboard getDashboard() {
        return this.expertDashboard;
    }
}
