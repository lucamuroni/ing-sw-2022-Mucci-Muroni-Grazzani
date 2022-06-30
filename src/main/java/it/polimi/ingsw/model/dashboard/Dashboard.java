package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * @author Sara Mucci
 * Class that represents a gamer dashboard, which includes gamer's towers, students in the waitingRoom and professors' table
 */
public class Dashboard {
    protected ArrayList<Student> waitingRoom;
    protected int towers;
    protected ArrayList<Student> hall;

    /**
     * Class builder
     * @param students represents the students that are present at start of the game in the gamer waiting room
     * @param numTowers represents the initial number of towers that are present at start of the game in the gamer waiting room
     */
    public Dashboard(ArrayList<Student> students, int numTowers){
        this.waitingRoom = new ArrayList<>(students);
        this.towers = numTowers;
        this.hall = new ArrayList<>();
    }

    /**
     * Method used to move towers to the islands and from the islands back to the dashboard in case of island ownership loss
     * @param number indicates number of towers that need to be swapped between dashboard and an island
     */
    public void moveTower(int number){
        this.towers += number;
    }

    /**
     * Method used to add student to waitingRoom of dashboard
     * @param students represents an arraylist of student that needs to be pushed into the dashboard
     */
    public void addStudentsWaitingRoom(ArrayList<Student> students){
        this.waitingRoom.addAll(students);
    }

    /**
     * Method used to calculate influence of a gamer on a professor, given its color
     * @param color is the color of the professor to check
     * @return an int which represents the number of students of that color that are in the hall
     */
    public int checkInfluence(PawnColor color){
        return Math.toIntExact(this.hall.stream().filter(x -> x.getColor().equals(color)).count());
    }

    /**
     * Method used to move a student from waitingRoom to hall
     * @param student represents a student in waitingRoom that must be moved
     */
    public void moveStudent(Student student){
        this.hall.add(student);
        this.waitingRoom.remove(student);
    }

    /**
     * Method used to move a student from waitingRoom to an island
     * @param student represents a student in waitingRoom that must be moved
     * @param island is the island target
     */
    public void moveStudent(Student student, Island island){
        island.addStudents(student);
        this.waitingRoom.remove(student);
    }

    /**
     * Method used to return the number of towers on the dashboard
     * @return the number of towers
     */
    public int getNumTowers() {
        return this.towers;
    }

    /**
     * Method that returns the waiting room of a dashboard
     * @return the waiting room
     */
    public ArrayList<Student> getWaitingRoom () {
        return this.waitingRoom;
    }

    /**
     * Method that returns the hall of a dashboard
     * @return the hall
     */
    public ArrayList<Student> getHall(){
        return  this.hall;
    }
}
