package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;

public abstract class CharacterCardInterface {
    private CharacterCard card;
    private ExpertGame game;

    public CharacterCardInterface(CharacterCard card,ExpertGame game){
        this.card = card;
        this.game = game;
    }
    public abstract void handle();

    public void payCardCost(){
        this.game.getCurrentPlayer().getDashboard().setCoins(-card.getMoneyCost());
        this.game.setCoinBank(card.getMoneyCost());
    }

    public ExpertGame getGame(){
        return this.game;
    }
}
