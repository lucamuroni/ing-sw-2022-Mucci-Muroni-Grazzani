package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.asset.game.DashBoard;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OWNER;

/**
 * @author Sara Mucci
 * Class that updates the dashboards in the view
 */
public class GetDashboard {
    MessageHandler messageHandler;
    Boolean stop = false;

    public GetDashboard(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public DashBoard handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String string = this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment());

        }
    }
}
