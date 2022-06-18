package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_FIGURE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_PHASE;

public class DeckPhase implements GamePhase {
    private final Game game;
    private final GameController controller;
    private final View view;

    public DeckPhase(Game game, GameController controller) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
    }

    @Override
    public void handle() {
        for(Player player : this.controller.getPlayers()){
                this.view.setCurrentPlayer(player);
                try {
                    try{
                        this.view.sendContext(CONTEXT_PHASE.getFragment());
                        this.view.sendNewPhase(Phase.DECK_PHASE);
                    }catch (MalformedMessageException | FlowErrorException e){
                        this.view.sendContext(CONTEXT_PHASE.getFragment());
                        this.view.sendNewPhase(Phase.DECK_PHASE);
                    }
                }catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                    this.controller.handlePlayerError(player,"Error while sending DECK PHASE");
                }
                AssistantCardDeckFigures figure = this.getChosenAssistantCardDeck(player);
                ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
                players.remove(player);
                Gamer currentPlayer = null;
                try {
                    currentPlayer = player.getGamer(this.game.getGamers());
                } catch (ModelErrorException e) {
                    this.controller.shutdown("Error founded in model : shutting down this game");
                }
                for(Player player1 : players){
                    this.updateChosenCardDeck(player1, currentPlayer, figure);
                }
                players.clear();
        }
    }

    /**
     * This method is called by handle() and it asks a player which AssistantCardDeckFigure he wants to take
     * @param player is the player currently playing
     * @return the figure chosen by the player
     */
    private AssistantCardDeckFigures getChosenAssistantCardDeck(Player player){
        AssistantCardDeckFigures card = null;
        try{
            try{
                card = this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }catch (MalformedMessageException e){
                card = this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player, "Error while getting chosen assistant card");
        }
        //TODO : controllare se è tutto a posto
        this.controller.getCardDesks().remove(card);
        return card;
    }

    /**
     * This method is called by handle() and it sends to a player the AssistantCardDeckFigure chosen by the currentPlayer
     * @param player is the player whose view will be adjourned
     * @param currentPlayer is the current player
     * @param figure is the figure chosen by the currentPlayer
     */
    private void updateChosenCardDeck(Player player, Gamer currentPlayer, AssistantCardDeckFigures figure){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.sendContext(CONTEXT_FIGURE.getFragment());
                this.view.sendChosenAssistantCardDeck(figure, currentPlayer.getToken(), currentPlayer);
            }catch (MalformedMessageException | FlowErrorException e){
                this.view.sendContext(CONTEXT_FIGURE.getFragment());
                this.view.sendChosenAssistantCardDeck(figure, currentPlayer.getToken(), currentPlayer);
            }
        }catch (FlowErrorException | MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player, "Error while updating chosen card deck figure");
        }
    }

    private AssistantCardDeckFigures randomFigurePicker() {
        Random random = new Random();
        int rand = random.nextInt(0, AssistantCardDeckFigures.values().length);
        return AssistantCardDeckFigures.values()[rand];
    }

    @Override
    public GamePhase next() {
        return new PlanningPhase(this.game,this.controller);
    }
}
