package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class GetCurrentPlayer {
    MessageHandler messageHandler;

    Game game;

    public GetCurrentPlayer(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read(ConnectionTimings.RESPONSE.getTiming());
        String player = this.messageHandler.getMessagePayloadFromStream(PLAYER_NAME.getFragment());
        boolean check = false;
        for (Gamer gamer : this.game.getGamers()) {
            if (gamer.getUsername().equals(player)) {
                check = true;
                break;
            }
        }
        if (!check)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(PLAYER_NAME.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        this.game.setCurrentPlayer(player);
    }

}
