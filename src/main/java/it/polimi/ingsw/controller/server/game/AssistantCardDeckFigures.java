package it.polimi.ingsw.controller.server.game;

public enum AssistantCardDeckFigures {
    WITCH("witch"),
    DRUID("druid"),
    KING("king"),
    MONK("monk");

    private final String name;

    private AssistantCardDeckFigures(String name){
        this.name = name;
    }
}
