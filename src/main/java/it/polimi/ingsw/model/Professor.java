/**
 * @author Sara Mucci
 * class that represents a professor.
 */

package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;
import java.util.Optional;

public class Professor extends Pawn{
    private Optional<Gamer> owner;
    private PawnColor color;

    /**
     * Class constructor.
     * @param color represents the professor's color.
     */
    public Professor (PawnColor color) {
        super(color);
        owner.empty();
    }

    /**
     * @return the professor color.
     */
    public PawnColor getColor() {
        return this.color;
    }

    /**
     * @param owner represents the professor's new owner.
     */
    public void setOwner(Optional<Gamer> owner) {
        this.owner = owner;
    }

    /**
     * @return the professor owner.
     */
    public Optional<Gamer> getOwner() {
        return this.owner;
    }
}
