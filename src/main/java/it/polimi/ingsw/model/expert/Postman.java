package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

/**
 * Card Postman from the deck of CharacterCards
 * @author Davide Grazzani
 */
public class Postman extends CharacterCardInterface {
    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     */
    public Postman(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    /**
     * Method that handles the effect of the card
     */
    @Override
    public void handle() {
        this.getGame().setMoreSteps();
    }
}
