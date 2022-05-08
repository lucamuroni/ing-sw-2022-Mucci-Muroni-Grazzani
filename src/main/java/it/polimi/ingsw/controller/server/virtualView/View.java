package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

import java.util.ArrayList;

public interface View{
    public void setCurrentPlayer(Player player);
    public void updateIslandStatus(Island island) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException;
    public void updateIslandStatus(ArrayList<Island> islands) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException;
    public void haltOnError();
    public void updateDashboards(ArrayList<Gamer> gamers) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException;
    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException;
    public AssistantCard getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;
    public PawnColor getMovedStudentColor() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;
    //TODO: modificare il return di getMovedStudentLocation da int a isola(riferimento dell'isola va preso da game)  
    public int getMovedStudentLocation() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;
    public Island getMNLocation(ArrayList<Island> islands) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException;
}
