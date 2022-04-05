package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public class ExpertDashboard {
    private ArrayList<Student> students = new ArrayList<Student>();
    private int towers;
    public ExpertDashboard(ArrayList<Student> students, int towers){
        this.students.addAll(students);
        this.towers = towers;
    }

    public ArrayList<Student> getStudents(){
        return students;
    }

    public int getTowers(){
        return towers;
    }

    public void addStudentsWaitingRoom(ArrayList<Student> students){

    }
}
