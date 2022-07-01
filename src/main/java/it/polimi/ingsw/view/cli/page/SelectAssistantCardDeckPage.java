package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * This class is used to ask a player which deck of assistantCards he wants to take
 */
public class SelectAssistantCardDeckPage implements Page {
    private final ArrayList<AssistantCardDeckFigures> figures;
    private final Gamer self;
    private final Cli cli;
    private boolean killed;
    private boolean readyToProceed = false;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param self is the player playing on this terminal
     * @param figures is the arrayList of possible decks the player can choose from
     */
    public SelectAssistantCardDeckPage(Cli cli, Gamer self, ArrayList<AssistantCardDeckFigures> figures) {
        this.cli = cli;
        this.self = self;
        this.figures = figures;
        this.killed = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for(AssistantCardDeckFigures figures : this.figures){
            options.add(figures.name());
        }
        Menù menù = new Menù(options);
        menù.setContext("Please select a deck ");
        int choice = this.cli.readInt(options.size(), menù, false);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        self.updateFigure(this.figures.get(choice-1));
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