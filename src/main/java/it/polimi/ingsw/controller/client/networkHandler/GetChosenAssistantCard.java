package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
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
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetChosenAssistantCard(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the messages to get the chosen assistant card
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        int id = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment()));
        Gamer owner = null;
        for (Gamer gamer : game.getGamers()) {
            if (gamer.getId() == id) {
                owner = gamer;
            }
        }
        String name = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment());
        for (AssistantCard assistantCard: AssistantCard.values()) {
            if (name.equals(assistantCard.getName())) {
                assistantCardToGet = assistantCard;
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        assert owner != null;
        owner.updateCurrentSelection(assistantCardToGet);
    }
}
