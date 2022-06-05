package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * Class that represents the assistant card page
 */
public class SelectAssistantCardPage implements Page {
    private ArrayList<AssistantCard> cards;
    private Game game;
    private Cli cli;
    private boolean killed;
    private boolean readyToProceed = false;

    /**
     * Class constructor
     * @param cards represents the arrayList of possible cards the player can choose from
     * @param game represents the game
     */
    public SelectAssistantCardPage(Cli cli, ArrayList<AssistantCard> cards, Game game){
        this.cli = cli;
        this.cards = new ArrayList<>(cards);
        this.game = game;
        this.killed = false;
    }

    /**
     * Method that handles the page
     * @throws UndoException to repeat the choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for(AssistantCard card : this.cards){
            options.add(card.getName()+" ("+card.getTurnValue()+", "+card.getMovement()+")");
        }
        Menù menù = new Menù(options);
        menù.setContext("Please select a card ");
        int choice;
        //Controllo del back
        choice = this.cli.readInt(options.size(), menù, false);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        //cli.clearConsole();
        this.game.getSelf().setCurrentSelection(this.cards.get(choice-1));
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
