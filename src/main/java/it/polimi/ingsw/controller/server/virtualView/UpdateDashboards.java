package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.*;
import static java.lang.Integer.valueOf;

public class UpdateDashboards {
    private ArrayList<Gamer> gamers;
    private MessageHandler messageHandler;
    public UpdateDashboards(ArrayList<Gamer> gamers, MessageHandler messageHandler){
        this.gamers = gamers;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (Gamer gamer : this.gamers) {
            Integer token = valueOf(gamer.getToken());
            messages.add(new Message(OWNER.getFragment(), token.toString(), topicId));
            Integer num = valueOf(gamer.getDashboard().getNumTowers());
            messages.add(new Message(NUM_TOWERS.getFragment(), num.toString(), topicId));
            for (Student student : gamer.getDashboard().getWaitingRoom()) {
                messages.add(new Message(WAITING_STUDENT.getFragment(), student.getColor().toString(), topicId));
            }
            for (PawnColor color : PawnColor.values()) {
                //TODO: Controllare con Grazza: ho dovuto inserire per forza un getHall, altrimenti non esisteva un metodo per ritornare
                // gli studenti della hall
                Integer numStud = Math.toIntExact(gamer.getDashboard().getHall().stream().filter(x -> x.getColor().equals(color)).count());
                messages.add(new Message(HALL_STUDENT.getFragment(), numStud.toString(), topicId));
            }
        }
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), DASHBOARD.getFragment());
    }
}
