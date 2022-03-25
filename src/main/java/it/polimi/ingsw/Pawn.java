package it.polimi.ingsw.model;

import it.polimi.ingsw.model.PawnColor;

/**
 * @author Sara Mucci
 * Class that implements a pawn (student-professor).
 */

public class Pawn {
    private PawnColor color;

    /**
     * Class constructor.
     * @param color -> pawn color.
     */
    private Pawn(PawnColor color) {
        this.color = color;
    }

    /**
     * @return pawn color
     */
    public PawnColor getColor() {
        return this.color;
    }
}
