package it.polimi.ingsw.model.expert;

/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * CLass enum that represents the cards for the expert mode
 */
public enum CharacterCard {
    THIEF("Thief", 3,"Choose a student color; each player (including you) must return 3 students of that color present in\nhis hall to the bag. Whoever has fewer than 3 students of that color, will put back all the ones he has."),
    VILLAGER("Villager", 2,"During this turn, take control of the professors even if you have the same number of students in your\nroom as the player currently controlling them"),
    AMBASSADOR("Ambassador", 3,"Choose an island and calculate the majority as if mother nature had finished her movement there.\nIn this turn, mother nature will move as usual and, on the island where her movement ends, the majority"),
    POSTMAN("Postman", 1,"You can move Mother Nature up to 2 more islands than indicated on the assistant card you played"),
    CENTAUR("Centaur", 3,"When counting the influence on an island (or a group of islands), the towers present are not counted"),
    KNIGHT("Knight", 2,"This turn, you have 2 additional influence points when calculating influence"),
    MERCHANT("Merchant", 3,"Choose a student color; this turn, that color provides no influence when calculating influence"),
    BARD("Bard", 1,"You can exchange up to 2 students in your waitingRoom and hall with each other");

    private final String name;
    private final int moneyCost;
    private final String effect;

    /**
     * Constructor of the class
     * @param name is the name of the card
     * @param moneyCost is the cost of the card
     */
    CharacterCard(String name, int moneyCost,String effect){
        this.name = name;
        this.moneyCost = moneyCost;
        this.effect = effect;
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

    public String getEffect(){
        return this.effect;
    }
}
