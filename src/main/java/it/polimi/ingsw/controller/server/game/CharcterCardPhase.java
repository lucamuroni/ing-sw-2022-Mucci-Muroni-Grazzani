package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.debug.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_FIGURE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_PHASE;

public class CharcterCardPhase implements GamePhase{
    private final ExpertGame game;
    private final GameController controller;
    private final View view;
    private final Phase phase;

    public CharcterCardPhase(ExpertGame game, GameController controller, Phase phase) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        this.phase = phase;
    }

    public void handle() {
        Player player = null;
        try {
            player = this.controller.getPlayer(this.game.getCurrentPlayer();
        } catch (ModelErrorException e) {
            throw new RuntimeException(e);
        }
        this.view.setCurrentPlayer(player);
        try {
            try{
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.CHARACTER_PHASE);
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.CHARACTER_PHASE);
            }
        }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException |
                ClientDisconnectedException e) {
            this.controller.handlePlayerError(player,"Error while sending CHARACTERCARD PHASE");
        }
        boolean doPhase = false;
        try {
            try {
                doPhase = this.view.getAnswer();
            } catch (MalformedMessageException | FlowErrorException e) {
                doPhase = this.view.getAnswer();
            }
        } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player,"Error while getting answer");
        }
        if (doPhase) {
            CharacterCard card = this.getChosenCharacterCard(player);
            this.playcard(card);
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

    private CharacterCard getChosenCharacterCard(Player player) {
        CharacterCard card = null;
        try{
            try{
                card = this.view.getChosenCharacterCard();
            }catch (MalformedMessageException e){
                card = this.view.getChosenCharacterCard();
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player, "Error while getting chosen character card");
        }
        return card;
    }

    private void playcard(CharacterCard card) {

    }

    private void updateChosenCharacterCard(Player player, Gamer currentGamer, CharacterCard card) {
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.sendContext(CONTEXT_CHARACTER.getFragment());
                this.view.sendChosenCharacterCard(card, currentGamer);
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.sendContext(CONTEXT_CHARACTER.getFragment());
                this.view.sendChosenCharacterCard(card, currentGamer);
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player, "Error while updating chosen card deck figure");
        }
    }

    public GamePhase next() {
        GamePhase nextPhase = null;
        switch (phase) {
            case ACTION_PHASE_1:
                nextPhase = new ActionPhase1(this.game, this.controller);
                break;
            case MOTHER_NATURE_PHASE:
                nextPhase = new MotherNaturePhase(this.game, this.controller);
                break;
            case ACTION_PHASE_3:
                nextPhase = new ActionPhase3(this.game, this.controller);
                break;
        }
        return nextPhase;
    }
}
