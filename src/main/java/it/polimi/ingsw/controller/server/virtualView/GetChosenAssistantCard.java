package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.ASSISTANT_CARD;
import static it.polimi.ingsw.controller.networking.MessageFragment.STOP;

class GetChosenAssistantCard {
    ArrayList<AssistantCard> cards;
    MessageHandler messageHandler;

    public GetChosenAssistantCard(ArrayList<AssistantCard> cards, MessageHandler messageHandler){
        this.cards = cards;
        this.messageHandler = messageHandler;
    }

    public AssistantCard handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (AssistantCard card : this.cards){
            Message message = new Message(ASSISTANT_CARD.getFragment(), card.getName(), topicId);
            this.messageHandler.write(message);
        }
        this.messageHandler.write(new Message(STOP.getFragment(), "", topicId));
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        String cardName;
        AssistantCard result = null;
        cardName = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment());
        for (AssistantCard card : this.cards) {
            if (card.getName().equals(cardName))
                result = card;
        }
        return result;
    }
}
