package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.asset.game.*;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the Network interface
 */
public class NetworkHandler implements Network {
    MessageHandler messageHandler;

    @Override
    public Player getCurrentPlayer() {

    }

    @Override
    public void getConnection() {
        GetConnection func = new GetConnection();
        func.handle();
    }

    @Override
    public void getLobbyStatus() {
        GetLobbyStatus func = new GetLobbyStatus();
        func.handle();
    }

    @Override
    public Phase getPhase() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        GetPhase func = new GetPhase(messageHandler);
        return func.handle();
    }

    @Override
    public void getPossibleCards(Game game) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        GetPossibleCards func = new GetPossibleCards(messageHandler, game);
        func.handle();
    }

    @Override
    public ArrayList<Island> getPossibleIslands(Game game) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        GetPossibleIslands func = new GetPossibleIslands(messageHandler, game);
        return func.handle();
    }

    @Override
    public void getNewOwner(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetNewOwner func = new GetNewOwner(messageHandler, game);
        func.handle();
    }

    @Override
    public ArrayList<Cloud> getPossibleClouds(Game game) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        GetPossibleClouds func = new GetPossibleClouds(messageHandler, game);
        return func.handle();
    }

    @Override
    public ArrayList<Gamer> getWinner(Game game) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException{
        GetWinner func = new GetWinner(messageHandler, game);
        return func.handle();
    }

    @Override
    public void getCloudStatus(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetCloudStatus func = new GetCloudStatus(messageHandler, game);
        func.handle();
    }

    @Override
    public void getDashboard(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetDashboard func = new GetDashboard(messageHandler, game);
        func.handle();
    }

    @Override
    public void getIslandStatus(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetIslandStatus func = new GetIslandStatus(messageHandler, game);
        func.handle();
    }

    @Override
    public void getMotherNaturePlace(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMotherNaturePlace func = new GetMotherNaturePlace(messageHandler, game);
        func.handle();
    }

    @Override
    public void getChosenAssistantCard(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(messageHandler, game);
        func.handle();
    }

    @Override
    public ArrayList<AssistantCardDeckFigures> getPossibleDecks() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetPossibleDecks func = new GetPossibleDecks(messageHandler);
        return func.handle();
    }

    @Override
    public void sendInfo(Player player, Game game) throws MalformedMessageException {
        SendInfo func = new SendInfo(player, game, messageHandler);
        func.handle();
    }

    @Override
    public void sendCard(AssistantCard card) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
        SendCard func = new SendCard(card, messageHandler);
        func.handle();
    }

    @Override
    public void sendColor(PawnColor color) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
        SendStudentColor func = new SendStudentColor(color, messageHandler);
        func.handle();
    }

    @Override
    public void sendLocation(int location) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
        SendStudentLocation func = new SendStudentLocation(location, messageHandler);
        func.handle();
    }

    @Override
    public void sendIsland(Island island) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
        SendIsland func = new SendIsland(island, messageHandler);
        func.handle();
    }

    @Override
    public void sendCloud(Cloud cloud) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
        SendCloud func = new SendCloud(cloud, messageHandler);
        func.handle();
    }

    @Override
    public void sendAssistantCardDeck(AssistantCardDeckFigures assistantCardDeck) throws MalformedMessageException {
        SendCardDeck func = new SendCardDeck(assistantCardDeck, messageHandler);
        func.handle();
    }
}
