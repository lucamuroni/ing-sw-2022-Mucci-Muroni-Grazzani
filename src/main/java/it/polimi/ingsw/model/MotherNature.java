package it.polimi.ingsw.model;

/**
 * @author Davide Grazzani
 * Class designated for the rappresentation of Mother Nature
 */
public class MotherNature {
    private Island place;

    /**
     * Class constructor
     * @param initialPlace represents the first island where Mother Nature is set at the start of the game
     */
    public MotherNature(Island initialPlace){
        this.place = initialPlace;
    }

    /**
     * Setter method
     * @param newPosition represents the island on witch Mother Nature will be moved by the current player
     */
    public void setPlace (Island newPosition){
        this.place = newPosition;
    }

    /**
     * Getter method
     * @return the current position of Mother Nature
     */
    public Island getPlace () {
        return this.place;
    }
}
