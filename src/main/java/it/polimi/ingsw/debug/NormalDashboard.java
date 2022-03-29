package it.polimi.ingsw.debug;

import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

public class NormalDashboard {
    private ArrayList<Student> waitingRoom;
    private int towers;
    private ArrayList<Student> hall;

    public NormalDashboard(ArrayList<Student> students, int towers){
        this.waitingRoom = students;
        this.towers = towers;
    }

    public void addStudentsWaitingRoom(ArrayList<Student> students){
        waitingRoom.addAll(students);
    }

    public ArrayList<Student> getWaitingRoom() {
        return waitingRoom;
    }

    public int getTowers() {
        return towers;
    }
}
