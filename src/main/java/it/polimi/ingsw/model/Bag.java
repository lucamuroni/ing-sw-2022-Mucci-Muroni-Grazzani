package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Davide Grazzani
 * Class that represent the game bag as a pool of students
 */
//TODO : rimuovere 2 studenti per colore
public class Bag {
    private ArrayList<Student> students;

    /**
     * Class constructor
     */
    public Bag(){
        int numStudentsPerColor = 26;
        this.students = new ArrayList<Student>();
        for(PawnColor color : PawnColor.values()){
            for(int i=0; i<numStudentsPerColor; i++){
                Student student = new Student(color);
                this.students.add(student);
            }
        }
    }

    /**
     * Method that pulls the students from the bag
     * @param number represents how many students has to be pulled
     * @return the pulled students
     */
    public ArrayList<Student> pullStudents(int number){
        ArrayList<Student> results = new ArrayList<Student>(number);
        Random randomIndexGen = new Random();
        int selectedIndex;
        for(int i = 0; i<number;i++){
            try{
                selectedIndex = randomIndexGen.nextInt(students.size());
                results.add(this.students.get(selectedIndex));
                students.remove(selectedIndex);
            }catch (java.lang.IllegalArgumentException e){
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * Method that pushes students in the bag
     * @param student represents the student that has to be pushed
     */
    public void pushStudent(Student student){
        this.students.add(student);
    }

    /**
     * Method that checks if the bag is empty
     * @return true if the bag is empty, false if the bag isn't empty
     */
    public boolean isEmpty() {
        return this.students.isEmpty();
    }
}
