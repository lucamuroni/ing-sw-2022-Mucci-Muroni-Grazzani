package it.polimi.ingsw.model;



import java.util.ArrayList;
import java.util.Random;

/**
 * @author Davide Grazzani
 * Class that represent the game bag as a pool of students
 */
public class Bag {
    private ArrayList<Student> students;

    public Bag(){
        int numStudentsPerColor = 26;
        for(PawnColor color : PawnColor.values()){
            for(int i=0; i<numStudentsPerColor; i++){
                Student student = new Student(color);
                this.students.add(student);
            }
        }
    }

    public ArrayList<Student> pullStudents(int number){
        ArrayList<Student> results = new ArrayList<Student>(number);
        Random randomIndexGen = new Random();
        int selectedIndex;
        for(int i = 0; i<number;i++){
            selectedIndex = randomIndexGen.nextInt(students.size());
            results.add(this.students.get(selectedIndex));
            students.remove(selectedIndex);
        }
        return results;
    }
}
