package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.controller.networking.messageParts.TowerColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * This class represents an Island
 */
public class Island {
    private final int id;
    private ArrayList<Student> students;
    private int numTowers;
    private TowerColor towersColor;

    /**
     * Constructor of the class
     * @param id
     */
    public Island(int id){
        this.id = id;
        this.numTowers = 0;
    }

    /**
     * This method updates all the attributes of the island with the information received from the server
     * @param students is the array containing all the students that are on the island
     * @param numTowers is the number of towers on the island/group of islands
     * @param towersColor is the color of the towers, or rather the color of the owner of the island
     */
    public void updateIsland(ArrayList<Student> students, int numTowers, TowerColor towersColor) {
        this.numTowers = numTowers;
        this.students.clear();
        this.students.addAll(students);
        this.towersColor = towersColor;
    }

    /**
     * This method add a student on the island on client's move
     * @param student
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }
}
