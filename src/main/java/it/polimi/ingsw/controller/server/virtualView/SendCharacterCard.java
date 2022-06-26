package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

public class SendCharacterCard {
    private final MessageHandler messageHandler;
    private final CharacterCard card;

    public SendCharacterCard(MessageHandler messageHandler, CharacterCard card) {
        this.messageHandler = messageHandler;
        this.card = card;
    }

    public void handle() throws MalformedMessageException, FlowErrorException, ClientDisconnectedException {
        int topic = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(CHARACTER_CARD.getFragment(), card.getName(), topic);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), CHARACTER_CARD.getFragment());
    }
}
