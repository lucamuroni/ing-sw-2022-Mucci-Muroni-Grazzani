package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.asset.game.Results;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class represents the last phase of the game, and it is called only when a winning condition is met
 */
public class EndGame implements GamePhase{
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    /**
     * Constructor of the class
     * @param controller is the controller of client side
     */
    public EndGame(ClientController controller) {
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    /**
     * This is the main and only method that handles the VictoryPhase
     */
    @Override
    public void handle() {
        ArrayList<Gamer> winner = new ArrayList<>();
        try {
            try {
                winner.addAll(this.network.getWinner(game));
            } catch (MalformedMessageException e) {
                winner.addAll(this.network.getWinner(game));
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found player");
        }
        if(winner.size() == 0){
            this.view.showEndGamePage(Results.ERROR);
        }else if (winner.size() == 1) {
            if (winner.get(0).getId() == this.game.getSelf().getId()) {
                this.view.showEndGamePage(Results.WIN);
            } else {
                this.view.showEndGamePage(Results.LOSS);
            }

        } else {
            boolean tie = false;
            for (Gamer gamer : winner) {
                if (gamer.getId() == this.game.getSelf().getId()) {
                    this.view.showEndGamePage(Results.TIE);
                    tie = true;
                }
            }
            if(!tie){
                this.view.showEndGamePage(Results.LOSS);
            }
        }
    }

    /**
     * Method unused because EndGame is the last phase, so there isn't a next phase
     * @return null
     */
    @Override
    public GamePhase next() {
        return null;
    }
}