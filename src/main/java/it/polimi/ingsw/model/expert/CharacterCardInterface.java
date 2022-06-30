package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

/**
 * @author Davide Grazzani
 * Interface used by all the class that represents the CharacterCards
 */
public abstract class CharacterCardInterface {
    private final CharacterCard card;
    private final ExpertGame game;

    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     */
    public CharacterCardInterface(CharacterCard card,ExpertGame game){
        this.card = card;
        this.game = game;
    }

    /**
     * Method used by the inherited classes to manage their effect
     */
    public abstract void handle();

    /**
     * Method used to pay the cost to play the card
     * @param usage is the number of times the card has been played
     */
    public void payCardCost(int usage){
        int cost = card.getMoneyCost()+usage;
        this.game.getCurrentPlayer().getDashboard().setCoins(-cost);
        this.game.setCoinBank(cost-1);
    }

    /**
     * Method used to get the game associated with the card
     * @return the current game
     */
    public ExpertGame getGame(){
        return this.game;
    }
}
