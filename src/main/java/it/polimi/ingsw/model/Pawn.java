package it.polimi.ingsw.model;

import it.polimi.ingsw.model.PawnColor;

/**
 * @author Sara Mucci
 * @author Davide Grazzani
 * Class that implements a pawn (student-professor).
 */

public abstract class Pawn {
    private final PawnColor color;

    /**
     * Class constructor.
     * @param color represent the pawn color.
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
