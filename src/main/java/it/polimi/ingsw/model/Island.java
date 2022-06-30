package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Class for represent a single island or a group of them
 * @author Davide Grazzani
 */
public class Island{
    private int numTowers;
    private final ArrayList<Student> students;
    private Optional<Gamer> owner;
    private final int id;

    /**
     * Constructor of the class
     * Initiate the number of towers, the array of Students and the owner of the island(s)
     * @param id is the unique id (1 to 12) given to an island
     */
    public Island(int id){
        this.numTowers = 0;
        this.students = new ArrayList<>();
        this.owner = Optional.empty();
        this.id = id;
    }

    /**
     * Method to add students to an island
     * @param student represents the student to be added
     */
    public void addStudents(Student student){
        this.students.add(student);
    }

    /**
     * Method used to merge 2 different islands (or group of islands) whose owner is shared
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
     * Method used to get the students on an island
     * @return the arrayList of students on an island
     */
    public ArrayList<Student> getStudents(){
        return this.students;
    }

    /**
     * Method used to set the owner of an island(s)
     * @param gamer represents the new owner of an island
     */
    public void setOwner(Gamer gamer){
        this.owner = Optional.of(gamer);
    }

    /**
     * Method used to calculate the influence of a gamer given the arrayList of colors taken from the list of professors that the gamer itself owns
     * This method would not calculate additional influence points given from the presence of towers
     * @param colors represents the colors on which you want to calculate the influence
     * @return an int that represent the influence of the gamer but without considering towers' additional influence points
     */
    public int getInfluenceByColor(ArrayList<PawnColor> colors){
        int result;
        result = Math.toIntExact(this.students.stream().filter(x -> colors.contains(x.getColor())).count());
        return result;
    }

    /**
     * Method used to return the current owner of the island
     * @return the owner of the island (if present)
     */
    public Optional<Gamer> getOwner(){
        return this.owner;
    }

    /**
     * Method used to add a tower to an island
     */
    public void addTower(){
        this.numTowers += 1;
    }

    /**
     * Method used to get the id associated with an island
     * @return the associated id
     */
    public Integer getId(){
        return this.id;
    }
}
