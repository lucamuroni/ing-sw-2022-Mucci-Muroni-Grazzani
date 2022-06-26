package it.polimi.ingsw.view.cli;


/**
 * @author Davide Grazzani
 * Class that represents the possible pawns to show in the cli
 */
public enum AnsiChar {
    TOWER("♖"),
    PAWN("♟"),
    MISSING_PAWN("♙"),
    MOTHER_NATURE("\uD83D\uDCA9"),
    MISSING_WIZARD("♕"),
    WIZARD("♛"),
    MONEY("\uD83D\uDCB0");
// 💩
    private final String ansiText;

    /**
     * Class constructor
     * @param ansiText represents the ansiText to set. An ansiText is
     */
    private AnsiChar(String ansiText){
        this.ansiText = ansiText;
    }


    /**
     * Override of the toString method
     * @return the ansiText as a string
     */
    @Override
    public String toString() {
        return this.ansiText;
    }
}
