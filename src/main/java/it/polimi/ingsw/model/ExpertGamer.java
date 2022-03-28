package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.CharacterCard;
import it.polimi.ingsw.debug.Cloud;
import it.polimi.ingsw.debug.ExpertDashboard;
import it.polimi.ingsw.debug.Student;

import java.util.ArrayList;

public class ExpertGamer extends NormalGamer{
    private ExpertDashboard dashboard;

    public ExpertGamer(int token, String username) {
        super(token, username);
    }

    @Override
    public void initGamer(ArrayList<Student> students, int towers) {
        dashboard = new ExpertDashboard(students, towers);
    }

    @Override
    public void selectCloud(Cloud cloud) {
        super.selectCloud(cloud);
    }

    public void selectCharacterCard(CharacterCard card){

    }
}
