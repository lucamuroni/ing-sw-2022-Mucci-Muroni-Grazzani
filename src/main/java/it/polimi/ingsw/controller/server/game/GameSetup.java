package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.*;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Random;

public class GameSetup implements GamePhase{
    private final int numTowers;
    private final int numStudents;
    private final Game game;
    private final GameController controller;
    private final View view;

    public GameSetup(GameController controller, Game game){
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        if(game.getGamers().size() == 2){
            this.numStudents = 7;
            this.numTowers = 8;
        }else{
            this.numTowers = 6;
            this.numStudents = 9;
        }
    }


    public void handle(){
        for(Player player : this.controller.getPlayers()){
            this.updateMotherNaturePlace(player);
        }
        this.initIslands(this.game);
        for(Player player : this.controller.getPlayers()){
            this.updateIslandStatus(player);
            try {
                player.getGamer(this.game.getGamers()).initGamer(this.game.getBag().pullStudents(this.numStudents),this.numTowers);
            } catch (ModelErrorException e) {
                System.out.println("Error founded in model : shutting down this game");
                this.controller.shutdown();
                e.printStackTrace();
                return;
            }
            this.updateDashboards(player);
        }
        for(Player player : this.controller.getPlayers()){
            AssistantCardDeckFigures figure = this.getChosenAssistantCardDeck(player);
            for(Player player1 : this.controller.getPlayers()){
                if(!player.equals(player1)){
                    this.updateChosenCardDeck(player1);
                }
            }
        }
    }

    public GamePhase next(){
        return new PlanningPhase(this.game,this.controller);
    }

    private void initIslands(Game game){
        ArrayList<Student> students = new ArrayList<Student>();
        for(PawnColor color : PawnColor.values()){
            for(int i = 0 ;i<2;i++){
                students.add(new Student(color));
            }
        }
        Random random = new Random();
        ArrayList<Student> copy = new ArrayList<Student>(students);
        students.clear();
        while (copy.size()>1){
            int i = random.nextInt(copy.size());
            students.add(copy.get(i));
            copy.remove(copy.get(i));
        }
        students.add(copy.get(0));
        game.initIsland(students);
    }

    private void updateMotherNaturePlace(Player player) {
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    private void updateIslandStatus(Player player){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.updateIslandStatus(this.game.getIslands());
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateIslandStatus(this.game.getIslands());
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    private void updateDashboards(Player player){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.updateDashboards(this.game.getGamers(), this.game);
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateDashboards(this.game.getGamers(), this.game);
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    private AssistantCardDeckFigures getChosenAssistantCardDeck(Player player){
        //TODO : selezione randomica dell'immagine nel caso non risponda in tempo
        AssistantCardDeckFigures card = ;
        this.view.setCurrentPlayer(player);
        try{
            try{
                card =this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }catch (MalformedMessageException | FlowErrorException e){
                this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }catch (TimeHasEndedException e1){
                System.out.println("Time for selecting Wizard Deck is Over");
                this.updateChosenCardDeck(player);
            }
        }catch (FlowErrorException | MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
        this.controller.getCardDesks().remove(card);
        return card;
    }

    private void updateChosenCardDeck(Player player){
        //TODO :definire una funzione in view per aggiornare il mazzo scelto
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.updateChosenCardDeck(this.game.getIslands());
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateChosenCardDeck(this.game.getIslands());
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

}
