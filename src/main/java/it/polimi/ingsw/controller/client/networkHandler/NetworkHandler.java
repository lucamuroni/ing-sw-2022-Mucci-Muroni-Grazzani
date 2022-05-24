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
    public Player getCurrentPlayer() {

    }

    @Override
    public void getConnection() {

    }

    @Override
    public void getLobbyStatus() {

    }

    /**
     * Method that returns the new current phase
     * @return the current phase
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public Phase getPhase() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        GetPhase func = new GetPhase(messageHandler);
        return func.handle();
    }

    /**
     * Method that updates the possible cards to choose from
     * @param game represents the current game
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void getPossibleCards(Game game) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        GetPossibleCards func = new GetPossibleCards(messageHandler, game);
        func.handle();
    }

    /**
     * Method that returns the arraylist of the possible islands on which mother nature can move on
     * @param game represents the current game
     * @return the available islands
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public ArrayList<Island> getPossibleIslands(Game game) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        GetPossibleIslands func = new GetPossibleIslands(messageHandler, game);
        return func.handle();
    }

    /**
     * Method that updates the new owner's color for the island on which mother nature is on
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getNewOwner(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetNewOwner func = new GetNewOwner(messageHandler, game);
        func.handle();
    }

    /**
     * Method that returns the arraylist with the possible clouds to choose from
     * @param game represents the current game
     * @return the available clouds
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
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
    /**
     * Method that returns the arraylist with the winner (or winners) for the current game
     * @param game represents the current game
     * @return the winner/winners
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    @Override
    public void getCloudStatus(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetCloudStatus func = new GetCloudStatus(messageHandler, game);
        func.handle();
    }

    /**
     * Method that updates the clouds' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getDashboard(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetDashboard func = new GetDashboard(messageHandler, game);
        func.handle();
    }

    /**
     * Method that updates the dashboards' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getIslandStatus(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetIslandStatus func = new GetIslandStatus(messageHandler, game);
        func.handle();
    }

    /**
     * Method that updates the islands' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getMotherNaturePlace(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMotherNaturePlace func = new GetMotherNaturePlace(messageHandler, game);
        func.handle();
    }

    /**
     * Method that sets the chosen assistant card
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void getChosenAssistantCard(Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(messageHandler, game);
        func.handle();
    }

    /**
     * Method that returns the arraylist with the possible decks to choose from
     * @return the available decks
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
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

    /**
     * Method that sends the chosen assistant card to the server
     * @param card the chosen assistant card
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     */
    @Override
    public void sendCard(AssistantCard card) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
        SendCard func = new SendCard(card, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the color of the moved student to the server
     * @param color represents the color of the student
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void sendColor(PawnColor color) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        SendStudentColor func = new SendStudentColor(color, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the location of the moved student to the server
     * @param location represents the student's new location
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    @Override
    public void sendLocation(int location) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        SendStudentLocation func = new SendStudentLocation(location, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the mother nature's new location to the server
     * @param island the new island to be sent
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     */
    @Override
    public void sendIsland(Island island) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
        SendIsland func = new SendIsland(island, messageHandler);
        func.handle();
    }

    /**
     * Method that sends the chosen clouds to the server
     * @param cloud the chosen cloud
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     */
    @Override
    public void sendCloud(Cloud cloud) throws FlowErrorException, MalformedMessageException, TimeHasEndedException {
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
