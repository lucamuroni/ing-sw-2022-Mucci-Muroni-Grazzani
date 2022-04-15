package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * @author Sara Mucci
 * Class that represent a gamer dashboard, which include the gamer's towers, the students present at the entrace and
 * the professor's table
 */

public class Dashboard {
    protected ArrayList<Student> waitingRoom;
    protected int towers;
    protected ArrayList<Student> hall;

    /**
     * Class builder
     * @param students represent the initial students that are present at the start of the game in the waiting room of a Gamer
     * @param numTowers represent the initial number of towers that are present at the start of the game in the waiting room of a Gamer
     */
    public Dashboard(ArrayList<Student> students, int numTowers){
        this.waitingRoom = new ArrayList<Student>(students);
        this.towers = numTowers;
        this.hall = new ArrayList<Student>();
    }

    /**
     * Method for moving towers to the islands and from the islands back to the dashboard in case of island ownership loss
     * @param number indicate the number of towers that need to be swapped between the dashboard and the islands
     */
    public void moveTower(int number){
        this.towers += number;
    }

    /**
     * Method used to add student to the "staged" area of a Gamer's dashboard
     * @param students represent an arraylist of student thath needs to be pushed into the dashboard
     */
    public void addStudentsWaitingRoom(ArrayList<Student> students){
        this.waitingRoom.addAll(students);
    }

    /**
     * Method used to calculate the influence of a Gamer on a Professor, given it's color
     * @param color is the color of the Professor you want to check
     * @return an int which represents the number of Students present in the hall of that professor
     */
    public int checkInfluence(PawnColor color){
        int result = Math.toIntExact(this.hall.stream().filter(x -> x.getColor().equals(color)).count());
        return result;
    }

    /**
     * Method used to move a Student from the staging area to a Professor table
     * @param student represent a Student in the staging area (waitingRoom) which must be moved
     */
    public void moveStudent(Student student){
        this.hall.add(student);
        this.waitingRoom.remove(student);
    }

    /**
     * Method used to move a Student from the staging area to an island
     * @param student represent a Student in the staging area (waitingRoom) which must be moved
     * @param island is the island target
     */
    public void moveStudent(Student student, Island island){
        island.addStudents(student);
        this.waitingRoom.remove(student);
    }

    /**
     * method used to return the number of towers on the dashboard
     * @return towers represents the number of towers
     */
    public int getNumTowers() {
        return this.towers;
    }

    //metodo da eliminare una volta terminato il test.
    public ArrayList<Student> getWaitingRoom () {
        return this.waitingRoom;
    }
}
