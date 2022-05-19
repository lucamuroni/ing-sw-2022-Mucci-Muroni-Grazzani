package it.polimi.ingsw.controller.networking;

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
