package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class ActionPhase1 implements GamePhase{
    private final PhaseName name = PhaseName.PLANNING_PHASE;
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;
    private final int numOfMoves;

    public ActionPhase1(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.controller = controller;
        this.network = this.controller.getNetwork();
        this.view = view;
        if (this.game.getGamers().size() == 2) {
            this.numOfMoves = 3;
        } else {
            this.numOfMoves = 4;
        }

    }
    @Override
    public void handle() {
        try {
            Student student = this.view.chooseStudentToMove();
            int location = 0;
            if (this.view.choosePlace().equals("Island")) {
                Island island = this.view.chooseIsland(this.game.getIslands());
                this.game.getSelf().getDashBoard().moveStudentToIsland(island, student);
                location = this.game.getIslands().indexOf(island);
            } else {
                this.game.getSelf().getDashBoard().moveStudentToHall(student);
            }
            this.network.sendColor(student.getColor());
            this.network.sendLocation(location);
        } catch (AssetErrorException e) {
            //throw new RuntimeException(e);
        }
    }

    @Override
    public GamePhase next() {
        return new Idle(this.game, this.controller, this.view);
    }

    @Override
    public PhaseName getNamePhase() {
        return name;
    }
}
