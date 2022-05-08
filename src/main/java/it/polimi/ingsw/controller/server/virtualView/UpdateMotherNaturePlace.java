package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.MN_LOCATION;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;

import static java.lang.Integer.valueOf;

//TODO : classe che gestisce l'aggiornamento della posizione di madre natura


class UpdateMotherNaturePlace {
    Island island;
    MessageHandler messageHandler;

    public UpdateMotherNaturePlace(Island island, MessageHandler messageHandler){
        this.island = island;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<>();
        Integer id = valueOf(island.getId());
        messages.add(new Message(MN_LOCATION.getFragment(),id.toString(), this.messageHandler.getNewUniqueTopicID()))
        this.messageHandler.write(messages);
        messages.clear();
        messages.addAll(this.messageHandler.writeOutAndWait(ConnectionTimings.CONNECTION_STARTUP.getTiming()));
        this.messageHandler.assertOnEquals(OK.getFragment(), MN_LOCATION.getFragment(), messages);
    }
}
