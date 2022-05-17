package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.client.game.GamePhase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.*;

import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Interface with all the messages methods executed by the client
 */
public interface Network {
    public Player getCurrentPlayer();

    public void getConnection();

    public void sendInfo(Player player, Game game) throws MalformedMessageException;

    public void getLobbyStatus();

    public GamePhase getPhase();

    public ArrayList<AssistantCard> getPossibleCards() throws TimeHasEndedException, ClientDisconnectedException;

    public void sendCard(AssistantCard card) throws FlowErrorException, MalformedMessageException;

    public void sendColor(PawnColor color);

    public void sendLocation(int location);

    public ArrayList<it.polimi.ingsw.model.Island> getPossibleIslands() throws TimeHasEndedException, ClientDisconnectedException;

    public void sendIsland(Island island);

    public Player getNewOwner();

    public ArrayList<Cloud> getPossibleClouds() throws TimeHasEndedException, ClientDisconnectedException;

    public void sendCloud(Cloud cloud) throws FlowErrorException, MalformedMessageException;

    public ArrayList<Player> getWinner();

    public Cloud getCloudStatus();

    public DashBoard getDashboard();

    public Island getIslandStatus();

    public Island getMotherNaturePlace();
}
