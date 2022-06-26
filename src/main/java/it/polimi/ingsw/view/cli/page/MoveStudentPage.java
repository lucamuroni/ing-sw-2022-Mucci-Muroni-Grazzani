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
    private final Cli cli;
    private final Game assetGame;

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
        this.cli.drawArchipelago();
        this.cli.drawDashboard();
        ArrayList<String> options = new ArrayList<>();
        int possiblePlace = this.assetGame.getPossiblePlace();
        if (possiblePlace == 2) {
            options.add("Hall");
        }
        options.add("Island");
        Menù menù= new Menù(options);
        menù.setContext("Where do you want to move your player?");
        int choice = this.cli.readInt(options.size(), menù, false);
        if(choice==2){
            this.cli.drawArchipelago();
            options.clear();
            for(Island island : this.assetGame.getIslands()){
                if (!island.isMerged())
                    options.add("Island " + island.getId());
            }
            options.add("Back");
            menù.clear();
            menù.addOptions(options);
            menù.setContext("Which island do you want to choose?");
            choice = this.cli.readInt(options.size(), menù, true);
            assetGame.setChosenIsland(this.assetGame.getIslands().get(choice-1));
        }
        this.cli.drawDashboard();
        options.clear();
        ArrayList<PawnColor> possibleColors = new ArrayList<>(this.assetGame.getPossibleColors(choice));
        for (PawnColor color : possibleColors) {
            options.add(color.name());
        }
        options.add("Back");
        menù.clear();
        menù.addOptions(options);
        menù.setContext("Which type of student do you want to move?");
        choice = this.cli.readInt(options.size(), menù, true);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        //cli.clearConsole();
        assetGame.setChosenColor(possibleColors.get(choice-1));
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
