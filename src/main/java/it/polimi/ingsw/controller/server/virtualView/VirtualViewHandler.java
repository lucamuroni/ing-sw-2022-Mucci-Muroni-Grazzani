package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.hamcrest.core.Is;

import java.util.ArrayList;

public class VirtualViewHandler implements View {

    private MessageHandler messageHandler;

    @Override
    public void setCurrentPlayer(Player player) {
        this.messageHandler = player.getMessageHandler();
    }

    @Override
    public void updateMotherNaturePlace(Island island) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        UpdateMotherNaturePlace func = new UpdateMotherNaturePlace(island, messageHandler);
        func.handle();
    }

    @Override
    public void updateIslandStatus(ArrayList<Island> islands) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException {
        for(Island island : islands){
            updateIslandStatus(island);
        }
    }

    @Override
    public void updateIslandStatus(Island island) throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException {
        UpdateIslandStatus func = new UpdateIslandStatus(island, messageHandler);
        func.handle();
    }

    @Override
    public void haltOnError() {

    }

    @Override
    public void updateDashboards(ArrayList<Gamer> gamers) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        UpdateDashboards func = new UpdateDashboards(gamers, messageHandler);
        func.handle();
    }

    @Override
    public AssistantCard getChosenAssistantCard(ArrayList<AssistantCard> cardsList) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenAssistantCard func = new GetChosenAssistantCard(cardsList, messageHandler);
        AssistantCard result = func.handle();
        return result;
    }

    @Override
    public PawnColor getMovedStudentColor() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMovedStudentColor func = new GetMovedStudentColor(messageHandler);
        PawnColor result = func.handle();
        return result;
    }

    @Override
    public int getMovedStudentLocation() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMovedStudentLocation func = new GetMovedStudentLocation(messageHandler);
        //Controllare con Grazza: l'idea è che la funzione ritornerà un int che potrà essere 0, e allora indicherà che lo studente
        //è stato mosso nella hall, o un numero che va da 1 a 12, e allora indicherà una delle 12 isole
        int result = func.handle();
        return result;
    }

    @Override
    public Island getMNLocation(ArrayList<Island> islands) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetMNLocation func = new GetMNLocation(islands, messageHandler);
        Island result = func.handle();
        return result;
    }

    //TODO: Controllare con Grazza: serve una classe TowerColor, di tipo enum, per passare il colore al client
    @Override
    public void sendTowerColor(TowerColor color) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException{
        SendTowerColor func = new SendTowerColor(color, messageHandler);
        func.handle();
    }
    @Override
    public Cloud getChosenCloud(ArrayList<Cloud> clouds) throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        GetChosenCloud func = new GetChosenCloud(clouds, messageHandler);  //TODO: rivedere errore
        Cloud result = func.handle();
        return result;
    }
}

