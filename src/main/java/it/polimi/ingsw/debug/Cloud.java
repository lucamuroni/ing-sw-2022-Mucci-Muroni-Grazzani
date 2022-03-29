package it.polimi.ingsw.debug;

import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

public class Cloud {
    private ArrayList<Student> students;

    public void pushStudents(ArrayList<Student> students){

    }

    public ArrayList<Student> pullStudents(){
        return this.students;
    }
}
