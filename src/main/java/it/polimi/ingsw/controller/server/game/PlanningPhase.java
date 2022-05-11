package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.*;
import it.polimi.ingsw.controller.server.game.exceptions.GenericErrorException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;
import java.util.Random;

/**
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
     * @param game represents the current game that is handled
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
     * This main method that handles this phase
     */
    public void handle (){
        for(Cloud cloud : this.game.getClouds()){
            this.game.fillCloud(this.game.getBag().pullStudents(this.numStudents), cloud);
        }
        for(Player player : this.controller.getPlayers()){
            this.updateCloudsStatus(player);
        }
        ArrayList<AssistantCard> alreadyPlayedCards = new ArrayList<AssistantCard>();
        for(Player player : this.controller.getPlayers()){
            try {
                AssistantCard card = this.getChoseAssistantCard(player,alreadyPlayedCards);
                alreadyPlayedCards.add(card);
            } catch (ModelErrorException e) {
                this.controller.shutdown();
                e.printStackTrace();
                return;
            } catch (GenericErrorException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    public GamePhase next(){
        return new ActionPhase1(this.game,this.controller);
    }

    /**
     * This method is called in handle() to update the dashboards of all the players when the clouds are filled
     * @param player represents the player whose view will be adjourned
     */
    private void updateCloudsStatus(Player player){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.updateCloudsStatus(this.game.getClouds());
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateCloudsStatus(this.game.getClouds());
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    /**
     * This method, called by handle(), manages the AssistantCards choose by the players
     * @param player
     * @param alreadyPlayedCards
     * @return
     * @throws ModelErrorException
     * @throws GenericErrorException
     */
    private AssistantCard getChoseAssistantCard(Player player,ArrayList<AssistantCard> alreadyPlayedCards) throws ModelErrorException, GenericErrorException {
        this.view.setCurrentPlayer(player);
        ArrayList<AssistantCard> cardsOfPlayer;
        Gamer currentPlayer = player.getGamer(this.game.getGamers());
        AssistantCard result;
        cardsOfPlayer = new ArrayList<AssistantCard>(currentPlayer.getDeck().getCardList());
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
            }catch (TimeHasEndedException e){
                result = this.getRandomAssistantCard(cardsOfPlayer);
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
            throw new GenericErrorException();
        }catch (TimeHasEndedException e){
            result = this.getRandomAssistantCard(cardsOfPlayer);
        }
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        players.remove(player);
        for (Player pl : players) {
            this.view.sendChosenAssistantCard(result, player.getToken());
        }
        currentPlayer.getDeck().setPastSelection();
        currentPlayer.getDeck().setCurrentSelection(result);
        return result;
    }

    /**
     * Method called by getChoseAssistantCard() that pick a random AssistantCard when the player doesn't reply in time
     * @param cards is the ArrayList of possible choices
     * @return the random AssistantCard chosen
     */
    private AssistantCard getRandomAssistantCard(ArrayList<AssistantCard> cards){
        Random random = new Random();
        int rand = random.nextInt(0, cards.size());
        return cards.get(rand);
    }
}
