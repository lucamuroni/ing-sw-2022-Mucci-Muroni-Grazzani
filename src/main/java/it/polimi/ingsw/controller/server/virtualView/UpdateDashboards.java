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
        messages.addAll(this.messageHandler.writeOutAndWait(ConnectionTimings.CONNECTION_STARTUP.getTiming()));
        for (Gamer gamer : this.gamers) {
            this.messageHandler.assertOnEquals(OK.getFragment(), OWNER.getFragment(), messages);
            this.messageHandler.assertOnEquals(OK.getFragment(), NUM_TOWERS.getFragment(), messages);
            //TODO: Controllare con Grazza: l'idea è che va controllato se tutti gli studenti della waiting room sono arrivati e non solo vedere se c'è almeno
            // un messaggio con questo header
            for (Student student : gamer.getDashboard().getWaitingRoom()) {
                this.messageHandler.assertOnEquals(OK.getFragment(), WAITING_STUDENT.getFragment(), messages);
            }
            //TODO: Controllare con Grazza: stesso concetto del for soprastante, ma semplificato perché si controlla solo che siano arrivati dei messaggi
            // di "ok" tanti quanti sono i colori delle pedine studente
            for (PawnColor color : PawnColor.values()) {
                this.messageHandler.assertOnEquals(OK.getFragment(), HALL_STUDENT.getFragment(), messages);
            }
        }

    }
}
