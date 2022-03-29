package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Class for represent a single island or a group of them
 * @author Davide Grazzani
 */
public class Island{
    private int numTowers;
    private ArrayList<Student> students;
    private Optional<Gamer> owner;

    /**
     * Builder of the class
     * Initiate the number of towers, the array of Students and the owner of
     * the island(s)
     */
    public Island(){
        this.numTowers = 0;
        this.students = new ArrayList<Student>();
        this.owner = Optional.empty();
    }

    /**
     * Method for adding Students to the Island
     * @param student the student to be added
     */
    public void addStudents(Student student){
        this.students.add(student);
    }

    /**
     * Method for merging 2 different islands (or group of islands) whose owner
     * is shared
     * @param island represent the island that is going to be merged
     */
    public void mergeIsland(Island island){
        this.numTowers += island.getNumTowers();
        this.students.addAll(island.getStudents());
    }

    /**
     * Method used to get the number of tower on the island(s)
     * @return the number of the towers
     */
    public int getNumTowers(){
        return this.numTowers;
    }

    /**
     * Method used to get the Students on the island
     * @return the ArrayList of Students on the island
     */
    private ArrayList<Student> getStudents(){
        return this.students;
    }

    /**
     * Method for setting the owner of the island(s)
     * @param gamer is the gamer that will own the island
     */
    public void setOwner(Gamer gamer){
        this.owner = Optional.of(gamer);
    }

    /**
     * Method for calculate the influence of a Gamer given the ArrayList of colors
     * taken from the list of professors that the gamer itself owns.
     * This method would not calculate additional influence points given from the presence
     * of towers
     * @param colors represents the colors on witch you want to calculate the influence
     * @return an int that represent the influence of the gamer but without considering towers additional influence points
     */
    public int getInfluenceByColor(ArrayList<PawnColor> colors){
        int result = 0;
        result = Math.toIntExact(this.students.stream().filter(x -> colors.contains(x.getColor())).count());
        return result;
    }

    /**
     * Method used to return the current owner of the island
     * @return the owner of the island as Optional
     */
    public Optional<Gamer> getOwner(){
        return this.owner;
    }

    /**
     * Method for adding a tower to the island
     */
    public void addTower(){
        this.numTowers += 1;
    }
}
