package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public interface ViewHandler {
    AssistantCard selectCard(ArrayList<AssistantCard> cards);
    Student chooseStudentToMove();
    String choosePlace();
    Island chooseIsland(ArrayList<Island> islands);
}
