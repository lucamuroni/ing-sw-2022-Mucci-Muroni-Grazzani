package it.polimi.ingsw.controller.server.virtualView;

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

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * @author Sara Mucci
 * Interface with all the messsages method executed by the server
 */
public interface View{
    /**
     * Method that handles the messages to update the status of an island
     * @param island represents the island to update
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void updateIslandStatus(Island island) throws MalformedMessageException, FlowErrorException, ClientDisconnectedException;

    /**
     * Method that handles the messages to update the status of a cloud
     * @param cloud the cloud to update
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void updateCloudsStatus(Cloud cloud) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to update the status of a dashboards
     * @param gamer represents the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void updateDashboards(Gamer gamer, Game game) throws MalformedMessageException, ClientDisconnectedException, FlowErrorException;

    /**
     * Method that handles the messages to update the location of motherNature
     * @param island represents the location of motherNature
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void updateMotherNaturePlace(Island island) throws MalformedMessageException, ClientDisconnectedException, FlowErrorException;

    /**
     * Method that handles the messages to get the answer of the current player
     * @return the answer of the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    boolean getAnswer() throws MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the characterCard chosen by the current player
     * @param game is the current game
     * @param cards is the arrayList of possible cards to chose from
     * @return the card chosen by the current player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws ModelErrorException when there isn't an object with the same info received from the client
     * @throws MalformedMessageException when a received message isn't correct
     */
    CharacterCard getChosenCharacterCard(ExpertGame game, ArrayList<CharacterCard> cards) throws ModelErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the assistantCardDeck the current player chooses
     * @param cardDeck represents the available decks
     * @return the chosen deck
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    AssistantCardDeckFigures getChosenAssistantCardDeck(ArrayList<AssistantCardDeckFigures> cardDeck) throws MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the assistantCard the current player chooses
     * @param cardsList represents the available cards
     * @return the chosen card
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    AssistantCard getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the color of the student the current player moves
     * @return the color of the moved student
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    PawnColor getMovedStudentColor() throws MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the location of the student the current player moves
     * @return the location of the moved student
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    int getMovedStudentLocation() throws MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the new location of motherNature
     * @param islands represents the available islands
     * @return the new  location of motherNature
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    Island getMNLocation(ArrayList<Island> islands) throws MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to get the cloud chosen by the current player
     * @param clouds represents the available islands
     * @return the chosen island
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    Cloud getChosenCloud(ArrayList<Cloud> clouds) throws MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the characterCard chosen by the current player
     * @param card is the chosen characterCard
     * @param currentGamer is the current player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendChosenCharacterCard(CharacterCard card, ExpertGamer currentGamer) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException ;

    /**
     * Method that handles the messages to send the assistantCardDeck chosen by the current player
     * @param deck represents the chosen deck
     * @param token represents the token associated with the current player
     * @param gamer represents the currentGamer
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, Gamer gamer) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the assistantCard chosen by the current player
     * @param card represents the chosen card
     * @param token represents the token associated with the current player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendChosenAssistantCard(AssistantCard card, Integer token) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the color of the tower associated with the current player
     * @param gamer represents the current player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendTowerColor(Gamer gamer) throws MalformedMessageException, ClientDisconnectedException, FlowErrorException;

    /**
     * Method that handles the messages to send the new phase to the current player
     * @param phase represents the new phase
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendNewPhase(Phase phase) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the username of the winner / the usernames of the winners (in case of draw)
     * @param names represents the usernames to be sent
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendWinner(ArrayList<String> names) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the context to the players not currently playing
     * A context is sent to the players not currently playing to inform them of the phase in which the current player is
     * or to update their view
     * @param context represents the context to send
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendContext(String context) throws FlowErrorException, MalformedMessageException,  ClientDisconnectedException;

    /**
     * Method that handles the messages to send the username of the current player
     * @param player is the current player whose username will be sent
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendActiveUsername(Player player) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the username of a player to all others players
     * @param player is the player whose username will be sent
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendUsername(Player player) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the islands that have been merged
     * @param mergedIslands is the arrayList containing the merged islands
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendMergedIslands(ArrayList<Island> mergedIslands) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send to a player his coins
     * @param coins is the number of coins owned by the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendCoins(int coins) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method that handles the messages to send the all characterCards at the start of the game
     * @param card is the characterCard to be sent
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    void sendCharacterCard(CharacterCard card) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException;

    /**
     * Method used to set a new current player
     * @param player represents the new current player
     */
    void setCurrentPlayer(Player player);
}