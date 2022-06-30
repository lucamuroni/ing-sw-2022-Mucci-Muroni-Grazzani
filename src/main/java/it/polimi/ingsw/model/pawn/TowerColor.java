package it.polimi.ingsw.model.pawn;

/**
 * @author Davide Grazzani
 * Class that represents the possible colors of a tower associated with a gamer
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
    TowerColor(String color,char acronym){
        this.color = color;
        this.acronym = acronym;
    }

    /**
     * Method that returns the name of the color
     * @return the name of the color
     */
    @Override
    public String toString() {
        return this.color;
    }

    /**
     * Method used to get the acronym of a color
     * @return the acronym of a color
     */
    public char getAcronym(){
        return this.acronym;
    }
}
