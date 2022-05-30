package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that represents the viewHandler with the methods to implement in the cli and the gui
 */
public interface ViewHandler {
    /**
     * Method that returns the assistant card the player chooses
     * @param cards represents the possible cards to choose from
     * @return the chosen assistant card
     */
    AssistantCard selectCard(ArrayList<AssistantCard> cards);

    /**
     * Method that returns the chosen student to move
     * @return the chosen student
     */
    Student chooseStudentToMove();

    /**
     * Method that returns the chosen place to move a student
     * @return the chosen place on the dashboard
     */
    int choosePlace();

    /**
     * Method that returns the chosen island to move a student to
     * @param islands represents the available islands
     * @return the chosen island
     */
    Island chooseIsland(ArrayList<Island> islands);
    Cloud chooseCloud(ArrayList<Cloud> clouds);
    AssistantCardDeckFigures chooseFigure(ArrayList<AssistantCardDeckFigures> figures);
    void getPlayerInfo();
    void goToIdle();

}
