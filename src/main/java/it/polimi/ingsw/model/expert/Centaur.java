package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

public class Centaur extends CharacterCardInterface {

    public Centaur(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    @Override
    public void handle() {
        this.getGame().getInfluenceCalculator().setTowerInclusion(false);
    }
}
