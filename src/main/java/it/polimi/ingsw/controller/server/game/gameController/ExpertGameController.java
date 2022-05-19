package it.polimi.ingsw.controller.server.game.gameController;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.Server;

import java.util.ArrayList;

public class ExpertGameController extends GameController{

    public ExpertGameController(Server server, ArrayList<Player> players){
        super(server,players);
    }
}
