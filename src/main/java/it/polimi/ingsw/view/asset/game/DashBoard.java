package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import java.util.ArrayList;

/**
 * This class represents a Dashboard
 */
public class DashBoard {

    private ArrayList<Student> waitingRoom;
    private ArrayList<Student> hall;
    private int numTower;
    private ArrayList<PawnColor> professors;
    private String username;

    /**
     * Constructor of the class
     */
    public DashBoard(){
        this.waitingRoom = new ArrayList<>();
        this.hall = new ArrayList<>();
        this.professors = new ArrayList<>();
    }

    /**
     * This method is used to update the dashboards of all the others players
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
     * This method is used to move a student from waitingRoom to hall in client's view
     * @param student is the moved student
     * @throws AssetErrorException when an error occurs
     */
    public void moveStudentToHall(Student student) throws AssetErrorException {
        this.waitingRoom.remove(this.waitingRoom.stream().filter(x->x.getColor().equals(student.getColor())).findFirst().orElseThrow(AssetErrorException::new));
        this.hall.add(student);
    }

    /**
     * This method is used to move a student from waitingRoom to an island in client's view
     * @param island is the chosen island
     * @param student is the moved student
     * @throws AssetErrorException when an error occurs
     */
    public void moveStudentToIsland(Island island, Student student) throws AssetErrorException{
        this.waitingRoom.remove(this.waitingRoom.stream().filter(x->x.getColor().equals(student.getColor())).findFirst().orElseThrow(AssetErrorException::new));
        island.addStudent(student);
    }

    /**
     * This method is used to add students in the waitingRoom in client's view
     * @param students is the array of students
     */
    public void addStudentToWaiting(ArrayList<Student> students) {
        this.waitingRoom.addAll(students);
    }
    public void setUsername(String name){
        this.username = name;
    }

    public String getUsername(){
        return this.username;
    }

    public ArrayList<Student> getWaitingRoom() {
        return waitingRoom;
    }

    public ArrayList<Student> getHall() {
        return hall;
    }

    public int getNumTower() {
        return numTower;
    }

    public ArrayList<PawnColor> getProfessors() {
        return professors;
    }
}
