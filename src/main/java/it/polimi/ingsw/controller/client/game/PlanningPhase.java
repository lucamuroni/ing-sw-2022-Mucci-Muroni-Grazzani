package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class PlanningPhase implements GamePhase {
    private final PhaseName name = PhaseName.PLANNING_PHASE;
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public PlanningPhase(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.view = view;
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        try {
            this.game.getSelf().updateCards(this.network.getPossibleCards());
            AssistantCard choice = this.view.selectCard(this.game.getSelf().getCards());
            this.game.getSelf().updateCurrentSelection(choice);
            this.network.sendCard(choice);
        } catch ()
    }

    @Override
    public PhaseName getNamePhase() {
        return name;
    }

    @Override
    public GamePhase next() {
        return new Idle(this.game, this.controller);
    }
}
