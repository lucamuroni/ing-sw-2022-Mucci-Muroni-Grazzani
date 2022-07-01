package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 */
public class MoveMotherNaturePage implements Page {
    private final Game game;
    private final Cli cli;
    private final ArrayList<Island> islands;
    private boolean readyToProceed = false;
    private boolean killed;
    private final boolean expert;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param game is the current game
     * @param islands is the arrayList of possible islands the player can choose from
     * @param expert is used to check if this page will be used by a characterCard (ambassador)
     */
    public MoveMotherNaturePage(Cli cli, Game game, ArrayList<Island> islands, boolean expert) {
        this.cli = cli;
        this.game = game;
        this.killed = false;
        this.islands = islands;
        this.expert = expert;
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
        ArrayList<Integer> possibleIslands = new ArrayList<>();
        for(Island island : this.islands){
            if (!island.isMerged()){
                options.add("Island " + island.getId());
                possibleIslands.add(island.getId());
            }
        }
        Menù menù = new Menù(options);
        menù.setContext("Which island do you want to choose?");
        int choice = this.cli.readInt(options.size(), menù, false);
        Island selectedIsland =  null;
        for(Island island : this.game.getIslands()){
            if(island.getId() == possibleIslands.get(choice-1)){
                selectedIsland = island;
            }
        }
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        if (expert)
            game.getSelf().setSelectedIsland(selectedIsland);
        else
            game.setMotherNaturePosition(selectedIsland);
        this.setReadyToProcede();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public boolean isReadyToProceed() {
        if(!this.readyToProceed){
            return false;
        }else {
            this.readyToProceed = false;
            return true;
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