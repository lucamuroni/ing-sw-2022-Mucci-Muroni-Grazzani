package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        UpdateMotherNaturePlace func = new UpdateMotherNaturePlace(island, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to update the status of more than one island (EG: when there is an archipelago)
     * @param islands represents the islands to update
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateIslandStatus(ArrayList<Island> islands) throws MalformedMessageException, FlowErrorException, ClientDisconnectedException {
        for(Island island : islands){
            this.updateIslandStatus(island);
        }
    }

    /**
     * Method that handles the messages to update the status of an island
     * @param island represents the island to update
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateIslandStatus(Island island) throws MalformedMessageException, FlowErrorException, ClientDisconnectedException {
        UpdateIslandStatus func = new UpdateIslandStatus(island, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to update the clouds' status
     * @param clouds the clouds to update
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateCloudsStatus(ArrayList<Cloud> clouds) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        for (Cloud cloud : clouds) {
            this.updateCloudsStatus(cloud);
        }
    }

    /**
     * Method that handles the messages to update one cloud's status
     * @param cloud the cloud to update
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void updateCloudsStatus(Cloud cloud) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        UpdateCloudsStatus func = new UpdateCloudsStatus(cloud, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to update the status of the dashboards
     * @param gamers represents the players
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void updateDashboards(ArrayList<Gamer> gamers, Game game) throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        for (Gamer gamer : gamers) {
            this.updateDashboards(gamer, game);
        }
    }

    /**
     * Method that handles the messages to update the status of one dashboard
     * @param gamer represents the player
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void updateDashboards(Gamer gamer, Game game) throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        UpdateDashboards func = new UpdateDashboards(gamer, game, messageHandler);
        func.handle();
    }

    @Override
    public boolean getAnswer() throws MalformedMessageException, ClientDisconnectedException {
        GetAnswer func = new GetAnswer(messageHandler);
        return func.handle();
    }

    public CharacterCard getChosenCharacterCard(ExpertGame game, ArrayList<CharacterCard> cards) throws ModelErrorException, MalformedMessageException, ClientDisconnectedException {
        GetChosenCharacterCard func = new GetChosenCharacterCard(game, messageHandler, cards);
        CharacterCard result =  func.handle();
        Island island = null;
        ArrayList<PawnColor> colors = new ArrayList<>();
        switch (result){
            case AMBASSADOR -> {
                int islandIndex = this.getMovedStudentLocation();
                island = game.getIslands().get(islandIndex-1);
            }
            case BARD -> {
                colors.addAll(this.getChosenColors());
                //TODO fix a broken model
            }
            case MERCHANT,THIEF -> {
                colors.add(this.getMovedStudentColor());
            }
        }
        game.getDeck().setParameters(colors.stream().map(Student::new).collect(Collectors.toCollection(ArrayList::new)),island);
        return result;
    }

    private ArrayList<PawnColor> getChosenColors() throws MalformedMessageException, ClientDisconnectedException {
        GetChosenColors func = new GetChosenColors(messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the assistant card the current player chooses
     * @param cardsList represents the available cards
     * @return the chosen card
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public AssistantCard getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, ClientDisconnectedException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(cardsList, messageHandler);
        return func.handle();
    }

    /**
     *Method that handles the messages to get the color of the student the current player moves
     * @return the color of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public PawnColor getMovedStudentColor() throws MalformedMessageException, ClientDisconnectedException {
        GetMovedStudentColor func = new GetMovedStudentColor(messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the location of the student the current player moves
     * @return the location of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public int getMovedStudentLocation() throws MalformedMessageException, ClientDisconnectedException {
        GetMovedStudentLocation func = new GetMovedStudentLocation(messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the mother nature's new location
     * @param islands represents the available islands
     * @return the new mother nature location
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public Island getMNLocation(ArrayList<Island> islands) throws MalformedMessageException, ClientDisconnectedException {
        GetMNLocation func = new GetMNLocation(islands, messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the cloud chosen by the current player
     * @param clouds represents the available islands
     * @return the chosen island
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public Cloud getChosenCloud(ArrayList<Cloud> clouds) throws MalformedMessageException, ClientDisconnectedException {
        GetChosenCloud func = new GetChosenCloud(clouds, messageHandler);
        return func.handle();
    }

    /**
     * Method that handles the messages to get the assistant card deck the current player chooses
     * @param cardDeck represents the available decks
     * @return the chosen deck
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public AssistantCardDeckFigures getChosenAssistantCardDeck(ArrayList<AssistantCardDeckFigures> cardDeck) throws MalformedMessageException, ClientDisconnectedException {
        GetChosenAssistantCardDeck func = new GetChosenAssistantCardDeck(cardDeck, messageHandler);
        return func.handle();
    }

    public void sendChosenCharacterCard(CharacterCard card, ExpertGamer currentGamer) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendChosenCharacterCard func = new SendChosenCharacterCard(card, currentGamer, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to send the assistant card deck chosen by the current player
     * @param deck represents the chosen deck
     * @param token represents the token associated to the current player
     * @param gamer represents the currentGamer
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void sendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, Gamer gamer) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendChosenAssistantCardDeck func = new SendChosenAssistantCardDeck(deck, token, messageHandler, gamer);
        func.handle();
    }

    /**
     * Method that handles the messages to send the new phase to the current player
     * @param phase represents the new phase
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void sendNewPhase(Phase phase) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendNewPhase func = new SendNewPhase(phase, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the message to send the username of the winner / the usernames of the winners (in case of a draw)
     * @param names represents the usernames to be sent
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void sendWinner(ArrayList<String> names) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendWinner func = new SendWinner(names, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to send the assistant card chosen by the current player
     * @param card represents the chosen card
     * @param token represents the token associated to the current player
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    @Override
    public void sendChosenAssistantCard(AssistantCard card, Integer token) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendChosenAssistantCard func = new SendChosenAssistantCard(card, token, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to send the color of the current player
     * @param gamer represents the gamer
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    @Override
    public void sendTowerColor(Gamer gamer) throws MalformedMessageException, ClientDisconnectedException, FlowErrorException{
        SendTowerColor func = new SendTowerColor(gamer, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the message to send the context to the players not currently playing
     * A context is sent to the players not currently playing to inform them of the phase in which the current player is
     * @param context represents the context to send
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     */
    public void sendContext(String context) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendContext func = new SendContext(context, messageHandler);
        func.handle();
    }

    /**
     * Method that handles the messages to set a new current player
     * @param player represents the new current player
     */
    @Override
    public void setCurrentPlayer(Player player) {
        this.messageHandler = player.getMessageHandler();
    }

    public void sendActiveUsername(Player player) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendActiveUsername func = new SendActiveUsername(messageHandler, player);
        func.handle();
    }

    @Override
    public void sendUsername(Player player) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendUsernames func = new SendUsernames(player, messageHandler);
        func.handle();
    }

    public void sendMergedIslands(ArrayList<Island> mergedIslands) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendMergedIslands func = new SendMergedIslands(messageHandler, mergedIslands);
        func.handle();
    }

    @Override
    public void sendCoins(int coins) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        SendCoins func = new SendCoins(messageHandler, coins);
        func.handle();
    }

}

