package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Cloud;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.CLOUD_ID;

public class GetChosenCloud {
    ArrayList<Cloud> clouds;
    MessageHandler messageHandler;
    public GetChosenCloud(ArrayList<Cloud> clouds, MessageHandler messageHandler) {
        this.clouds = clouds;
        this.messageHandler = messageHandler;
    }
    public Cloud handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (Cloud cloud: this.clouds) {
            messages.add(new Message(CLOUD_ID.getFragment(), cloud.getID(), topicId));
        }
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming());
        String cloudID;
        Cloud result = null;
        cloudID = this.messageHandler.getMessagePayloadFromStream(CLOUD_ID.getFragment());
        for (Cloud cloud : this.clouds) {
            if (cloud.getID().equals(cloudID))
                result = cloud;
        }
        return result;
    }
}
