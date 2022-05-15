package it.polimi.ingsw.controller.client.game;

public interface GamePhase {
    public void handle();
    public GamePhase next();
    public PhaseName getNamePhase();
}
