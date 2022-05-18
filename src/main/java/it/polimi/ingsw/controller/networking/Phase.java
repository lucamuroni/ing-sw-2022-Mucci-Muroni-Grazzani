package it.polimi.ingsw.controller.networking;

public enum Phase {
    PLANNINGPHASE("Planning phase"),
    ACTION_PHASE_1("Action phase 1"),
    ACTION_PHASE_3("Action phase 3");

    private String name;

    private Phase(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
