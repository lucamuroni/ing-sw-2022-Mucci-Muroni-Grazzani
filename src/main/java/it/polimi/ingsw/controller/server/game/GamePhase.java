package it.polimi.ingsw.controller.server.game;

public interface GamePhase {
    public void handle(Game game);
    public GamePhase next();
}
