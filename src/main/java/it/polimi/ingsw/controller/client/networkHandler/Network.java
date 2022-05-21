package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Phase;
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

    public Phase getPhase() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException;

    public ArrayList<AssistantCard> getPossibleCards() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException;

    public void sendCard(AssistantCard card) throws FlowErrorException, MalformedMessageException, TimeHasEndedException;

    public void sendColor(PawnColor color) throws FlowErrorException, MalformedMessageException, TimeHasEndedException;

    public void sendLocation(int location) throws FlowErrorException, MalformedMessageException, TimeHasEndedException;

    public ArrayList<Island> getPossibleIslands() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException;

    public void sendIsland(Island island) throws FlowErrorException, MalformedMessageException, TimeHasEndedException;

    public Player getNewOwner();

    public ArrayList<Cloud> getPossibleClouds(Game game) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException;

    public void sendCloud(Cloud cloud) throws FlowErrorException, MalformedMessageException, TimeHasEndedException;

    public ArrayList<Gamer> getWinner() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException;

    public void getCloudStatus(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    public void getDashboard() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    public void getIslandStatus(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    public Island getMotherNaturePlace(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    public AssistantCard getChosenAssistantCard() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    public void sendAssistantCardDeck(AssistantCardDeckFigures assistantCardDeck) throws MalformedMessageException;

    public ArrayList<AssistantCardDeckFigures> getPossibleDecks() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

}
