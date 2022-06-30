package it.polimi.ingsw.model.pawn;

/**
 * @author Sara Mucci
 * @author Davide Grazzani
 * Class that represents the possible colors of a pawn (student, professor)
 */
public enum PawnColor {
    BLUE("blue"),
    PINK("pink"),
    YELLOW("yellow"),
    RED("red"),
    GREEN("green");

    private final String nameColor;
    PawnColor(String nameColor){
        this.nameColor = nameColor;
    }

    /**
     * Method used to get the name of the color
     * @return the name of the color
     */
    @Override
    public String toString() {
        return nameColor;
    }
}
