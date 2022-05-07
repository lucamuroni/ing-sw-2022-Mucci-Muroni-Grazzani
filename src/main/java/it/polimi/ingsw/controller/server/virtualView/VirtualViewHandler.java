package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.model.Island;
import org.hamcrest.core.Is;

import java.util.ArrayList;

public class VirtualViewHandler implements View{
    private MessageHandler messageHandler;

    public void setCurrentPlayer(Player player){
        this.messageHandler = player.getMessageHandler();
    }

    public void updateMotherNaturePlace(Island island){
        UpdateMotherNaturePlace func = new UpdateMotherNaturePlace(island,messageHandler);
        func.handle();
    }

    @Override
    public void updateIslandStatus(ArrayList<Island> islands) {
        for(Island island : islands){
            updateIslandStatus(island);
        }
    }

    @Override
    public void updateIslandStatus(Island island) {

    }
}
