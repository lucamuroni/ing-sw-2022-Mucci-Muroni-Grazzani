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
     * @param color represents the professor's color.
     */
    public Professor (PawnColor color) {
        super(color);
        this.owner = Optional.empty();
    }

    /**
     * Setter method
     * @param owner represents the professor's new owner.
     */
    public void setOwner(Gamer owner) {
        this.owner = Optional.of(owner);
    }

    /**
     * Getter method
     * @return the professor owner.
     */
    public Optional<Gamer> getOwner() {
        return this.owner;
    }
}
