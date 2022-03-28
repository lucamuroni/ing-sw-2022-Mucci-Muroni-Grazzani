package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.*;
import it.polimi.ingsw.debug.Student;

import java.util.ArrayList;

public class NormalGamer {
    private final int token;
    private String username;
    //private Stream input;
    private boolean activity;
    private AssistantCardDeck deck;
    private TowerColor playerColor;
    private NormalDashboard dashboard;

    public NormalGamer(int token, String username/*, Stream input*/) {
        this.token = token;
        this.username = username;
        //this.input = input;
        activity = true;
        deck = new AssistantCardDeck();
    }

    public void selectCloud(Cloud cloud){
        dashboard.addStudentsWaitingRoom(cloud.pullStudents());
    }

    public void initGamer(ArrayList<Student> students, int towers){
        dashboard = new NormalDashboard(students, towers);
    }
}
