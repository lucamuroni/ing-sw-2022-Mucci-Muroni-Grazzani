package it.polimi.ingsw.controller.networking;

/**
 * @author Davide Grazzani
 * Class that represents the phases of a game
 */
public enum Phase {
    DECK_PHASE("Deck phase"),
    PLANNING_PHASE("Planning phase"),
    CHARACTER_PHASE("Character phase"),
    ACTION_PHASE_1("Action phase 1"),
    MOTHER_NATURE_PHASE("Mother nature phase"),
    ACTION_PHASE_3("Action phase 3"),
    END_GAME_PHASE("End game phase");

    private final String name;

    /**
     * Class constructor
     * @param name represents the name of the phase
     */
    Phase(String name){
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