package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;

/**
 * @author Davide Grazzani
 * Card Villager from the deck of CharacterCards
 */
public class Villager extends CharacterCardInterface {
    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     */
    public Villager(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    /**
     * Method that handles the effect of the card
     */
    @Override
    public void handle() {
        this.getGame().setEqualProfessorFlag();
        for (PawnColor color : PawnColor.values()) {
            try {
                this.getGame().changeProfessorOwner(color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
