package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.*;
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

        this.initIslands(this.game);
        for(Player player : this.controller.getPlayers()){
            this.view.setCurrentPlayer(player);
            this.view.updateIslandStatus(this.game.getIslands());
            try {
                player.getGamer(this.game.getGamers()).initGamer(this.game.getBag().pullStudents(this.numStudents),this.numTowers);
            } catch (ModelErrorException e) {
                System.out.println("Error founded in model : shutting down this game");
                this.controller.shutdown();
                e.printStackTrace();
                return;
            }
            this.view.updateDashboards(this.game.getGamers());
        }
        for(Player player : this.controller.getPlayers()){
            this.view.setCurrentPlayer(player);
            AssistantCardDeckFigures figure = this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            //TODO : propagare la scelta delle carte agli altri giocatori
            this.controller.getCardDesks().remove(figure);
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

    private void updateMotherNaturePlace() throws FlowErrorException, MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        for(Player player : this.controller.getPlayers()){
            this.view.setCurrentPlayer(player);
            try{
                this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
            }catch (FlowErrorException | ClientDisconnectedException){
                this.controller.
            }
        }
    }
}
