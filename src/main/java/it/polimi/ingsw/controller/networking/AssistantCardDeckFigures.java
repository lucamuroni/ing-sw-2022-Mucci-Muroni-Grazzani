package it.polimi.ingsw.controller.networking;

/**
 * @author DavideGrazzani
 * Class that represents the figures that an assistant card deck can have
 */
public enum AssistantCardDeckFigures {
    WITCH("witch"),
    DRUID("druid"),
    KING("king"),
    MONK("monk");

    private final String name;

    /**
     * Class constructor
     * @param name represents the name of the figure
     */
    private AssistantCardDeckFigures(String name){
        this.name = name;
    }
}
