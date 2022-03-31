package it.polimi.ingsw.model;


/**
 * @author Davide Grazzani
 * Class designated for the rappresentation of Mother Nature
 */
public class MotherNature {
    private Island place;

    public MotherNature(Island initialPlace){
        this.place = initialPlace;
    }

    /**
     * @param newPosition Island on witch Mother Nature will be set on
     */
    public void setPlace (Island newPosition){
        this.place = newPosition;
    }

    /**
     * @return the current position of Mother Nature
     */
    public Island getPlace () {
        return this.place;
    }
}
