package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.*;
import static java.lang.Integer.valueOf;

public class UpdateIslandStatus {
    private Island island;
    private MessageHandler messageHandler;
    public UpdateIslandStatus(Island island, MessageHandler messageHandler){
        this.island = island;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Integer num = valueOf(this.island.getNumTowers());
        messages.add(new Message(NUM_TOWERS.getFragment(), num.toString(), topicId));
        Integer token = null;
        if (this.island.getOwner().isPresent()){
            token = valueOf(this.island.getOwner().get().getToken());
        }
        else {
            token = valueOf(0);
        }
        messages.add(new Message(OWNER.getFragment(), token.toString(), topicId));
        for (Student student : this.island.getStudents()) {
            messages.add(new Message(STUDENT_COLOR.getFragment(), student.getColor().toString(), topicId));
        }
        this.messageHandler.write(messages);
        messages.clear();
        messages.addAll(this.messageHandler.writeOutAndWait(ConnectionTimings.CONNECTION_STARTUP.getTiming()));
        this.messageHandler.assertOnEquals(OK.getFragment(), OWNER.getFragment(), messages);
        this.messageHandler.assertOnEquals(OK.getFragment(), NUM_TOWERS.getFragment(), messages);
        //TODO: Controllare con Grazza se idea è giusta: vedo se nei messaggi ho ricevuto un "ok" per tutti gli studenti
        // passati, altrimenti la seconda possibilità è il controllo di un solo messaggio di "ok" comprensivo per tutti gli studenti ricevuti
        for (int i = 0; i < this.island.getStudents().size(); i++)
            this.messageHandler.assertOnEquals(OK.getFragment(), STUDENT_COLOR.getFragment(), messages);
    }
}
