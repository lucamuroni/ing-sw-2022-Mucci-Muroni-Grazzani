package it.polimi.ingsw.model.pawn;

/**
 * @author Davide Grazzani
 * Class that represents the color associated to a gamer
 */
public enum TowerColor {
    BLACK("black"),
    WHITE("white"),
    GREY("grey");

    private final String color;

    /**
     * Class constructor
     * @param color represents the color associated to a gamer
     */
    private TowerColor(String color){
        this.color = color;
    }

    /**
     * Method that returns the color as a string
     * @return the color as a string
     */
    @Override
    public String toString() {
        return this.color;
    }
}
