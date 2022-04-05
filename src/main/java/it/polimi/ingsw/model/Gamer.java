package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that represent a gamer in a non-expert game
 */

public class Gamer {
    private final int token;
    private String username;
    //private Stream input;
    private boolean activity;
    private AssistantCardDeck deck;
    //private TowerColor playerColor;
    private Dashboard dashboard;

    /**
     * Class constructor
     * @param token represent the unique number associated with a player
     * @param username represent the name choosen by the player that will be displayed
     */
    public Gamer(int token, String username/*, Stream input*/) {
        this.token = token;
        this.username = username;
        //this.input = input;
        activity = true;
    }

    /**
     * This method is called by the controller when the player has to choose
     * a cloud, take its students and put them in his waiting room in the dashboard
     * @param cloud is the cloud choosen by the player
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
     * @return deck of the player
     */
    public AssistantCardDeck getDeck(){
        return this.deck;
    }

    /**
     * Getter method
     * @return token associated to the player
     */
    public int getToken() {
        return token;
    }

    /**
     * Getter method
     * @return username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method
     * @return dashboard of the player
     */
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * Method that is called to verify if the player is still connected
     * @return a boolean: true if player is still connected
     */
    public boolean isActive() {
        return activity;
    }

    /**
     * Setter method
     * @param activity represents the status of the player: if he is connected it is true
     */
    public void setActivity(boolean activity) {
        this.activity = activity;
    }
}
