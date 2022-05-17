package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Cloud;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.CLOUD;
import static it.polimi.ingsw.controller.networking.MessageFragment.STOP;

public class GetChosenCloud {
    ArrayList<Cloud> clouds;
    MessageHandler messageHandler;
    public GetChosenCloud(ArrayList<Cloud> clouds, MessageHandler messageHandler) {
        this.clouds = clouds;
        this.messageHandler = messageHandler;
    }
    public Cloud handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (Cloud cloud: clouds) {
            Message message = new Message(CLOUD.getFragment(), cloud.getID(), topicId);
            this.messageHandler.write(message);
        }
        this.messageHandler.write(new Message(STOP.getFragment(), "", topicId));
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        String cloudID;
        Cloud result = null;
        cloudID = this.messageHandler.getMessagePayloadFromStream(CLOUD.getFragment());
        for (Cloud cloud : clouds) {
            if (cloud.getID().equals(cloudID))
                result = cloud;
        }
        return result;
    }
}
