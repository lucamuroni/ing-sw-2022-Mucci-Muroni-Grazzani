package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.*;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Interface with all the messages methods executed by the client
 */
public interface Network {
    void getUsernames(Game game) throws MalformedMessageException, ClientDisconnectedException;

    void getCurrentPlayer(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    void getTowerColor(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    void getConnection(ClientController controller) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    String getContext() throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    /**
     * Method that returns the new current phase
     * @return the current phase
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    Phase getPhase() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException;

    /**
     * Method that updates the possible cards to choose from
     * @param game represents the current game
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    void getPossibleCards(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException;

    /**
     * Method that returns the arraylist of the possible islands on which mother nature can move on
     * @param game represents the current game
     * @return the available islands
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    ArrayList<Island> getPossibleIslands(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException;

    /**
     * Method that updates the new owner's color for the island on which mother nature is on
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    void getNewOwner(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    /**
     * Method that returns the arraylist with the possible clouds to choose from
     * @param game represents the current game
     * @return the available clouds
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    ArrayList<Cloud> getPossibleClouds(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException;

    /**
     * Method that returns the arraylist with the winner (or winners) for the current game
     * @param game represents the current game
     * @return the winner/winners
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    ArrayList<Gamer> getWinner(Game game) throws ClientDisconnectedException, MalformedMessageException, AssetErrorException;

    /**
     * Method that updates the clouds' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    void getCloudStatus(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    void getMergedIslands(ViewHandler viewHandler) throws AssetErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that updates the dashboards' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    void getDashboard(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    /**
     * Method that updates the islands' status in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    void getIslandStatus(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    /**
     * Method that updates the mother nature's new place in the view
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    void getMotherNaturePlace(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    /**
     * Method that sets the chosen assistant card
     * @param game represents the current game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    void getChosenAssistantCard(Game game) throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    void getChosenAssistantCardDeck(Game game) throws MalformedMessageException,  ClientDisconnectedException, AssetErrorException;

    /**
     * Method that returns the arraylist with the possible decks to choose from
     * @return the available decks
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    ArrayList<AssistantCardDeckFigures> getPossibleDecks() throws MalformedMessageException, ClientDisconnectedException, AssetErrorException;

    void getCharacterCard(Game game) throws AssetErrorException, MalformedMessageException, ClientDisconnectedException;

    void getCoins(Game game) throws MalformedMessageException, ClientDisconnectedException;

    ArrayList<CharacterCard> getPossibleCharacters(Game game) throws AssetErrorException, MalformedMessageException, ClientDisconnectedException;

    void getChosenCharacterCard(Game game) throws AssetErrorException, MalformedMessageException, ClientDisconnectedException;

    void sendCharacterCard(CharacterCard card) throws MalformedMessageException;

    void sendChosenColors(ArrayList<PawnColor> colors) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    void sendAnswer(boolean answer) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    void sendInfo(Gamer gamer, String gameType, int players, String lobby) throws MalformedMessageException;

    /**
     * Method that sends the chosen assistant card to the server
     * @param card the chosen assistant card
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    void sendCard(AssistantCard card) throws FlowErrorException, MalformedMessageException;

    /**
     * Method that sends the color of the moved student to the server
     * @param color represents the color of the student
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    void sendColor(PawnColor color) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that sends the location of the moved student to the server
     * @param location represents the student's new location
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    void sendLocation(int location) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that sends the mother nature's new location to the server
     * @param island the new island to be sent
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    void sendIsland(Island island) throws FlowErrorException, MalformedMessageException;

    /**
     * Method that sends the chosen clouds to the server
     * @param cloud the chosen cloud
     * @throws FlowErrorException launched when the server sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    void sendCloud(Cloud cloud) throws FlowErrorException, MalformedMessageException;

    /**
     * Method that sends the chosen assistant card deck to the server
     * @param assistantCardDeck the chosen deck
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    void sendAssistantCardDeck(AssistantCardDeckFigures assistantCardDeck) throws MalformedMessageException;

    void broadcastPlayerInfo(Game game) throws MalformedMessageException;

    void awaitForLobby() throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;
}
