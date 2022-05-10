package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.ASSISTANT_CARD;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;

public class SendChosenAssistantCard {
    //riceve, in handle, una assistantCard e il token del giocatore.
    //lo chiami per uploadare al giocatore che carta il giocatore corrente ha scelto.
    private AssistantCard card;
    Integer token;
    MessageHandler messageHandler;
    public SendChosenAssistantCard(AssistantCard card, Integer token, MessageHandler messageHandler) {
        this.card = card;
        this.token = token;
        this.messageHandler = messageHandler;
    }
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(ASSISTANT_CARD.getFragment(),this.card.getName(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD.getFragment());
    }
}
