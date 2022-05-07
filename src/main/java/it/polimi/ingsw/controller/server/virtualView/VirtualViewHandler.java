package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import org.hamcrest.core.Is;

import java.util.ArrayList;

public class VirtualViewHandler implements View {

    private MessageHandler messageHandler;

    @Override
    public void setCurrentPlayer(Player player) {
        this.messageHandler = player.getMessageHandler();
    }

    @Override
    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        UpdateMotherNaturePlace func = new UpdateMotherNaturePlace(island, messageHandler);
        func.handle();
    }

    @Override
    public void updateIslandStatus(ArrayList<Island> islands) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException {
        for(Island island : islands){
            updateIslandStatus(island);
        }
    }

    @Override
    public void updateIslandStatus(Island island) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException {
        UpdateIslandStatus func = new UpdateIslandStatus(island, messageHandler);
        func.handle();
    }

    @Override
    public void haltOnError() {

    }

    @Override
    public void updateDashboards(ArrayList<Gamer> gamers) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        UpdateDashboards func = new UpdateDashboards(gamers, messageHandler);
        func.handle();
    }

    public AssistantCard getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(cardsList, messageHandler);
        AssistantCard result = func.handle();
        return result;
    }
}

