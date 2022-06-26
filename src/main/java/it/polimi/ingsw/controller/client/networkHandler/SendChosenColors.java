package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class SendChosenColors {
    private final MessageHandler messageHandler;
    private final ArrayList<PawnColor> colors;

    public SendChosenColors(MessageHandler messageHandler, ArrayList<PawnColor> colors) {
        this.messageHandler = messageHandler;
        this.colors = colors;
    }

    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        int topic = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(colors.size()), topic);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (int i = 0; i<this.colors.size(); i++) {
            message = new Message(COLOR.getFragment(), colors.get(i).toString(), topic);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        this.messageHandler.assertOnEquals(OK.getFragment(), COLOR.getFragment());
    }
}
