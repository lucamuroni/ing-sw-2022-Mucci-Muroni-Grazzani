package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;

/**
 * @author Davide Grazzani
 * Class that represents the colors to use in the cli
 */
public enum AnsiColor {
    RED("\u001B[31m",PawnColor.RED),
    GREEN("\u001B[32m",PawnColor.GREEN),
    YELLOW("\u001B[33m",PawnColor.YELLOW),
    BLUE("\u001B[34m",PawnColor.BLUE),
    PURPLE("\u001B[35m",PawnColor.PINK),
    RESET("\u001B[0m",null);

    private final String ansiText;
    private final PawnColor color;

    /**
     * Class constructor
     * @param ansiText represents the ansiText to set. An ansiText is
     * @param associatedColor represents the color associated to the pawn
     */
    AnsiColor(String ansiText, PawnColor associatedColor){
        this.ansiText = ansiText;
        this.color = associatedColor;
    }

    /**
     * Override of toString method
     * @return the ansiText as a String
     */
    @Override
    public String toString() {
        return this.ansiText;
    }

    /**
     * Getter method
     * @return the color of the specified pawn
     */
    public PawnColor getColor(){
        return this.color;
    }
}