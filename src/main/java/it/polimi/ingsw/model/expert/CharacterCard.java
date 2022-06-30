package it.polimi.ingsw.model.expert;

/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * CLass enum that represents the cards for the expert mode
 */
public enum CharacterCard {
    THIEF("Thief", 3),
    VILLAGER("Villager", 2),
    AMBASSADOR("Ambassador", 3),
    POSTMAN("Postman", 1),
    CENTAUR("Centaur", 3),
    KNIGHT("Knight", 2),
    MERCHANT("Merchant", 3),
    BARD("Bard", 1);

    private final String name;
    private final int moneyCost;

    /**
     * Constructor of the class
     * @param name is the name of the card
     * @param moneyCost is the cost of the card
     */
    CharacterCard(String name, int moneyCost){
        this.name = name;
        this.moneyCost = moneyCost;
    }

    /**
     * Method that returns the name of the card
     * @return the name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * Method that return the cost of the card
     * @return the cost of the card
     */
    public int getMoneyCost() {
        return moneyCost;
    }
}
