package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Random;

public class GameSetup implements GamePhase{
    // 10 = numero di studenti da dare alle isole
    public void handle(Game game, GameController controller){
        controller.getGamers().clear();
        controller.getGamers().addAll(game.getGamers());
        for(Player player : controller.getPlayers()){
            controller.getView().setCurrentPlayer(player);
            controller.getView().updateMotherNaturePlace(game.getMotherNature().getPlace());
        }
        // piazzare 10 studenti(coppie din colori) sulle isole
        // 8 (2) e 6 (3) torri per giocatore
        // scegliere carte mago assitente
        // studenti dal sacco 7 (2) oppure 9 (3)
    }

    public GamePhase next(){
        return new PlanningPhase();
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
}
