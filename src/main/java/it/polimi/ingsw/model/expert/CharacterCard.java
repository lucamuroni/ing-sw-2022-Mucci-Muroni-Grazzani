package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public enum CharacterCard {
    THIEF("Thief", 3, GamePhase.ALL),
    VILLAGER("Villager", 2, GamePhase.PROFESSORSINFLUENCE),
    AMBASSADOR("Ambassador", 3, GamePhase.MOTHERNATURE),
    POSTMAN("Postman", 1, GamePhase.MOTHERNATURE),
    CENTAUR("Centaur", 3, GamePhase.ISLANDINFLUENCE),
    KNIGHT("Knight", 2, GamePhase.ISLANDINFLUENCE),
    MERCHANT("Merchant", 3, GamePhase.ISLANDINFLUENCE),
    BARD("Bard", 1, GamePhase.ALL);

    private final String name;
    private final int moneyCost;
    private final GamePhase phase;

    private CharacterCard(String name, int moneyCost, GamePhase phase){
        this.phase = phase;
        this.name = name;
        this.moneyCost = moneyCost;
    }

    public String getName() {
        return name;
    }

    public int getMoneyCost() {
        return moneyCost;
    }
}
