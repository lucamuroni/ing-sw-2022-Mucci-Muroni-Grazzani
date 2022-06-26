package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

public class Villager extends CharacterCardInterface {

    public Villager(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    @Override
    public void handle() {
        this.getGame().setEqualProfessorFlag();
    }
}
