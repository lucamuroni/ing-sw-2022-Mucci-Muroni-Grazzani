package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * This class represents an Island
 */

public class Island {
    private final int id;
    private final ArrayList<Student> students;
    private int numTowers;
    private TowerColor towersColor;
    private boolean isMotherNaturePresent;
    private boolean isTowerPresent;
    private boolean isMerged;

    /**
     * Constructor of the class
     * @param id is the id associated with the island
     */
    public Island(int id){
        this.id = id;
        this.numTowers = 0;
        this.isMotherNaturePresent = false;
        this.isTowerPresent = false;
        this.isMerged = false;
        this.students = new ArrayList<>();
    }

    /**
     * This method updates all the attributes of the island with the information received from the server
     * @param students is the array containing all the students that are on the island
     * @param numTowers is the number of towers on the island/group of islands
     * @param towersColor is the color of the towers, or rather the color of the owner of the island
     */
    public void updateIsland(ArrayList<Student> students, int numTowers, TowerColor towersColor) {
        this.numTowers = numTowers;
        if (numTowers>0)
            this.isTowerPresent = true;
        this.students.clear();
        this.students.addAll(students);
        this.towersColor = towersColor;
        this.setTowerPresent();
    }

    /**
     * This method updates all the attributes of the island with the information received from the server
     * @param students is the array containing all the students that are on the island
     * @param numTowers is the number of towers on the island/group of islands
     */
    public void updateIsland(ArrayList<Student> students, int numTowers) {
        this.numTowers = numTowers;
        if (numTowers>0)
            this.isTowerPresent = true;
        this.students.clear();
        this.students.addAll(students);
    }

    /**
     * Method used to update the owner of the island
     * @param towersColor is the color of towers associated with the owner
     */
    public void updateOwner(TowerColor towersColor) {
        this.towersColor = towersColor;
    }

    /**
     * This method add a student on the island on client's move
     * @param student is the moved student
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }

    /**
     * Getter method
     * @return the id of the island
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method
     * @return the arrayList of student that are on the island
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Getter method
     * @return the number of turns elapsed
     */
    public int getNumTowers() {
        return numTowers;
    }

    /**
     * Getter method
     * @return the color of towers that are on the island
     */
    public TowerColor getTowersColor() {
        return towersColor;
    }

    /**
     * Method used to check if motherNature is present on the island
     * @return true if present, false otherwise
     */
    public boolean isMotherNaturePresent() {
        return this.isMotherNaturePresent;
    }

    /**
     * Setter method
     * @param motherNaturePresent is true if motherNature is present, false otherwise
     */
    public void setMotherNaturePresent(boolean motherNaturePresent) {
        this.isMotherNaturePresent = motherNaturePresent;
    }

    /**
     * Method used to check if there are towers on the island
     * @return true if present, false otherwise
     */
    public boolean isTowerPresent() {
        return isTowerPresent;
    }

    /**
     * Method used to set that towers are present on the island
     */
    public void setTowerPresent() {
        isTowerPresent = true;
    }

    /**
     * Method used to check if the island is merged
     * @return true if merged, false otherwise
     */
    public boolean isMerged() {
        return isMerged;
    }

    /**
     * Method used to set that an island has merged
     */
    public void setMerged() {
        isMerged = true;
    }
}
