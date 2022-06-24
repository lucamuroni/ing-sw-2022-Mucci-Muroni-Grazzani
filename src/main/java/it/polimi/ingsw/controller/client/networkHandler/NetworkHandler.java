package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.*;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the Network interface
 */
public class NetworkHandler implements Network {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the message handler to use
     */
    public NetworkHandler(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    @Override
    public void broadcastPlayerInfo(Game game) throws MalformedMessageException {
        BroadcastPlayerInfo func = new BroadcastPlayerInfo(messageHandler,game);
        func.handle();
    }

    @Override
    public void awaitForLobby() throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        AwaitForLobby func = new AwaitForLobby(this.messageHandler);
        func.handle();
    }

    @Override
    public void getUsernames(Game game) throws MalformedMessageException, ClientDisconnectedException {
        GetUsernames func = new GetUsernames(messageHandler, game);
        func.handle();
    }

    public void getTowerColor(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetTowerColor func = new GetTowerColor(messageHandler, game);
        func.handle();
    }

    @Override
    public void getCurrentPlayer(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetCurrentPlayer func = new GetCurrentPlayer(messageHandler, game);
        func.handle();
    }

    @Override
    public void getConnection(ClientController controller) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        GetConnection func = new GetConnection(this.messageHandler);
        int result = func.handle();
        Gamer self = new Gamer(result);
        Game game = new Game(self);
        controller.setGame(game);
    }

    public String getContext() throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetContext func = new GetContext(messageHandler);
        return func.handle();
    }

    /**
     * Method that returns the new current phase
     * @return the current phase
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public Phase getPhase() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        GetPhase func = new GetPhase(messageHandler);
        return func.handle();
    }

    /**
     * Method that updates the possible cards to choose from
     * @param game represents the current game
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void getPossibleCards(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        GetPossibleCards func = new GetPossibleCards(messageHandler, game);
        func.handle();
    }

    /**
     * Method that returns the arraylist of the possible islands on which mother nature can move on
     * @param game represents the current game
     * @return the available islands
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public ArrayList<Island> getPossibleIslands(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        GetPossibleIslands func = new GetPossibleIslands(messageHandler, game);
        return func.handle();
    }

    /**
     * Method that updates the new owner's color for the island on which mother nature is on
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getNewOwner(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetOwner func = new GetOwner(messageHandler, game);
        func.handle();
        //Al momento non esiste alcuna chiamata a questa classe perchè già updateIsland gestisce la cosa
    }

    /**
     * Method that returns the arraylist with the possible clouds to choose from
     * @param game represents the current game
     * @return the available clouds
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public ArrayList<Cloud> getPossibleClouds(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        GetPossibleClouds func = new GetPossibleClouds(messageHandler, game);
        return func.handle();
    }


    @Override
    public ArrayList<Gamer> getWinner(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        GetWinner func = new GetWinner(messageHandler, game);
        return func.handle();
    }
    /**
     * Method that returns the arraylist with the winner (or winners) for the current game
     * @param game represents the current game
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void getCloudStatus(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetCloudStatus func = new GetCloudStatus(messageHandler, game);
        func.handle();
    }

    @Override
    public void getMergedIslands(ViewHandler viewHandler) throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        GetMergedIslands func = new GetMergedIslands(messageHandler, viewHandler);
        func.handle();
    }

    /**
     * Method that updates the clouds' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getDashboard(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetDashboard func = new GetDashboard(messageHandler, game);
        func.handle();
    }

    /**
     * Method that updates the dashboards' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getIslandStatus(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetIslandStatus func = new GetIslandStatus(messageHandler, game);
        func.handle();
    }

    /**
     * Method that updates the islands' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getMotherNaturePlace(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetMotherNaturePlace func = new GetMotherNaturePlace(messageHandler, game);
        func.handle();
    }

    /**
     * Method that sets the chosen assistant card
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getChosenAssistantCard(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(messageHandler, game);
        func.handle();
    }

    public void getChosenAssistantCardDeck(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetChosenAssistantCardDeck func = new GetChosenAssistantCardDeck(messageHandler, game);
        func.handle();
    }

    /**
     * Method that returns the arraylist with the possible decks to choose from
     * @return the available decks
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public ArrayList<AssistantCardDeckFigures> getPossibleDecks() throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
        GetPossibleDecks func = new GetPossibleDecks(messageHandler);
        return func.handle();
    }

    public void getCharacterCard(Game game) throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        GetCharacterCard func = new GetCharacterCard(messageHandler, game);
        func.handle();
    }

    public void getCoins(Game game) throws MalformedMessageException, ClientDisconnectedException {
        GetCoins func = new GetCoins(messageHandler, game);
        func.handle();
    }

    @Override
    public void sendInfo(Gamer gamer, String gameType, int players, String lobby) throws MalformedMessageException {
        SendInfo func = new SendInfo(gamer, gameType, players, lobby, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the chosen assistant card to the server
     * @param card the chosen assistant card
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void sendCard(AssistantCard card) throws FlowErrorException, MalformedMessageException {
        SendCard func = new SendCard(card, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the color of the moved student to the server
     * @param color represents the color of the student
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void sendColor(PawnColor color) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendStudentColor func = new SendStudentColor(color, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the location of the moved student to the server
     * @param location represents the student's new location
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void sendLocation(int location) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendStudentLocation func = new SendStudentLocation(location, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the mother nature's new location to the server
     * @param island the new island to be sent
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void sendIsland(Island island) throws FlowErrorException, MalformedMessageException {
        SendIsland func = new SendIsland(island, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the chosen clouds to the server
     * @param cloud the chosen cloud
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void sendCloud(Cloud cloud) throws FlowErrorException, MalformedMessageException {
        SendCloud func = new SendCloud(cloud, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the chosen assistant card deck to the server
     * @param assistantCardDeck the chosen deck
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void sendAssistantCardDeck(AssistantCardDeckFigures assistantCardDeck) throws MalformedMessageException {
        SendCardDeck func = new SendCardDeck(assistantCardDeck, messageHandler);
        func.handle();
    }
}

