package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * This class is used to ask a player which cloud he wants to take the students from
 */
public class SelectCloudPage implements Page {
    private final ArrayList<Cloud> clouds;
    private final Game game;
    private final Cli cli;
    private boolean killed;
    private boolean readyToProceed = false;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param game is the current game
     * @param clouds is the arrayList of possible clouds the player can choose from
     */
    public SelectCloudPage(Cli cli, Game game, ArrayList<Cloud> clouds) {
        this.cli = cli;
        this.game = game;
        this.clouds = clouds;
        killed = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for(Cloud cloud : this.clouds){
                options.add("Cloud " + cloud.getId());
        }
        Menù menù = new Menù(options);
        this.cli.drawClouds();
        menù.setContext("Which cloud do you want to choose?");
        int choice = this.cli.readInt(options.size(), menù, false);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        game.setChosenCloud(clouds.get(choice-1));
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