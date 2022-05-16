package it.polimi.ingsw.view.cli;

public enum AnsiColor {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    RESET("\u001B[0m");

    private final String ansiText;
    private AnsiColor(String ansiText){
        this.ansiText = ansiText;
    }

    @Override
    public String toString() {
        return this.ansiText;
    }
}
