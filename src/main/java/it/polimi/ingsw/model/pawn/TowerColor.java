package it.polimi.ingsw.model.pawn;

/**
 * @author Davide Grazzani
 * Class that represents the color associated to a gamer
 */
public enum TowerColor {
    BLACK("black", 'B'),
    WHITE("white",'W'),
    GREY("grey",'G');

    private final String color;
    private final char acronym;

    /**
     * Class constructor
     * @param color represents the color associated to a gamer
     */
    private TowerColor(String color,char acronym){
        this.color = color;
        this.acronym = acronym;
    }

    /**
     * Method that returns the color as a string
     * @return the color as a string
     */
    @Override
    public String toString() {
        return this.color;
    }

    public char getAcronym(){
        return this.acronym;
    }
}
