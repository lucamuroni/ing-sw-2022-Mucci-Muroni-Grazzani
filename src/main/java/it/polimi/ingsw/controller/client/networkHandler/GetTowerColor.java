package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * This class is used to get a player's tower color
 */
public class GetTowerColor {
    private final MessageHandler messageHandler;
    private final Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetTowerColor(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public void handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        String result = this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment());
        int id = Integer.parseInt(result);
        Gamer owner = null;
        for (Gamer gamer : this.game.getGamers()) {
            if (gamer.getId() == id) {
                owner = gamer;
            }
        }
        if (owner == null){
            throw new AssetErrorException();
        }
        result = this.messageHandler.getMessagePayloadFromStream(TOWER_COLOR.getFragment());
        TowerColor col = null;
        for (TowerColor color : TowerColor.values()) {
            if (color.toString().equals(result)) {
                col = color;
            }
        }
        if (col == null)
            throw new AssetErrorException();
        Message message = new Message(TOWER_COLOR.getFragment(), OK.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        owner.setColor(col);
        owner.getDashBoard().setTowerColor(col.getAcronym());
    }
}
