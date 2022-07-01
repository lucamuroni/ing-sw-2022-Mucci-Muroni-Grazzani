package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * This class is used to ask a player which characterCard he wants to play
 */
public class CharacterCardPage implements Page {
    private final Cli cli;
    private final ArrayList<CharacterCard> cards;
    private final Game game;
    private boolean isReadyToProcede;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param options is the arrayList of possible characterCards to choose from
     * @param game is the current game
     */
    public CharacterCardPage(Cli cli, ArrayList<CharacterCard> options, Game game){
        this.cli = cli;
        this.cards = new ArrayList<>(options);
        this.game = game;
        this.isReadyToProcede = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for(CharacterCard card : this.cards){
            options.add(card.getName());
        }
        options.add("Effects");
        options.add("Don't play");
        Menù menù = new Menù(options);
        menù.setContext("Please chose a Character card from below");
        int choice = this.cli.readInt(options.size(),menù,false);
        if(choice == options.size()-1){
            this.printEffects();
            throw new UndoException();
        }else if(choice == options.size()){
            this.game.getSelf().setCurrentExpertCardSelection(null);
            this.setReadyToProcede();
            return;
        }
        for(CharacterCard card : this.cards){
            if(card.getName().equals(options.get(choice-1))){
                this.game.getSelf().setCurrentExpertCardSelection(card);
            }
        }
        this.setReadyToProcede();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public synchronized boolean isReadyToProceed() {
        return isReadyToProcede;
    }

    /**
     * Method used to set that the page has completed its task
     */
    @Override
    public void kill() {}

    /**
     * Method used to print the effect of a card
     */
    private void printEffects(){
        //TODO : printare gli effetti di ogni singola carta
        //PlaceHolder
        System.out.println("Qui printo gli effetti delle carte");
    }

    /**
     * Method used to set that the page has completed its task
     */
    private synchronized void setReadyToProcede(){
        this.isReadyToProcede = true;
    }
}