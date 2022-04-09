package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Collection;

public class Dashboard {
    private ArrayList<Student> waitingRoom;
    private int towers;

    public Dashboard(ArrayList<Student> students, int towers) {
        waitingRoom = new ArrayList<Student>(students);
        this.towers = towers;
    }

    public void addStudentsWaitingRoom(ArrayList<Student> students) {
        waitingRoom.addAll(students);
    }

    public ArrayList<Student> getWaitingRoom() {
        return waitingRoom;
    }

    public int getTowers() {
        return towers;
    }
}
