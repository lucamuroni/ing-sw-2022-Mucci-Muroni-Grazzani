package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the messages to get the choosen card deck the current player chooses (to update the view)
 */
public class GetChosenAssistantCardDeck {
    private MessageHandler messageHandler;
    private Game game;
    private AssistantCardDeckFigures deck;

    /**
     * Class constructor
     * @param messageHandler represents the message handler used for the message
     * @param game represents the current game
     */
    public GetChosenAssistantCardDeck(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the messages to get the chosen deck
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
        String name = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
        for (AssistantCardDeckFigures deckFigures: AssistantCardDeckFigures.values()) {
            if (name.equals(deckFigures.name())) {
                deck = deckFigures;
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD_DECK.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        assert owner != null;
        owner.updateFigure(deck);
    }
}

