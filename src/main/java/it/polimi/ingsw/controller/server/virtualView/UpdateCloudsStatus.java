package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.pawn.Student;
import static it.polimi.ingsw.controller.networking.MessageFragment.*;
import static java.lang.Integer.valueOf;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to update che status of the clouds
 */
public class UpdateCloudsStatus {
    ArrayList<Cloud> clouds;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param clouds represents the clouds to update
     * @param messageHandler represents the messageHandler used for the message
     */
    public UpdateCloudsStatus(ArrayList<Cloud> clouds, MessageHandler messageHandler) {
        this.clouds = clouds;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (Cloud cloud : this.clouds) {
            Integer token = valueOf(cloud.getID());
            messages.add(new Message(CLOUD.getFragment(), token.toString(), topicId));
            if (cloud.isEmpty()) {
                messages.add(new Message(CLOUD.getFragment(), "", topicId));
            }
            else {
                ArrayList<Student> students = cloud.getStudents();
                for (Student student : students) {
                    messages.add(new Message(STUDENT_COLOR.getFragment(), student.getColor().toString(), topicId));
                }
            }
        }
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), CLOUD.getFragment());
    }
}
