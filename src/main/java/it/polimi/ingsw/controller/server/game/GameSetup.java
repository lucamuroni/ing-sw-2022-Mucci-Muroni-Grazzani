package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.*;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.AssistantCardDeck;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;
import java.util.Random;

public class GameSetup implements GamePhase{
    private final int numTowers;
    private final int numStudents;
    private final ArrayList<TowerColor> colors;
    private final Game game;
    private final GameController controller;
    private final View view;

    public GameSetup(GameController controller, Game game){
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        this.colors = new ArrayList<TowerColor>();
        this.colors.add(TowerColor.WHITE);
        this.colors.add(TowerColor.BLACK);
        if(game.getGamers().size() == 2){
            this.numStudents = 7;
            this.numTowers = 8;
        }else{
            this.numStudents = 9;
            this.numTowers = 6;
            this.colors.add(TowerColor.GREY);
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
                TowerColor color = this.randomColorPicker();
                //TODO: bisogna modificare il metodo initGamer per far sì che si passi come parametro il colore
                //      delle torri da associare al giocatore, oppure in alternativa, si può mettere un metodo setTowerColor
                //      e poi si usa il messaggio SendTowerColor per dire il colore di un giocatore a tutti i giocatori
                player.getGamer(this.game.getGamers()).initGamer(this.game.getBag().pullStudents(this.numStudents),this.numTowers);
            } catch (ModelErrorException e) {
                System.out.println("Error founded in model : shutting down this game");
                this.controller.shutdown();
            }
            this.updateDashboards(player);
        }
        for(Player player : this.controller.getPlayers()){
            AssistantCardDeckFigures figure = this.getChosenAssistantCardDeck(player);
            ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
            players.remove(player);
            Gamer currentPlayer = null;
            try {
                currentPlayer = player.getGamer(this.game.getGamers());
            } catch (ModelErrorException e) {
                this.controller.shutdown();
            }
            for(Player player1 : players){
                this.updateChosenCardDeck(player1, currentPlayer, figure);
            }
            players.clear();
        }
    }

    private void initIslands(Game game){
        ArrayList<Student> students = new ArrayList<Student>();
        for(PawnColor color : PawnColor.values()){
            for(int i=0 ; i<2 ;i++){
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
        this.view.setCurrentPlayer(player);
        AssistantCardDeckFigures card = null;
        try{
            try{
                card = this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }catch (MalformedMessageException e){
                card = this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }catch (TimeHasEndedException e1){
                card = this.randomFigurePicker();
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }catch (TimeHasEndedException e) {
            card = this.randomFigurePicker();
        }
        this.controller.getCardDesks().remove(card);
        return card;
    }

    private void updateChosenCardDeck(Player player, Gamer currentPlayer, AssistantCardDeckFigures figure){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.sendChosenAssistantCardDeck(figure, currentPlayer.getToken());
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.sendChosenAssistantCardDeck(figure, currentPlayer.getToken());
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    private AssistantCardDeckFigures randomFigurePicker() {
        Random random = new Random();
        int rand = random.nextInt(0, AssistantCardDeckFigures.values().length);
        return AssistantCardDeckFigures.values()[rand];
    }

    private TowerColor randomColorPicker() {
        Random random = new Random();
        int rand = random.nextInt(0, this.colors.size());
        TowerColor result = this.colors.get(rand);
        this.colors.remove(rand);
        return result;
    }

    public GamePhase next(){
        return new PlanningPhase(this.game,this.controller);
    }
}
