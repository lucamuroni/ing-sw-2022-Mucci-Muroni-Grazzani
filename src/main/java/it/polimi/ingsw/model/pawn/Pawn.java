package it.polimi.ingsw.model.pawn;

/**
 * @author Sara Mucci
 * @author Davide Grazzani
 * Class that implements a pawn (student, professor).
 */

abstract class Pawn {
    private final PawnColor color;

    /**
     * Class constructor.
     * @param color represent the color of pawn
     */
    public Pawn(PawnColor color) {
        this.color = color;
    }

    /**
     * Method used to get the color of the pawn
     * @return the color of the pawn
     */
    public PawnColor getColor() {
        return this.color;
    }
}
