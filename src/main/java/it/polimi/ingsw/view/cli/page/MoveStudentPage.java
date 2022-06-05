package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;
import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * Class that represents the page to decide where to move a student (?)
 */
public class MoveStudentPage implements Page {
    private boolean killed;
    private boolean readyToProceed = false;
    private Cli cli;
    private Game assetGame;

    /**
     * Class constructor
     */
    public MoveStudentPage(Cli cli, Game assetGame){
        this.cli = cli;
        this.assetGame = assetGame;
        this.killed = false;
    }

    /**
     * Method that handles the page
     * @throws UndoException to repeat the choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        options.add("Hall");
        options.add("Island");
        Menù menù= new Menù(options);
        menù.setContext("Where do you want to move your player?");
        menù.print();
        int choice;
        //Controllo del back
        choice = this.cli.readInt(options.size(), menù, false);
        if(choice==2){
            options.clear();
            for(Island island : this.assetGame.getIslands()){
                options.add("Island " + island.getId());
            }
            options.add("Back");
            menù.clear();
            menù.addOptions(options);
            menù.setContext("Which island do you want to choose?");
            menù.print();
            //Back presente
            choice = this.cli.readInt(options.size(), menù, true);
            assetGame.setChosenIsland(this.assetGame.getIslands().get(choice-1));
        }
        options.clear();
        options.add("Red");
        options.add("Blue");
        options.add("Yellow");
        options.add("Green");
        options.add("Pink");
        options.add("Back");
        menù.clear();
        menù.addOptions(options);
        menù.setContext("Which type of student do you want to move?");
        menù.print();
        //Controllo del back
        choice = this.cli.readInt(options.size(), menù, true);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        //cli.clearConsole();
        switch (choice) {
            case 1 -> assetGame.setChosenColor(PawnColor.RED);
            case 2 -> assetGame.setChosenColor(PawnColor.BLUE);
            case 3 -> assetGame.setChosenColor(PawnColor.YELLOW);
            case 4 -> assetGame.setChosenColor(PawnColor.GREEN);
            case 5 -> assetGame.setChosenColor(PawnColor.PINK);
        }
        this.setReadyToProcede();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public synchronized boolean isReadyToProceed() {
        if(readyToProceed){
            readyToProceed = false;
            return true;
        }else{
            return false;
        }
    }

    private synchronized void setReadyToProcede(){
        this.readyToProceed = true;
    }

    @Override
    public synchronized void kill() {
        this.killed = true;
    }

    private synchronized boolean isKilled(){
        return this.killed;
    }
}
