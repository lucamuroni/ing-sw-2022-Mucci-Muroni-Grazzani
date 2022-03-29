package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Davide Grazzani
 */
public class Island{
    private int numTowers;
    private ArrayList<Student> students;
    private Optional<Gamer> owner;

    public Island(){
        this.numTowers = 0;
        this.students = new ArrayList<Student>();
        this.owner.isEmpty();
    }

    public void addStudents(Student student){
        this.students.add(student);
    }

    public void mergeIsland(Island island){
        this.numTowers += island.getNumTowers();
        this.students.addAll(island.getStudents());
    }

    public int getNumTowers(){
        return this.numTowers;
    }

    private ArrayList<Student> getStudents(){
        return this.students;
    }

    public void setOwner(Gamer gamer){
        this.owner = Optional.of(gamer);
    }

    public int getInfluenceByColor(ArrayList<PawnColor> colors){
        int result = 0;
        result = Math.toIntExact(this.students.stream().filter(x -> colors.contains(x.getColor())).count());
        return result;
    }

    public Optional<Gamer> getOwner(){
        return this.owner;
    }
}
