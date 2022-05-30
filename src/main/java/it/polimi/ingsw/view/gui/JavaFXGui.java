package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Island;
import java.util.ArrayList;

public class JavaFXGui implements ViewHandler {

    @Override
    public AssistantCard selectCard(ArrayList<AssistantCard> cards) {
        return null;
    }

    @Override
    public Student chooseStudentToMove() {
        return null;
    }

    @Override
    public String choosePlace() {
        return null;
    }

    @Override
    public Island chooseIsland(ArrayList<Island> islands) {
        return null;
    }
}
