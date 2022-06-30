package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;

/**
 * @author Luca Muroni
 * This class is used to get the characterCard chosen by the curretn player
 */
public class GetChosenCharacterCard {
    private final MessageHandler messageHandler;
    private final ExpertGame game;
    private final ArrayList<CharacterCard> cards;

    /**
     * Constructor of the class
     * @param game is the current game
     * @param messageHandler is the handler of messages
     * @param cards is the arrayList of possible choices
     */
    public GetChosenCharacterCard(ExpertGame game, MessageHandler messageHandler, ArrayList<CharacterCard> cards) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.cards = cards;
    }

    /**
     * Method that handles the exchange of messages
     * @return the chosen characterCard
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws ModelErrorException when there isn't an object with the same info received from the client
     * @throws MalformedMessageException when a received message isn't correct
     */
    public CharacterCard handle() throws ClientDisconnectedException, ModelErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.cards.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size),topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (CharacterCard card : this.cards){
            message = new Message(CHARACTER_CARD.getFragment(), card.getName(), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        String cardName = this.messageHandler.getMessagePayloadFromStream(CHARACTER_CARD.getFragment());
        CharacterCard result = null;
        for (CharacterCard card : this.game.getGameCards()) {
            if (card.getName().equals(cardName)) {
                result = card;
                break;
            }
        }
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        return result;
    }
}