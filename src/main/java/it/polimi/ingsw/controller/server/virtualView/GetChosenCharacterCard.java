package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.model.debug.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;

import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class GetChosenCharacterCard {
    private final MessageHandler messageHandler;
    private final ExpertGame game;

    public GetChosenCharacterCard(ExpertGame game, MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    public CharacterCard handle() throws ClientDisconnectedException, ModelErrorException, MalformedMessageException {
        this.messageHandler.read();
        String cardName = this.messageHandler.getMessagePayloadFromStream(CHARACTER.getFragment());
        CharacterCard result = null;
        for (CharacterCard card : this.game.getGameCards()) {
            if (card.getName().equals(cardName)) {
                result = card;
                break;
            }
        }
        if (result == null)
            throw new ModelErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(CHARACTER.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return result;
    }
}
