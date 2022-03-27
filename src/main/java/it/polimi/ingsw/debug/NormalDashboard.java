package it.polimi.ingsw.debug;

import java.util.ArrayList;

public class NormalDashboard {
    private ArrayList<Student> waitingRoom;
    private int towers;
    private ArrayList<Student> hall;

    public NormalDashboard(ArrayList<Student> students, int towers){
        this.waitingRoom = students;
        this.towers = towers;
    }
}
