package it.polimi.ingsw.view.asset;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public class Cloud {
    private final int id;
    ArrayList<Student> students;

    public Cloud(int id){
        this.id = id;
        this.students = new ArrayList<Student>();
    }

    public void addStudent(ArrayList<Student> students){
        this.students.addAll(students);
    }

    public ArrayList<Student> removeStudents(){
        ArrayList<Student> result = new ArrayList<Student>(this.students);
        this.students.clear();
        return result;
    }
}
