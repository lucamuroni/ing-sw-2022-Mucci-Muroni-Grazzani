package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.networkHandler.*;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the Network interface
 */
public class ClientController implements Network {
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
    public Phase getPhase(Phase phase) {
        GetPhase func = new GetPhase(phase, messageHandler);
        Phase result = func.handle();
        return result;
    }

    @Override
    public ArrayList<AssistantCard> getPossibleCards() {
        GetPossibleCards func = new GetPossibleCards(messageHandler);
        func.handle();
    }

    @Override
    public void sendCard(AssistantCard card) {
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
    public ArrayList<Island> getPossibleIslands() {
        GetPossibleIslands func = new GetPossibleIslands(messageHandler);
        ArrayList<Island> result = func.handle();
        return result;
    }

    @Override
    public void sendIsland(Island island) {
        SendIsland func = new SendIsland(island, messageHandler);
        func.handle();
    }

    @Override
    public Player getNewOwner() {
        GetNewOwner func = new GetNewOwner(messageHandler);
        Player result = func.handle();
        return result;
    }

    @Override
    public ArrayList<Cloud> getPossibleClouds() {
        GetPossibleClouds func = new GetPossibleClouds(messageHandler);
        ArrayList<Cloud> result = func.handle();
        return result;
    }

    @Override
    public void sendCloud(Cloud cloud) {
        SendCloud func = new SendCloud(cloud, messageHandler);
        func.handle();
    }

    @Override
    public ArrayList<Player> getWinner(){
        GetWinner func = new GetWinner(messageHandler);
        ArrayList<Player> result = func.handle();
        return result;
    }

    @Override
    public void getCloudStatus() {
        GetCloudStatus func = new GetCloudStatus(messageHandler);
        func.handle();
    }

    @Override
    public void getDashboard() {
        GetDashboard func = new GetDashboard(messageHandler);
        func.handle();
    }

    @Override
    public void getIslandStatus() {
        GetIslandStatus func = new GetIslandStatus(messageHandler);
        func.handle();
    }

    @Override
    public void getMotherNaturePlace() {
        GetMotherNaturePlace func = new GetMotherNaturePlace(messageHandler);
        func.handle();
    }
}
