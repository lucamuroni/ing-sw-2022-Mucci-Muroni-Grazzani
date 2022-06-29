package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

/**
 * Card Knight from the deck of CharacterCards
 * @author Davide Grazzani
 */
public class Knight extends CharacterCardInterface{
    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     */
    public Knight(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    /**
     * Method that handles the effect of the card
     */
    @Override
    public void handle() {
        ExpertGame game = this.getGame();
        game.getInfluenceCalculator().setMoreInfluence(game.getCurrentPlayer());
    }
}
