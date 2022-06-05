package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectAssistantCardDeckPage implements Page {
    private ArrayList<AssistantCardDeckFigures> figures;
    private Gamer self;
    private Cli cli;
    private boolean killed;
    private boolean readyToProceed = false;
    
    public SelectAssistantCardDeckPage(Cli cli, Gamer self, ArrayList<AssistantCardDeckFigures> figures) {
        this.cli = cli;
        this.self = self;
        this.figures = figures;
        this.killed = false;
    }

    /**
     * Method that handles the page
     * @throws UndoException to repeat the choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for(AssistantCardDeckFigures figures : this.figures){
            options.add(figures.name());
        }
        Menù menù = new Menù(options);
        menù.setContext("Please select a deck ");
        int choice;
        //Controllo del back
        choice = this.cli.readInt(options.size(), menù);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        //cli.clearConsole();
        self.setFigure(this.figures.get(choice-1));
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
