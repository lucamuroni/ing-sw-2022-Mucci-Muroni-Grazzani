package it.polimi.ingsw.model;

/**
 * @author Sara Mucci
 * Class that implements a student.
 */

public class Student extends Pawn{
    private PawnColor color;

    /**
     * Class constructor.
     * @param color represents student color.
     */
    public Student(PawnColor color) {
        super(color);
    }

    /**
     * @return the student color.
     */
    public PawnColor getColor() {
        return this.color;
    }
}
