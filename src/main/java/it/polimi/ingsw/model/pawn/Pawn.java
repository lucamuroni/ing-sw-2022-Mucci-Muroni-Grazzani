package it.polimi.ingsw.model.pawn;

import it.polimi.ingsw.model.pawn.PawnColor;

/**
 * @author Sara Mucci
 * @author Davide Grazzani
 * Class that implements a pawn (student-professor).
 */

abstract class Pawn {
    private final PawnColor color;

    /**
     * Class constructor.
     * @param color represent the pawn color.
     */
    public Pawn(PawnColor color) {
        this.color = color;
    }

    /**
     *
     * @return pawn color
     */
    public PawnColor getColor() {
        return this.color;
    }
}