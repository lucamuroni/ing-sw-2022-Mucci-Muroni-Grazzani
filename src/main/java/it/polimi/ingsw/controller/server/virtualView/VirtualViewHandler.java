package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Island;
import org.hamcrest.core.Is;

import java.util.ArrayList;

public class VirtualViewHandler implements View {
    private MessageHandler messageHandler;

    public void setCurrentPlayer(Player player) {
        this.messageHandler = player.getMessageHandler();
    }

    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        UpdateMotherNaturePlace func = new UpdateMotherNaturePlace(island, messageHandler);
        func.handle();
    }

    public String getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(cardsList, messageHandler);
        String cardName = func.handle();
        return cardName;
    }
}