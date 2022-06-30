package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.*;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class implements the first phase of the game, which is the planning phase, where all the players choose
 * an AssistantCard to be played
 */
public class PlanningPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final int numStudents;
    private final View view;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public PlanningPhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        if(this.game.getGamers().size()==2){
            this.numStudents = 3;
        }else{
            this.numStudents = 4;
        }
    }

    /**
     * This main method that handles the PlanningPhase
     */
    public void handle (){
        if(!this.controller.isGameStarted()){
            this.controller.setGameStarted();
        }
        for(Cloud cloud : this.game.getClouds()){
            this.game.fillCloud(this.game.getBag().pullStudents(this.numStudents), cloud);
        }
        for(Player player : this.controller.getPlayers()){
            this.updateCloudsStatus(player);
        }
        ArrayList<AssistantCard> alreadyPlayedCards = new ArrayList<>();
        for(Player player : this.controller.getPlayers()){
                this.view.setCurrentPlayer(player);
                try {
                    try{
                        this.view.sendContext(CONTEXT_PHASE.getFragment());
                        this.view.sendNewPhase(Phase.PLANNING_PHASE);
                    }catch (MalformedMessageException | FlowErrorException e){
                        this.view.sendContext(CONTEXT_PHASE.getFragment());
                        this.view.sendNewPhase(Phase.PLANNING_PHASE);
                    }
                }catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                        this.controller.handlePlayerError(player,"Error while sending PLANNING PHASE");
                }
            AssistantCard card = null;
            try {
                card = this.getChoseAssistantCard(player, alreadyPlayedCards);
            } catch (ModelErrorException e) {
                this.controller.shutdown("Error founded in model : shutting down this game");
            }
            alreadyPlayedCards.add(card);
        }
        ArrayList<Player> pls = new ArrayList<>(this.controller.getPlayers());
        for (Player player : pls) {
            this.view.setCurrentPlayer(player);
            try {
                try {
                    this.view.sendContext(CONTEXT_USERNAME.getFragment());
                    this.view.sendActiveUsername(this.controller.getPlayer(this.game.getCurrentPlayer()));
                } catch (MalformedMessageException | FlowErrorException e) {
                    this.view.sendContext(CONTEXT_USERNAME.getFragment());
                    this.view.sendActiveUsername(this.controller.getPlayer(this.game.getCurrentPlayer()));
                }
            } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                this.controller.handlePlayerError(player,"Error while uploading current player to other gamers");
            } catch (ModelErrorException e) {
                this.controller.shutdown("Error founded in model : shutting down this game");
            }
        }
    }

    /**
     * This method is called in handle() to update the views of all the players when the clouds are filled
     * @param player represents the player whose view will be adjourned
     */
    private void updateCloudsStatus(Player player){
        this.view.setCurrentPlayer(player);
        for (Cloud cloud : this.game.getClouds()) {
            try{
                try{
                    this.view.sendContext(CONTEXT_CLOUD.getFragment());
                    this.view.updateCloudsStatus(cloud);
                }catch (MalformedMessageException | FlowErrorException e){
                    this.view.sendContext(CONTEXT_CLOUD.getFragment());
                    this.view.updateCloudsStatus(cloud);
                }
            }catch (FlowErrorException | MalformedMessageException | ClientDisconnectedException e){
                this.controller.handlePlayerError(player,"Error while updating clouds status");
            }
        }

    }

    /**
     * This method is called by handle() and it asks a player which AssistantCard he wants to play
     * @param player represents the current player
     * @param alreadyPlayedCards are all the cards played till this moment
     * @return the card chosen by the player
     */
    private AssistantCard getChoseAssistantCard(Player player, ArrayList<AssistantCard> alreadyPlayedCards) throws ModelErrorException {
        this.view.setCurrentPlayer(player);
        Gamer currentPlayer = player.getGamer(this.game.getGamers());
        AssistantCard result = null;
        ArrayList<AssistantCard> cardsOfPlayer = new ArrayList<>(currentPlayer.getDeck().getCardList());
        if(alreadyPlayedCards.size()>=1){
            for(AssistantCard cardAlreadySelected: alreadyPlayedCards){
                cardsOfPlayer.remove(cardAlreadySelected);
            }
        }
        try{
            try{
                result = this.view.getChosenAssistantCard(cardsOfPlayer);
            }catch (MalformedMessageException e){
                result = this.view.getChosenAssistantCard(cardsOfPlayer);
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player,"Error while getting the chose assistant card");
        }
        this.sendInfo(player, result);
        currentPlayer.getDeck().setPastSelection();
        currentPlayer.getDeck().setCurrentSelection(result);
        return result;
    }

    /**
     * This method sends to all the other players the AssistantCard chosen by the currentPlayer
     * @param currentPlayer is the player playing now
     * @param chosenCard is the card chosen by the currentPlayer
     */
    private void sendInfo(Player currentPlayer, AssistantCard chosenCard) {
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        players.remove(currentPlayer);
        for (Player player : players) {
            this.view.setCurrentPlayer(player);
            try {
                try {
                    this.view.sendContext(CONTEXT_CARD.getFragment());
                    this.view.sendChosenAssistantCard(chosenCard, currentPlayer.getToken());
                } catch (MalformedMessageException | FlowErrorException e) {
                    this.view.sendContext(CONTEXT_CARD.getFragment());
                    this.view.sendChosenAssistantCard(chosenCard, currentPlayer.getToken());
                }
            } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                this.controller.handlePlayerError(player, "Error while uploading chosen assistant card");
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    public GamePhase next(){
        if(controller.getGameType()== GameType.EXPERT ){
            return new CharacterCardPhase((ExpertGame) this.game,this.controller,new ActionPhase1(this.game, this.controller));
        }
        return new ActionPhase1(this.game, this.controller);
    }
}
