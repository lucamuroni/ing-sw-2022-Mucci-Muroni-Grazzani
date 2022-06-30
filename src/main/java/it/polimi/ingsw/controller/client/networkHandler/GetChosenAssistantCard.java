package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the chosen assistant card the current player chooses (to update the view)
 */
public class GetChosenAssistantCard {
    MessageHandler messageHandler;
    AssistantCard assistantCardToGet;
    Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetChosenAssistantCard(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public Gamer handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        int id = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment()));
        Gamer owner = null;
        for (Gamer gamer : game.getGamers()) {
            if (gamer.getId() == id) {
                owner = gamer;
            }
        }
        if (owner==null)
            throw new AssetErrorException();
        String name = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment());
        for (AssistantCard assistantCard: AssistantCard.values()) {
            if (name.equals(assistantCard.getName())) {
                assistantCardToGet = assistantCard;
            }
        }
        if (assistantCardToGet == null)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        owner.updateCurrentSelection(assistantCardToGet);
        return owner;
    }
}
