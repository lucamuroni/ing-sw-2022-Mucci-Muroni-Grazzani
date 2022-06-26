package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;

public class Postman extends CharacterCardInterface {

    public Postman(CharacterCard card, ExpertGame game) {
        super(card, game);
    }

    @Override
    public void handle() {
        this.getGame().setMoreSteps();
    }
}
