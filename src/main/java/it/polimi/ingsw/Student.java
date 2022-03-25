package it.polimi.ingsw.model;

/**
 * @author Sara Mucci
 * Class that implements a student.
 */

public class Student {
    private PawnColor color;

    /**
     * Class constructor.
     * @param color -> student color.
     */
    public Student(PawnColor color) {
        this.color = color;
    }

    /**
     * @return the student color.
     */
    public PawnColor getColor() {
        return this.color;
    }
}
