package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * Card Merchant from the deck of CharacterCards
 */
public class Merchant extends CharacterCardInterface {
    private final PawnColor color;

    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     */
    public Merchant(CharacterCard card, ExpertGame game,PawnColor color) {
        super(card, game);
        this.color = color;
    }

    /**
     * Method that handles the effect of the card
     */
    @Override
    public void handle() {
        ArrayList<PawnColor> color = new ArrayList<>();
        color.add(this.color);
        this.getGame().getInfluenceCalculator().addColorExclusion(color);
    }
}
