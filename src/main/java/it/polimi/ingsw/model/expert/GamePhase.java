package it.polimi.ingsw.model.expert;

public enum GamePhase {
    ALL(0),
    PROFESSORSINFLUENCE(1),
    ISLANDINFLUENCE(2),
    MOTHERNATURE(3);

    private final int value;

    private GamePhase(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }
}
