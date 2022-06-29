package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

/**
 * Card Ambassador from the deck of CharacterCards
 * @author Davide Grazzani
 */
public class Ambassador extends CharacterCardInterface{
    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     */
    public Ambassador(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    /**
     * Method handle is empty because we use ConquerIslandPhase to handle the effect
     */
    @Override
    public void handle() {}

}
