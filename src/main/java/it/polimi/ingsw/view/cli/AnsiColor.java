package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;

public enum AnsiColor {
    RED("\u001B[31m",PawnColor.RED),
    GREEN("\u001B[32m",PawnColor.GREEN),
    YELLOW("\u001B[33m",PawnColor.YELLOW),
    BLUE("\u001B[34m",PawnColor.BLUE),
    PURPLE("\u001B[35m",PawnColor.PINK),
    RESET("\u001B[0m",null);

    private final String ansiText;
    private final PawnColor color;
    private AnsiColor(String ansiText, PawnColor associatedColor){
        this.ansiText = ansiText;
        this.color = associatedColor;
    }

    @Override
    public String toString() {
        return this.ansiText;
    }

    public PawnColor getColor(){
        return this.color;
    }
}
