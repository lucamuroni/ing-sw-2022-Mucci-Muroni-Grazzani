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

public class UpdateCloudsStatus {
    private ArrayList<Cloud> clouds;
    private MessageHandler messageHandler;
    public UpdateCloudsStatus(ArrayList<Cloud> clouds, MessageHandler messageHandler) {
        this.clouds = clouds;
        this.messageHandler = messageHandler;
    }
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
        this.messageHandler.writeOutAndWait(ConnectionTimings.CONNECTION_STARTUP.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), CLOUD.getFragment());
    }
}
