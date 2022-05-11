package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.exceptions.GenericErrorException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;
import java.util.Optional;

public class ConquerIslandPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private Gamer currentPlayer;
    private final View view;
    private Player player = null;

    public ConquerIslandPhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.currentPlayer = this.game.getCurrentPlayer();
    }
    @Override
    public void handle() {
        try {
            this.view.phaseChanghe("ActionPhase1");
        } catch () {}
        TowerColor color = this.conquerIsland();
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        for (Player pl : players) {
            this.view.setCurrentPlayer(pl);
            try {
                try {
                    this.view.sendTowerColor(color);
                } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                    this.view.sendTowerColor(color);
                }
            } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                this.controller.handlePlayerError(pl);
            }
        }
    }

    private TowerColor conquerIsland() {
        Optional<Gamer> conqueror = this.game.checkIslandOwner();
        if (conqueror.isPresent()) {
            Gamer gamer = conqueror.get();
            return gamer.getTowerColor();
        }
        return null;
    }

    @Override
    public GamePhase next() {
        return new ActionPhase3(this.game,this.controller);
    }
}
