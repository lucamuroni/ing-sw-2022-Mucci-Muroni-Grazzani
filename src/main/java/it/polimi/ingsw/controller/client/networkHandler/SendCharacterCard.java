package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;

public class SendCharacterCard {
    private final MessageHandler messageHandler;
    private final CharacterCard card;

    public SendCharacterCard(MessageHandler messageHandler, CharacterCard card) {
        this.messageHandler = messageHandler;
        this.card  = card;
    }

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
