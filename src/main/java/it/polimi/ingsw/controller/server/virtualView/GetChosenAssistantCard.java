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

class GetChosenAssistantCard {
    ArrayList<AssistantCard> cards;
    MessageHandler messageHandler;

    public GetChosenAssistantCard(ArrayList<AssistantCard> cards, MessageHandler messageHandler){
        this.cards = cards;
        this.messageHandler = messageHandler;
    }

    public String handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (AssistantCard card : this.cards){
            messages.add(new Message(ASSISTANT_CARD.getFragment(), card.getName(), topicId));
        }
        this.messageHandler.write(messages);
        messages.clear();
        //TODO : definire un nuovo tipo in connection timings -> la scelta delle carte non è automatica da parte del client quindi non si una CONNECTION_STARTUP
        messages.addAll(this.messageHandler.writeOutAndWait(ConnectionTimings.CONNECTION_STARTUP.getTiming()));
        //TODO : modificare qui per ricevere una carta vera e propria non una stringa
        String cardName;
        cardName = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment(), messages);
        return cardName;
    }
}
