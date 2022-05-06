package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.model.Island;
import org.hamcrest.core.Is;

public class VirtualViewHandler implements VirtualViewInterface{

public class VirtualViewHandler implements View{
    private MessageHandler messageHandler;

    public void setCurrentPlayer(Player player){
        this.messageHandler = player.getMessageHandler();
    }

    public void updateMotherNaturePlace(Island island){
        UpdateMotherNaturePlace func = new UpdateMotherNaturePlace(island,messageHandler);
        func.handle();
    }
}
