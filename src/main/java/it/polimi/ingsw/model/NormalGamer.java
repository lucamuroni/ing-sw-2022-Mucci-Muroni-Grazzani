package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.*;
import it.polimi.ingsw.debug.Student;

import java.util.ArrayList;

/**
 * @author luca
 * Class that represent a gamer in a non-expert game
 */

public class NormalGamer {
    private final int token;
    private String username;
    //private Stream input;
    private boolean activity;
    private AssistantCardDeck deck;
    private TowerColor playerColor;
    private NormalDashboard dashboard;

    /**
     * Class constructor
     * @param token represent the unique number associate with a player
     * @param username represent the name choose by the player that will be displayed
     */
    public NormalGamer(int token, String username/*, Stream input*/) {
        this.token = token;
        this.username = username;
        //this.input = input;
        activity = true;
        deck = new AssistantCardDeck();
    }

    /**
     * This method is called by the controller when the player he has to choose
     * a cloud and he has to take its students and put them in his waiting room in the dashboard
     * @param cloud is the cloud choose by the player
     */
    public void selectCloud(Cloud cloud){
        dashboard.addStudentsWaitingRoom(cloud.pullStudents());
    }

    /**
     * This method is called by the controller when all players connect to the game and the game's objects
     * can be inizialized
     * @param students represents the students taken by the player from the bag at the start of the game
     * @param towers represents the towers that every player must have (the number of towers depends on how
     * many players are playing)
     */
    public void initGamer(ArrayList<Student> students, int towers){
        dashboard = new NormalDashboard(students, towers);
    }
}
