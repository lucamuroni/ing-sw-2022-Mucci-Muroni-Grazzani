package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * This class is used to get the students chosen by the player when he plays the characterCard bard
 */
public class GetChosenColors {
    private final MessageHandler messageHandler;
    private final ArrayList<PawnColor> colors;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public GetChosenColors(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.colors = new ArrayList<>();
    }

    /**
     * Method that handles the exchange of messages
     * @return the answer of the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    public ArrayList<PawnColor> handle() throws ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read();
        int size = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<size; i++) {
            this.messageHandler.read();
            String col = this.messageHandler.getMessagePayloadFromStream(COLOR.getFragment());
            for (PawnColor color : PawnColor.values()) {
                if (color.toString().equals(col)){
                    colors.add(color);
                    break;
                }
            }
        }
        Message message = new Message(COLOR.getFragment(), OK.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return colors;
    }
}