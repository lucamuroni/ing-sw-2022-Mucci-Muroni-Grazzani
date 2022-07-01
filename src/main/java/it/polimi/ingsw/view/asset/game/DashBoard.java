package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class represents a Dashboard
 */
public class DashBoard {
    private final ArrayList<Student> waitingRoom;
    private final ArrayList<Student> hall;
    private int numTower;
    private final ArrayList<PawnColor> professors;
    private String username;
    private char towerColor;

    /**
     * Constructor of the class
     */
    public DashBoard(){
        this.waitingRoom = new ArrayList<>();
        this.hall = new ArrayList<>();
        this.professors = new ArrayList<>();
    }

    /**
     * This method is used to update the dashboards of all others players
     * @param numTower is the number of towers of the player
     * @param studentsToWaitingRoom is the array of students in the waiting room
     * @param studentsToHall is the array of students in the hall
     * @param professors is the array of the professors owned by the player
     */
    public void updateDashBoard(int numTower, ArrayList<Student> studentsToWaitingRoom, ArrayList<Student> studentsToHall, ArrayList<PawnColor> professors){
        this.numTower = numTower;
        this.waitingRoom.clear();
        this.waitingRoom.addAll(studentsToWaitingRoom);
        this.hall.clear();
        this.hall.addAll(studentsToHall);
        this.professors.clear();
        this.professors.addAll(professors);
    }

    /**
     * Getter method
     * @return the name of the player
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Getter method
     * @return the arrayList of students from waitingRoom
     */
    public ArrayList<Student> getWaitingRoom() {
        return waitingRoom;
    }

    /**
     * Getter method
     * @return the arrayList of students from hall
     */
    public ArrayList<Student> getHall() {
        return hall;
    }

    /**
     * Getter method
     * @return the number of towers still in dashboard
     */
    public int getNumTower() {
        return numTower;
    }

    /**
     * Getter method
     * @return the arrayList of professors owned by the player
     */
    public ArrayList<PawnColor> getProfessors() {
        return professors;
    }

    /**
     * Getter method
     * @return the color of towers owned by the player
     */
    public char getTowerColor() {
        return this.towerColor;
    }

    /**
     * Setter method
     * @param color is the color of towers associated with the player
     */
    public void setTowerColor(char color){
        this.towerColor = color;
    }

    /**
     * Setter method
     * @param name is the name of the player
     */
    public void setUsername(String name){
        this.username = name;
    }
}