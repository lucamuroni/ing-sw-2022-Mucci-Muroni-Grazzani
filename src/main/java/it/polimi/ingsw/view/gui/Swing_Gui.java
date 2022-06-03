package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.asset.game.Island;
import java.util.ArrayList;

public class Swing_Gui implements ViewHandler {
    Game game;
    ClientController clientController;

    public Swing_Gui (Game game, ClientController controller) {
        this.game = game;
        this.clientController = controller;
        this.start();
    }

    private void start() {

    }
    @Override
    public AssistantCard selectCard(ArrayList<AssistantCard> cards) {
        return null;
    }

    @Override
    public Student chooseStudentToMove() {
        return null;
    }

    @Override
    public int choosePlace() {
        return null;
    }

    @Override
    public Island chooseIsland(ArrayList<Island> islands) {
        return null;
    }
}
