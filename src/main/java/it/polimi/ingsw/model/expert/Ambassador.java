package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;

public class Ambassador extends CharacterCardInterface{

    private Island island;

    public Ambassador(CharacterCard card, ExpertGame game, Island island) {
        super(card, game);
        this.island = island;
    }

    @Override
    public void handle() {
        //this.getGame().checkIslandOwner(island);
        //TODO: fare metodo privato mergeIsland -> si può fare metodo preso da MotherNature phase in controller/ fare un metodo da spostare in game
    }

}
