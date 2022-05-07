package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

public interface View {
    public void setCurrentPlayer(Player player);
    public void updateMotherNaturePlace(Island island);
    public void updateIslandStatus(Island island);
    public void updateIslandStatus(ArrayList<Island> islands);
    public void haltOnError();
    public void updateDashboards(ArrayList<Gamer> gamers);
}
