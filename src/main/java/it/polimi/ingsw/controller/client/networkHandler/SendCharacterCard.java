package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class is used to send the character card chosen by the player
 */
public class SendCharacterCard {
    private final MessageHandler messageHandler;
    private final CharacterCard card;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param card is the chosen characterCard
     */
    public SendCharacterCard(MessageHandler messageHandler, CharacterCard card) {
        this.messageHandler = messageHandler;
        this.card  = card;
    }

    /**
     * Method that handles the exchange of messages
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws MalformedMessageException {
        Message message;
        if(card != null){
            message = new Message(CHARACTER_CARD.getFragment(), card.getName(), this.messageHandler.getMessagesUniqueTopic());
        }else{
            message = new Message(CHARACTER_CARD.getFragment(), "", this.messageHandler.getMessagesUniqueTopic());
        }
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}