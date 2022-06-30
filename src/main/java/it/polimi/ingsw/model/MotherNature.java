package it.polimi.ingsw.model;

/**
 * @author Davide Grazzani
 * Class designated for the rappresentation of motherNature
 */
public class MotherNature {
    private Island place;

    /**
     * Class constructor
     * @param initialPlace represents the first island where motherNature is set at the start of the game
     */
    public MotherNature(Island initialPlace){
        this.place = initialPlace;
    }

    /**
     * Method used to set the new island where motherNature is on
     * @param newPosition represents the island on which motherNature is moved by the current player
     */
    public void setPlace (Island newPosition){
        this.place = newPosition;
    }

    /**
     * Method used to get the island where motherNature is on
     * @return the current position of motherNature
     */
    public Island getPlace () {
        return this.place;
    }
}
