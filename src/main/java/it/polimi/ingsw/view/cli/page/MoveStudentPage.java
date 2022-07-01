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
 * @author Luca Muroni
 * This class is used to ask a player which students he wants to move and where
 */
public class MoveStudentPage implements Page {
    private boolean killed;
    private boolean readyToProceed = false;
    private final Cli cli;
    private final Game assetGame;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param assetGame is the current game
     */
    public MoveStudentPage(Cli cli, Game assetGame){
        this.cli = cli;
        this.assetGame = assetGame;
        this.killed = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
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
            ArrayList<Integer> possibleIslands = new ArrayList<>();
            for(Island island : this.assetGame.getIslands()){
                if (!island.isMerged()){
                    options.add("Island " + island.getId());
                    possibleIslands.add(island.getId());
                }
            }
            options.add("Back");
            menù.clear();
            menù.addOptions(options);
            menù.setContext("Which island do you want to choose?");
            choice = this.cli.readInt(options.size(), menù, true);
            Island selectedIsland =  null;
            for(Island island : this.assetGame.getIslands()){
                if(island.getId() == possibleIslands.get(choice-1)){
                    selectedIsland = island;
                }
            }
            assetGame.setChosenIsland(selectedIsland);
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

    /**
     * Method used to set that the page has completed its task
     */
    private synchronized void setReadyToProcede(){
        this.readyToProceed = true;
    }

    /**
     * Method used to terminate the page in case of threading
     */
    @Override
    public synchronized void kill() {
        this.killed = true;
    }

    /**
     * Method that checks if the page has been killed
     * @return true if killed, false otherwise
     */
    private synchronized boolean isKilled(){
        return this.killed;
    }
}