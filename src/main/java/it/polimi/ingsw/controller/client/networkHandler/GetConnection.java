package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.AUTH_ID;

public class GetConnection {
    private MessageHandler messageHandler;


    public GetConnection(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    public int handle() throws TimeHasEndedException, ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        System.out.println("aspetto un messaggio");
        this.messageHandler.read(ConnectionTimings.CONNECTION_STARTUP.getTiming());
        System.out.println("messaggio ricevuto");
        this.messageHandler.assertOnEquals("",MessageFragment.GREETINGS.getFragment());
        this.messageHandler.write(new Message(MessageFragment.GREETINGS.getFragment(),MessageFragment.OK.getFragment(), this.messageHandler.getMessagesUniqueTopic()));
        this.messageHandler.writeOut();
        System.out.println("messaggio inviato");
        this.messageHandler.read(ConnectionTimings.RESPONSE.getTiming());
        System.out.println("ho letto il secondo messaggio");
        return Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(AUTH_ID.getFragment()));
    }
}

