package it.polimi.ingsw.view.cli;

public enum AnsiChar {
    TOWER("♖"),
    PAWN("♙"),
    MOTHER_NATURE("♟");


    private final String ansiText;
    private AnsiChar(String ansiText){
        this.ansiText = ansiText;
    }

    @Override
    public String toString() {
        return this.ansiText;
    }
}
