package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
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
 * Class that implements the view interface
 */
public class VirtualViewHandler implements View {

    MessageHandler messageHandler;

    /**
     * Method that handles the messages to update the mother nature location
     * @param island represents the new mother nature location
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        UpdateMotherNaturePlace func = new UpdateMotherNaturePlace(island, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to update the status of more than one island (EG: when there is an archipelago)
     * @param islands represents the islands to update
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateIslandStatus(ArrayList<Island> islands) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException {
        for(Island island : islands){
            this.updateIslandStatus(island);
        }
    }

    /**
     * Methos that handles the messages to update the status of an island
     * @param island represents the island to update
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateIslandStatus(Island island) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException {
        UpdateIslandStatus func = new UpdateIslandStatus(island, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to update the clouds' status
     * @param clouds the clouds to update
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateCloudsStatus(ArrayList<Cloud> clouds) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        for (Cloud cloud : clouds) {
            this.updateCloudsStatus(cloud);
        }
    }

    /**
     * Method that handles the messages to update one cloud's status
     * @param cloud the cloud to update
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateCloudsStatus(Cloud cloud) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        UpdateCloudsStatus func = new UpdateCloudsStatus(cloud, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to update the status of the dashboards
     * @param gamers represents the players
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void updateDashboards(ArrayList<Gamer> gamers, Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        for (Gamer gamer : gamers) {
            this.updateDashboards(gamer, game);
        }
    }

    /**
     * Method that handles the messages to update the status of one dashboard
     * @param gamer represents the player
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void updateDashboards(Gamer gamer, Game game) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        UpdateDashboards func = new UpdateDashboards(gamer, game, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to get the assistant card the current player chooses
     * @param cardsList represents the available cards
     * @return the chosen card
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public AssistantCard getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(cardsList, messageHandler);
        return func.handle();
    }

    /**
     *Method that handles the messages to get the color of the student the current player moves
     * @return the color of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public PawnColor getMovedStudentColor() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMovedStudentColor func = new GetMovedStudentColor(messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the location of the student the current player moves
     * @return the location of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public int getMovedStudentLocation() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMovedStudentLocation func = new GetMovedStudentLocation(messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the mother nature's new location
     * @param islands represents the available islands
     * @return the new mother nature location
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public Island getMNLocation(ArrayList<Island> islands) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMNLocation func = new GetMNLocation(islands, messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the cloud chosen by the current player
     * @param clouds represents the available islands
     * @return the chosen island
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public Cloud getChosenCloud(ArrayList<Cloud> clouds) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenCloud func = new GetChosenCloud(clouds, messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the assistant card deck the current player chooses
     * @param cardDeck represents the available decks
     * @return the chosen deck
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public AssistantCardDeckFigures getChosenAssistantCardDeck(ArrayList<AssistantCardDeckFigures> cardDeck) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenAssistantCardDeck func = new GetChosenAssistantCardDeck(cardDeck, messageHandler);
        return func.handle();
    }

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
    @Override
    public void sendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, Gamer gamer) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        SendChosenAssistantCardDeck func = new SendChosenAssistantCardDeck(deck, token, messageHandler, gamer);
        func.handle();
    }

    /**
     * Method that handles the messages to send the new phase to the current player
     * @param phase represents the new phase
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void sendNewPhase(Phase phase) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        SendNewPhase func = new SendNewPhase(phase, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the message to send the username of the winner / the usernames of the winners (in case of a draw)
     * @param names represents the usernames to be sent
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void sendWinner(ArrayList<String> names) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        SendWinner func = new SendWinner(names, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to send the assistant card chosen by the current player
     * @param card represents the chosen card
     * @param token represents the token associated to the current player
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void sendChosenAssistantCard(AssistantCard card, Integer token) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        SendChosenAssistantCard func = new SendChosenAssistantCard(card, token, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to send the color of the current player
     * @param color represents the current player's color
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void sendTowerColor(TowerColor color) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException{
        SendTowerColor func = new SendTowerColor(color, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the message to send the context to the players not currently playing
     * A context is sent to the players not currently playing to inform them of the phase in which the current player is
     * @param context represents the context to send
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws TimeHasEndedException launched when the available time for the response ends
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void sendContext(String context) throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        SendContext func = new SendContext(context, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to set a new current player
     * @param player represents the new current player
     */
    //TODO: controllare se è necessario creare un messaggio che aggiorni il current player o se questo metodo non serve
    @Override
    public void setCurrentPlayer(Player player) {
        this.messageHandler = player.getMessageHandler();
    }

    /**
     *
     */
    @Override
    public void haltOnError() {

    }
}

