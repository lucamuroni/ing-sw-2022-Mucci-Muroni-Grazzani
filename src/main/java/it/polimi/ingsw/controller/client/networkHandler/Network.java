package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.game.DashBoard;

import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Interface with all the messages methods executed by the client
 */
public interface Network {
    public Player getCurrentPlayer();

    public void getConnection();

    public void sendInfo(Player player, Game game) throws MalformedMessageException;

    public void getLobbyStatus();

    public Phase getPhase(Phase phase);

    public ArrayList<AssistantCard> getPossibleCards();

    public void sendCard(AssistantCard card);

    public void sendColor(PawnColor color);

    public void sendLocation(int location);

    public ArrayList<Island> getPossibleIslands();

    public void sendIsland(Island island);

    public Player getNewOwner();

    public ArrayList<Cloud> getPossibleClouds();

    public void sendCloud(Cloud cloud);

    public ArrayList<Player> getWinner();

    public Cloud getCloudStatus();

    public DashBoard getDashboard();

    public Island getIslandStatus();

    public Island getMotherNaturePlace();
}
