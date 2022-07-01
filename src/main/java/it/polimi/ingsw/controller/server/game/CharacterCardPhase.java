package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class implements the expert phase of an expertGame, where the currentPlayer can choose to play a card and, if the
 * answer is true, which one to play
 */
public class CharacterCardPhase implements GamePhase{
    private final ExpertGame game;
    private final GameController controller;
    private final View view;
    private final GamePhase nextPhase;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     * @param nextPhase represents the next phase to go after this expert phase has been concluded
     */
    public CharacterCardPhase(ExpertGame game, GameController controller, GamePhase nextPhase) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        this.nextPhase = nextPhase;
    }

    /**
     * This is the main method that handles the CharacterCardPhase
     */
    public void handle() {
        int coins = this.game.getCurrentPlayer().getDashboard().getCoins();
        ArrayList<CharacterCard> cards = new ArrayList<>();
        for (CharacterCard card1 : game.getGameCards()) {
            if (card1.getMoneyCost() <= coins)
                cards.add(card1);
        }
        this.check(cards);
        if (!cards.isEmpty()) {
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
                CharacterCard card = this.getChosenCharacterCard(cards, player);
                if(card == null){
                    return;
                }
                this.game.getDeck().playCard(card,this.game);
                ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
                players.remove(player);
                Gamer currentPlayer = null;
                try {
                    currentPlayer = player.getGamer(this.game.getGamers());
                } catch (ModelErrorException e) {
                    this.controller.shutdown("Error founded in model : shutting down this game");
                }
                for (Player player1 : players){
                    this.updateChosenCharacterCard(player1, currentPlayer, card);
                }
                Player current;
                try {
                    current = this.controller.getPlayer(this.game.getCurrentPlayer());
                } catch (ModelErrorException e) {
                    throw new RuntimeException(e);
                }
                this.view.setCurrentPlayer(current);
                try {
                    try {
                        this.view.sendCoins(this.game.getCurrentPlayer().getDashboard().getCoins());
                    } catch (MalformedMessageException e) {
                        this.view.sendCoins(this.game.getCurrentPlayer().getDashboard().getCoins());
                    }
                } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                    this.controller.handlePlayerError(current, "Error while sending coins");
                }
                if(card == CharacterCard.AMBASSADOR){
                    ConquerIslandPhase ambassadorPhase = new ConquerIslandPhase(game,controller);
                    ambassadorPhase.setTarget(this.game.getDeck().getIslandParameter());
                    ambassadorPhase.handle();
                }else{
                    for (Player player1 : this.controller.getPlayers()) {
                        this.view.setCurrentPlayer(player1);
                        for (Island island : this.game.getIslands()) {
                            try {
                                try {
                                    this.view.sendContext(CONTEXT_ISLAND.getFragment());
                                    this.view.updateIslandStatus(island);
                                } catch (MalformedMessageException e) {
                                    this.view.sendContext(CONTEXT_ISLAND.getFragment());
                                    this.view.updateIslandStatus(island);
                                }
                            } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                                this.controller.handlePlayerError(player1, "Error while updating islands");
                            }
                        }
                        for (Gamer gamer : this.game.getGamers()) {
                            try {
                                try {
                                    this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                                    this.view.updateDashboards(gamer, game);
                                } catch (MalformedMessageException e) {
                                    this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                                    this.view.updateDashboards(gamer, game);
                                }
                            } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                                this.controller.handlePlayerError(player1, "Error while updating dashboards");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method called by handle() to get the characterCard chosen by the currentPlayer
     * @param cards is the arrayList of possible choices
     * @param player is the currentPlayer
     * @return the card chosen by the player
     */
    private CharacterCard getChosenCharacterCard(ArrayList<CharacterCard> cards, Player player) {
        CharacterCard card = null;
        try{
            try{
                card = this.view.getChosenCharacterCard(game, cards);
            }catch (MalformedMessageException e){
                card = this.view.getChosenCharacterCard(game, cards);
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player, "Error while getting chosen character card");
        }catch (ModelErrorException e) {
            this.controller.handlePlayerError(player, "Error: doesn't exist card selected");
        }
        return card;
    }

    /**
     * Method used to send to all others players the card chosen by the currentPlayer
     * @param player is another player
     * @param currentGamer is the currentPlayer
     * @param card is the card chosen by the currentPlayer
     */
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

    /**
     * Method called by handle() to check if the characterCard bard is in the possible choices; if that is true, it also
     * checks if it can be played or not
     * @param cards is the arrayList of possible choices
     */
    private void check(ArrayList<CharacterCard> cards) {
        for (CharacterCard card : cards) {
            if (card == CharacterCard.BARD) {
                int num = this.game.getCurrentPlayer().getDashboard().getHall().size();
                if (num == 0)
                    cards.remove(card);
                break;
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    public GamePhase next() {
        return this.nextPhase;
    }
}
