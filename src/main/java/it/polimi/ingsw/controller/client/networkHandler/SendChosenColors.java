package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * This class is used to send the colors of the students chosen by players while playing a characterCard of the
 * expert version
 */
public class SendChosenColors {
    private final MessageHandler messageHandler;
    private final ArrayList<PawnColor> colors;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param colors is the arrayList of colors to send
     */
    public SendChosenColors(MessageHandler messageHandler, ArrayList<PawnColor> colors) {
        this.messageHandler = messageHandler;
        this.colors = colors;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topic = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(colors.size()), topic);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (PawnColor color : this.colors) {
            message = new Message(COLOR.getFragment(), color.toString(), topic);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        this.messageHandler.assertOnEquals(OK.getFragment(), COLOR.getFragment());
    }
}