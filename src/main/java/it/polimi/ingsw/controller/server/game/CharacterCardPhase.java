package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class CharacterCardPhase implements GamePhase{
    private final ExpertGame game;
    private final GameController controller;
    private final View view;
    private final GamePhase nextPhase;
    //TODO controllare i messaggi
    // TODO chiamata a model da riorganizzare
    //TODO metodo per inviare le monete
    public CharacterCardPhase(ExpertGame game, GameController controller, GamePhase nextPhase) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        this.nextPhase = nextPhase;
    }

    public void handle() {
        Player player;
        try {
            player = this.controller.getPlayer(this.game.getCurrentPlayer());
        } catch (ModelErrorException e) {
            throw new RuntimeException(e);
        }
        this.view.setCurrentPlayer(player);
        try {
            try{
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.CHARACTER_PHASE);
            }catch (MalformedMessageException | FlowErrorException e){
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.CHARACTER_PHASE);
            }
        }catch (MalformedMessageException | FlowErrorException |
                ClientDisconnectedException e) {
            this.controller.handlePlayerError(player,"Error while sending CHARACTERCARD PHASE");
        }
        boolean doPhase = false;
        try {
            try {
                doPhase = this.view.getAnswer();
            } catch (MalformedMessageException e) {
                doPhase = this.view.getAnswer();
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player,"Error while getting answer");
        }
        if (doPhase) {
            CharacterCard card = this.getChosenCharacterCard(game.getCurrentPlayer(), player);
            this.playCard(card);
            ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
            players.remove(player);
            Gamer currentPlayer = null;
            try {
                currentPlayer = player.getGamer(this.game.getGamers());
            } catch (ModelErrorException e) {
                this.controller.shutdown("Error founded in model : shutting down this game");
            }
            for(Player player1 : players){
                this.updateChosenCharacterCard(player1, currentPlayer, card);
            }
        }
    }

    private CharacterCard getChosenCharacterCard(ExpertGamer gamer, Player player) {
        CharacterCard card = null;
        int coins = gamer.getDashboard().getCoins();
        ArrayList<CharacterCard> cards = new ArrayList<>();
        for (CharacterCard card1 : game.getGameCards()) {
            if (card1.getMoneyCost() <= coins)
                cards.add(card1);
        }
        try{
            try{
                card = this.view.getChosenCharacterCard(game, cards);
            }catch (MalformedMessageException e){
                card = this.view.getChosenCharacterCard(game, cards);
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player, "Error while getting chosen character card");
        }catch (ModelErrorException e) {
            this.controller.handlePlayerError(player, "Error: doesn't exist card seleted");
        }
        return card;
    }

    private void playCard(CharacterCard card) {

    }

    private void updateChosenCharacterCard(Player player, Gamer currentGamer, CharacterCard card) {
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.sendContext(CONTEXT_CHARACTER.getFragment());
                this.view.sendChosenCharacterCard(card, (ExpertGamer) currentGamer);
            }catch (MalformedMessageException | FlowErrorException e){
                this.view.sendContext(CONTEXT_CHARACTER.getFragment());
                this.view.sendChosenCharacterCard(card, (ExpertGamer) currentGamer);
            }
        }catch (FlowErrorException | MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player, "Error while updating chosen card deck figure");
        }
    }

    public GamePhase next() {
        return this.nextPhase;
    }
}