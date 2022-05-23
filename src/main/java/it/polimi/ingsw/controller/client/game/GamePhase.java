package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.networking.Phase;

public interface GamePhase {
    public void handle();
    public GamePhase next();
    public Phase getNamePhase();
}
