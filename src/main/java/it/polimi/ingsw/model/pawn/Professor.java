package it.polimi.ingsw.model.pawn;

import it.polimi.ingsw.model.gamer.Gamer;
import java.util.Optional;

/**
 * @author Sara Mucci
 * class that represents a professor.
 */
public class Professor extends Pawn {
    private Optional<Gamer> owner;

    /**
     * Class constructor.
     * @param color represents the color of professor
     */
    public Professor (PawnColor color) {
        super(color);
        this.owner = Optional.empty();
    }

    /**
     * Method used to set the owner of a professor
     * @param owner represents the new owner of the professor
     */
    public void setOwner(Gamer owner) {
        this.owner = Optional.of(owner);
    }

    /**
     * Gmethod used to get the owner of a professor
     * @return the owner of a professor
     */
    public Optional<Gamer> getOwner() {
        return this.owner;
    }
}
