package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public interface ViewHandler {
    AssistantCard selectCard(ArrayList<AssistantCard> cards);
    Student chooseStudentToMove();
    int choosePlace();
    Island chooseIsland(ArrayList<Island> islands);
    Cloud chooseCloud(ArrayList<Cloud> clouds);

    AssistantCardDeckFigures chooseFigure(ArrayList<AssistantCardDeckFigures> figures);

}
