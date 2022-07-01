package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.Cli;

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * This class is used to ask a player if he wants to play a characterCard
 */
public class ExpertGameSelectionPage implements Page {
    private final Cli cli;
    private boolean response;
    private boolean readyToProcede;
    private boolean killed;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     */
    public ExpertGameSelectionPage(Cli cli){
        this.cli = cli;
        this.readyToProcede = false;
        this.killed = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Do you want to play a Character card (y/n) : ",options,true);
        if(input.equals("y")){
            this.response = true;
        }else{
            this.response = false;
        }
        setReadyToProcede();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public synchronized boolean isReadyToProceed() {
        return this.readyToProcede;
    }

    /**
     * Method used to set that the page has completed its task
     */
    @Override
    public void kill() {
        this.killed = true;
    }

    /**
     * Method used to get the answer of the player
     * @return the player's answer
     */
    public boolean getAnswer(){
        return this.response;
    }

    /**
     * Method used to set that the page has completed its task
     */
    private synchronized void setReadyToProcede(){
        this.readyToProcede = true;
    }
}