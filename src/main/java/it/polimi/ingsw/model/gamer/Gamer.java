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
     * @param token represents the unique number associated with a player
     * @param username represents the name chosen by the player that will be displayed
     * @param towerColor represents the color of towers associated with the gamer
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
     * @param students represents the students taken by gamer from the bag at the start of the game
     * @param towers represents the towers that every gamer must have (the number of towers depends on how
     * many gamers are playing)
     */
    public void initGamer(ArrayList<Student> students, int towers){
        dashboard = new Dashboard(students, towers);
        deck = new AssistantCardDeck();
    }

    /**
     * Method used to get the deck of assistantCard
     * @return the deck of assistantCard of the gamer
     */
    public AssistantCardDeck getDeck(){
        return this.deck;
    }

    /**
     * Method used to get the token associated with the gamer
     * @return the token of the gamer
     */
    public int getToken() {
        return token;
    }

    /**
     * Method used to get the username associated with the gamer
     * @return the username of the gamer
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method used to get the dashboard associated with the gamer
     * @return the dashboard of the gamer
     */
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * Method used to get the color of towers associated with the gamer
     * @return the tower's color
     */
    public TowerColor getTowerColor() {
        return this.playerColor;
    }

    /**
     * Method used to get the activity of the gamer
     * @return true if the gamer is still connected, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Method used to set the inactivity of the gamer (e.g. the gamer disconnected from the game)
     */
    public void setInActivity(){
        active = false;
    }
}
