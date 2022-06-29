package it.polimi.ingsw.model.gamer;

import it.polimi.ingsw.model.AssistantCardDeck;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import java.util.ArrayList;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * Class that represent a gamer in a non-expert game
 */

public class Gamer{
    final int token;
    final String username;
    protected AssistantCardDeck deck;
    private final TowerColor playerColor;
    private Dashboard dashboard;
    boolean active;

    /**
     * Class constructor
     * @param token represent the unique number associated with a player
     * @param username represent the name chosen by the player that will be displayed
     */
    public Gamer(int token, String username, TowerColor towerColor) {
        this.token = token;
        this.username = username;
        this.playerColor = towerColor;
        this.active = true;
    }

    /**
     * This method is called by the controller when the player has to choose
     * a cloud, take its students and put them in his waiting room in the dashboard
     * @param cloud is the cloud chosen by the player
     */
    public void selectCloud(Cloud cloud){
        dashboard.addStudentsWaitingRoom(cloud.pullStudent());
    }

    /**
     * This method is called by the controller when all players are connected to the game and the game's objects
     * can be initialized
     * @param students represents the students taken by the player from the bag at the start of the game
     * @param towers represents the towers that every player must have (the number of towers depends on how
     * many players are playing)
     */
    public void initGamer(ArrayList<Student> students, int towers){
        dashboard = new Dashboard(students, towers);
        deck = new AssistantCardDeck();
    }

    /**
     * Getter method
     * @return the deck of the player
     */
    public AssistantCardDeck getDeck(){
        return this.deck;
    }

    /**
     * Getter method
     * @return the token associated to the player
     */
    public int getToken() {
        return token;
    }

    /**
     * Getter method
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method
     * @return the dashboard of the player
     */
    public Dashboard getDashboard() {
        return dashboard;
    }


    public TowerColor getTowerColor() {
        return this.playerColor;
    }

    public boolean isActive() {
        return active;
    }

    public void setInActivity(){
        active = false;
    }
}
