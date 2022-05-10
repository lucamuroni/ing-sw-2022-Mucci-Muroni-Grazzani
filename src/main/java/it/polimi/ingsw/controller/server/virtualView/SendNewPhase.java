package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.MessageFragment.PHASE;

public class SendNewPhase {
    MessageHandler messageHandler;
    Phase phase;
    public SendNewPhase(Phase phase, MessageHandler messageHandler) {
        this.phase = phase;
        this.messageHandler = messageHandler;
    }
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(PHASE.getFragment(), phase, topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), PHASE.getFragment());
    }
}
