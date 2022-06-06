package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class GetTowerColor {
    private MessageHandler messageHandler;
    private Game game;

    public GetTowerColor(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
        String result = this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment());
        int id = Integer.parseInt(result);
        Gamer owner = null;
        for (Gamer gamer : this.game.getGamers()) {
            if (gamer.getId() == id) {
                owner = gamer;
            }
        }
        if (owner == null)
            throw new AssetErrorException();
        result = this.messageHandler.getMessagePayloadFromStream(TOWER_COLOR.getFragment());
        TowerColor col = null;
        for (TowerColor color : TowerColor.values()) {
            if (color.toString().equals(result)) {
                col = color;
            }
        }
        if (col == null)
            throw new AssetErrorException();
        owner.setColor(col);
    }
}
