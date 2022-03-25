package it.polimi.ingsw.model;

import debug.Gamer;
import java.util.Optional;

/**
 * @author Sara Mucci
 * class that represents a professor.
 */

public class Professor {
    private Optional<Gamer> owner;
    private PawnColor color;

    /**
     * Class constructor.
     * @param color -> the professor's color.
     */
    public Professor (PawnColor color) {
        this.color = color;
    }

    /**
     * @return the professor color.
     */
    public PawnColor getColor() {
        return this.color;
    }

    /**
     * @param owner -> the professor's new owner.
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
