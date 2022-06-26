package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

public class Knight extends CharacterCardInterface{
    public Knight(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    @Override
    public void handle() {
        ExpertGame game = this.getGame();
        game.getInfluenceCalculator().setMoreInfluence(game.getCurrentPlayer());
    }
}
