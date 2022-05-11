package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;
import java.util.Random;

public class MotherNaturePhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private Gamer currentPlayer;
    private final View view;
    private Player player = null;

    public MotherNaturePhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.currentPlayer = this.game.getCurrentPlayer();
        this.view = this.controller.getView();
    }

    @Override
    public void handle() {
        try {
            this.view.phaseChanghe("ActionPhase1");
        } catch () {}
        try {
            this.moveMotherNature(this.controller.getPlayer(this.game.getCurrentPlayer()));
            ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
            players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
            for (Player pl : players) {
                this.view.setCurrentPlayer(pl);
                try {
                    try {
                        this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
                    } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                        this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
                    }
                } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                    this.controller.handlePlayerError(pl);
                }
            }
        } catch (ModelErrorException e) {
            this.controller.shutdown();
            e.printStackTrace();
            return;
        }
    }

    private void moveMotherNature(Player player) {
        this.view.setCurrentPlayer(player);
        Island place = null;
        ArrayList<Island> possibleChoices = null;
        possibleChoices = this.game.getMotherNatureDestination();
        try {
            try {
                place = this.view.getMNLocation(possibleChoices);
            } catch (MalformedMessageException e) {
                place = this.view.getMNLocation(possibleChoices);
            } catch (TimeHasEndedException e) {
                place = this.getRandomIsland(possibleChoices);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player);
        } catch (TimeHasEndedException e) {
            place = this.getRandomIsland(possibleChoices);
        }
        this.game.moveMotherNature(place);
    }

    private Island getRandomIsland(ArrayList<Island> choices) {
        Random random = new Random();
        int rand = random.nextInt(0, choices.size());
        return choices.get(rand);
    }

    @Override
    public GamePhase next() {
        return new ConquerIslandPhase(this.game,this.controller);
    }
}
