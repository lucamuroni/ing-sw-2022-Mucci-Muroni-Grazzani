package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.COLOR;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

public class GetChosenColors {
    private MessageHandler messageHandler;
    private ArrayList<PawnColor> colors;

    public GetChosenColors(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.colors = new ArrayList<>();
    }

    public ArrayList<PawnColor> handle() throws ClientDisconnectedException, MalformedMessageException {
        for (int i = 0; i<4; i++) {
            this.messageHandler.read();
            String col = this.messageHandler.getMessagePayloadFromStream(COLOR.getFragment());
            for (PawnColor color : PawnColor.values()) {
                if (color.toString().equals(col))
                    colors.add(color);
            }
        }
        Message message = new Message(COLOR.getFragment(), OK.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return colors;
    }
}
