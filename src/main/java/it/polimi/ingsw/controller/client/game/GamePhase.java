package it.polimi.ingsw.controller.client.game;

public interface GamePhase {
    void handle();
    GamePhase next();
}
