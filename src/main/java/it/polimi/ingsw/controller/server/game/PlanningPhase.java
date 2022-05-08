package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.game.Game;

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
            this.view.setCurrentPlayer(player);
            this.view.updateCloudsStatus(this.game.getClouds());
        }
        ArrayList<AssistantCard> alreadyPlayedCards = new ArrayList<AssistantCard>();
        for(Player player : this.controller.getPlayers()){
            ArrayList<AssistantCard> possibleCards;
            try {
                possibleCards = new ArrayList<AssistantCard>(player.getGamer(this.game.getGamers()).getDeck().getCardList())
            } catch (ModelErrorException e) {
                System.out.println("Error founded in model : shutting down this game");
                this.controller.shutdown();
                e.printStackTrace();
                return;
            }
            if(alreadyPlayedCards.size()>=1){
                for(AssistantCard card : alreadyPlayedCards){
                    possibleCards.remove(card);
                }
            }
            this.view.setCurrentPlayer(player);
            AssistantCard card = this.view.getChosenAssistantCard(possibleCards);
            player.getGamer(this.game.getGamers())
        }
        //scelta della carta assistente
    };

    public GamePhase next(){
        return new ActionPhase1(this.game,this.controller);
    };
}
