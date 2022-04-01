package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Dashboard {
    private ArrayList<Student> waitingRoom;
    private int towers;
    private ArrayList<Student> hall;

    public Dashboard(ArrayList<Student> students, int numTowers){
        this.waitingRoom = new ArrayList<Student>(students);
        this.towers = numTowers;
        this.hall = new ArrayList<Student>();
    }

    public void moveTower(int number){
        this.towers += number;
    }

    public void addStudentsWaitingRoom(ArrayList<Student> students){
        this.waitingRoom.addAll(students);
    }

    public void
}
