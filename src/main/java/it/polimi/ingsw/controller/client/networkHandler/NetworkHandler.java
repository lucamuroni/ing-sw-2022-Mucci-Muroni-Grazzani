package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.client.game.GamePhase;
import it.polimi.ingsw.controller.networking.MessageHandler;
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
    public void sendInfo(Player player, Game game) throws MalformedMessageException {
        SendInfo func = new SendInfo( player, game, messageHandler);
        func.handle();
    }

    @Override
    public void getLobbyStatus() {
        GetLobbyStatus func = new GetLobbyStatus();
        func.handle();
    }

    @Override
    public GamePhase getPhase() {
        GetPhase func = new GetPhase(messageHandler);
        return func.handle();
    }

    @Override
    public ArrayList<AssistantCard> getPossibleCards() throws TimeHasEndedException, ClientDisconnectedException {
        GetPossibleCards func = new GetPossibleCards(messageHandler);
        return func.handle();
    }

    @Override
    public void sendCard(AssistantCard card) throws FlowErrorException, MalformedMessageException {
        SendCard func = new SendCard(card, messageHandler);
        func.handle();
    }

    @Override
    public void sendColor(PawnColor color) {
        SendStudentColor func = new SendStudentColor(color, messageHandler);
        func.handle();
    }

    @Override
    public void sendLocation(int location) {
        SendStudentLocation func = new SendStudentLocation(location, messageHandler);
        func.handle();
    }

    @Override
    public ArrayList<Island> getPossibleIslands() throws TimeHasEndedException, ClientDisconnectedException {
        GetPossibleIslands func = new GetPossibleIslands(messageHandler);
        return func.handle();
    }

    @Override
    public void sendIsland(Island island) {
        SendIsland func = new SendIsland(island, messageHandler);
        func.handle();
    }

    @Override
    public Player getNewOwner() {
        GetNewOwner func = new GetNewOwner(messageHandler);
        return func.handle();
    }

    @Override
    public ArrayList<Cloud> getPossibleClouds() throws TimeHasEndedException, ClientDisconnectedException {
        GetPossibleClouds func = new GetPossibleClouds(messageHandler);
        return func.handle();
    }

    @Override
    public void sendCloud(Cloud cloud) throws FlowErrorException, MalformedMessageException {
        SendCloud func = new SendCloud(cloud, messageHandler);
        func.handle();
    }

    @Override
    public ArrayList<Player> getWinner(){
        GetWinner func = new GetWinner(messageHandler);
        return func.handle();
    }

    @Override
    public Cloud getCloudStatus() {
        GetCloudStatus func = new GetCloudStatus(messageHandler);
        return func.handle();
    }

    @Override
    public DashBoard getDashboard() {
        GetDashboard func = new GetDashboard(messageHandler);
        return func.handle();
    }

    @Override
    public Island getIslandStatus() {
        GetIslandStatus func = new GetIslandStatus(messageHandler);
        return func.handle();
    }

    @Override
    public Island getMotherNaturePlace() {
        GetMotherNaturePlace func = new GetMotherNaturePlace(messageHandler);
        return func.handle();
    }
}
