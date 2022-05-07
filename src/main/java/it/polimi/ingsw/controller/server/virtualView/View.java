package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import java.util.ArrayList;

public interface View{
    public void setCurrentPlayer(Player player);
    public void updateIslandStatus(Island island);
    public void updateIslandStatus(ArrayList<Island> islands);
    public void haltOnError();
    public void updateDashboards(ArrayList<Gamer> gamers);
    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException;
    public String getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;
}
