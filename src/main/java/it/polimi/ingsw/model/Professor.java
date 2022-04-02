/**
 * @author Sara Mucci
 * class that represents a professor.
 */

package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;
import java.util.Optional;

public class Professor extends Pawn{
    private Optional<Gamer> owner;

    /**
     * Class constructor.
     * @param color represents the professor's color.
     */
    public Professor (PawnColor color) {
        super(color);
        this.owner = Optional.empty();
    }

    /**
     * @param owner represents the professor's new owner.
     */
    public void setOwner(Gamer owner) {
        this.owner = Optional.of(owner);
    }

    /**
     * @return the professor owner.
     */
    public Optional<Gamer> getOwner() {
        return this.owner;
    }
}
