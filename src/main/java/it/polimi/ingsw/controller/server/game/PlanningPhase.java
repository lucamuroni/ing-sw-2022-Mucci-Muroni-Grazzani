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

public class PlanningPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final int numStudents;
    private final View view;

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

    public GamePhase next(){
        return new ActionPhase1(this.game,this.controller);
    }

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
        return result;
    }

    private AssistantCard getRandomAssistantCard(ArrayList<AssistantCard> cards){
        //TODO : implementare funzione randomica per scegliere una carta a caso nel caso in cui il giocatore non risponda in tempo
    }
}
