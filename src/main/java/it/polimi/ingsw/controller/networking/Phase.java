package it.polimi.ingsw.controller.networking;

/**
 * @author Davide Grazzani
 * Class that represents the phases of a game
 */
public enum Phase {
    PLANNINGPHASE("Planning phase"),
    ACTION_PHASE_1("Action phase 1"),
    ACTION_PHASE_3("Action phase 3"),
    END_GAME_PHASE("End game phase");

    private String name;

    /**
     * Class constructor
     * @param name represents the name of the phase
     */
    private Phase(String name){
        this.name = name;
    }

    /**
     * Method that returns the name associated to a phase
     * @return the name as a string
     */
    @Override
    public String toString() {
        return this.name;
    }
}
