package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Luca Muroni
 * This class is used to send the characterCards at the start of the game
 */
public class SendCharacterCard {
    private final MessageHandler messageHandler;
    private final CharacterCard card;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param card is the card that will be sent
     */
    public SendCharacterCard(MessageHandler messageHandler, CharacterCard card) {
        this.messageHandler = messageHandler;
        this.card = card;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topic = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(CHARACTER_CARD.getFragment(), card.getName(), topic);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), CHARACTER_CARD.getFragment());
    }
}