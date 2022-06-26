package it.polimi.ingsw.model.pawn;

/**
 * @author Sara Mucci
 * @author Davide Grazzani
 * Class that represents the color that a pawn (student-professor) can have.
 */
public enum PawnColor {
    BLUE("blue"),
    PINK("pink"),
    YELLOW("yellow"),
    RED("red"),
    GREEN("green");

    private String nameColor;
    private PawnColor(String nameColor){
        this.nameColor = nameColor;
    }


    @Override
    public String toString() {
        return nameColor;
    }
}
