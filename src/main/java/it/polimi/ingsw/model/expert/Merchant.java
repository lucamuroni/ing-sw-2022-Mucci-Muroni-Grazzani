package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

public class Merchant extends CharacterCardInterface {

    private PawnColor color;

    public Merchant(CharacterCard card, ExpertGame game,PawnColor color) {
        super(card, game);
        this.color = color;
    }

    @Override
    public void handle() {
        ArrayList<PawnColor> color = new ArrayList<>();
        color.add(this.color);
        this.getGame().getInfluenceCalculator().addColorExclusion(color);
    }
}
