package it.polimi.ingsw.controller.server;

/**
 * @author Davide Grazzani
 * Enum used to distinguish between normal and exper game mode
 */
public enum GameType {
    NORMAL("normal"),
    EXPERT("expert");

    private String name;

    /**
     * Enum builder
     * @param name is the name of a game mode
     */
    private GameType(String name){
        this.name = name;
    }

    /**
     * Getter method
     * @return the name associated the mode you chose
     */
    public String getName(){
        return this.name;
    }
}