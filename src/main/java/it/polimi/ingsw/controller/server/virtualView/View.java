package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * @author Sara Mucci
 * Interface with all the messsages method executed by the server
 */
public interface View{
    /**
     * Method that handles the messages to set a new current player
     * @param player represents the new current player
     */
    public void setCurrentPlayer(Player player);

    /**
     * Methos that handles the messages to update the status of an island
     * @param island represents the island to update
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void updateIslandStatus(Island island) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to the status of more than one island (EG: when there is an archipelago)
     * @param islands represents the islands to update
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void updateIslandStatus(ArrayList<Island> islands) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to update the clouds status
     * @param cloud the cloud to update
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void updateCloudsStatus(Cloud cloud) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to update the clouds status
     * @param clouds the clouds to update
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void updateCloudsStatus(ArrayList<Cloud> clouds) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     *
     */
    public void haltOnError(); //TODO: scrivere il metodo

    /**
     * Method that handles the messages to update the status of the dashboards
     * @param gamers represents the players
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void updateDashboards(ArrayList<Gamer> gamers, Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException;

    /**
     * Method that handles the messages to update the status of the dashboards
     * @param gamer represents the player
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void updateDashboards(Gamer gamer, Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException;


    /**
     * Methos that handles the messages to update the mother nature location
     * @param island represents the new mother nature location
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException;

    /**
     * Method that handles the messages to get the assistant card deck the current player chooses
     * @param cardDeck represents the available decks
     * @return the chosen deck
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public AssistantCardDeckFigures getChosenAssistantCardDeck(ArrayList<AssistantCardDeckFigures> cardDeck) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the assistant card deck chosen by the current player
     * @param deck represents the chosen deck
     * @param token represents the token associated to the current player
     * @param gamer represents the currentGamer
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void sendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, Gamer gamer) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the assistant card the current player chooses
     * @param cardsList represents the available cards
     * @return the chosen card
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public AssistantCard getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the assistant card chosen by the current player
     * @param card represents the chosen card
     * @param token represents the token associated to the current player
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void sendChosenAssistantCard(AssistantCard card, Integer token) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     *Method that handles the messages to get the color of the student the current player moves
     * @return the color of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public PawnColor getMovedStudentColor() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the location of the student the current player moves
     * @return the location of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public int getMovedStudentLocation() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the new mother nature location
     * @param islands represents the available islands
     * @return the new mother nature location
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public Island getMNLocation(ArrayList<Island> islands) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the color of the current player
     * @param color represents the current player's color
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void sendTowerColor(TowerColor color) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException;

    /**
     * Method that handles the messages to get the cloud chosen by the current player
     * @param clouds represents the available islands
     * @return the chosen island
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public Cloud getChosenCloud(ArrayList<Cloud> clouds) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the new phase to the current player
     * @param phase represents the new phase
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void sendNewPhase(Phase phase) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;

    /**
     * Method that handles the message to send the username of the winner / the usernames of the winners in case of a draw
     * @param names represents the usernames to be sent
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void sendWinner(ArrayList<String> names) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;
}
