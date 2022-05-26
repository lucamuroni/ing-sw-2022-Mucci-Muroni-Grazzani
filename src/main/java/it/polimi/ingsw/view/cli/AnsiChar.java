package it.polimi.ingsw.view.cli;

public enum AnsiChar {
    TOWER("♖"),
    PAWN("♟"),
    MISSING_PAWN("♙"),
    MOTHER_NATURE("\uD83D\uDCA9"),
    WIZARD("⚝");

// 💩
    private final String ansiText;
    private AnsiChar(String ansiText){
        this.ansiText = ansiText;
    }

    @Override
    public String toString() {
        return this.ansiText;
    }
}
