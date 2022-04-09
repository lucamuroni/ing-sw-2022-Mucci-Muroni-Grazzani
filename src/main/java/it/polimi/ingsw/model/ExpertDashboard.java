package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public class ExpertDashboard {
    private ArrayList<Student> waitingRoom = new ArrayList<Student>();
    private int towers;

    public ExpertDashboard(ArrayList<Student> students, int towers){
        this.waitingRoom.addAll(students);
        this.towers = towers;
    }

    public ArrayList<Student> getWaitingRoom(){
        return waitingRoom;
    }

    public int getTowers(){
        return towers;
    }

    public void addStudentsWaitingRoom(ArrayList<Student> students){
        waitingRoom.addAll(students);
    }
}
