package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;

public class Villager extends CharacterCardInterface {

    public Villager(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    @Override
    public void handle() {
        this.getGame().setEqualProfessorFlag();
        for (PawnColor color : PawnColor.values()) {
            try {
                this.getGame().changeProfessorOwner(color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
