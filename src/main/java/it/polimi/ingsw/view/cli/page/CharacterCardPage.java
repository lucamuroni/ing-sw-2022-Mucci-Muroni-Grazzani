package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

public class CharacterCardPage implements Page {

    private final Cli cli;
    private final ArrayList<CharacterCard> cards;
    private final Game game;
    private boolean isReadyToProcede;

    public CharacterCardPage(Cli cli, ArrayList<CharacterCard> options, Game game){
        this.cli = cli;
        this.cards = new ArrayList<>(options);
        this.game = game;
        this.isReadyToProcede = false;
    }

    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for(CharacterCard card : this.cards){
            options.add(card.getName());
        }
        options.add("Effects");
        Menù menù = new Menù(options);
        menù.setContext("Please chose a Character card from below");
        int choice = this.cli.readInt(options.size(),menù,false);
        if(choice == options.size()){
            this.printEffects();
            throw new UndoException();
        }
        for(CharacterCard card : this.cards){
            if(card.getName().equals(options.get(choice-1))){
                this.game.getSelf().setCurrentExpertCardSelection(card);
            }
        }
        this.setReadyToProcede();
    }

    @Override
    public synchronized boolean isReadyToProceed() {
        return isReadyToProcede;
    }

    @Override
    public void kill() {

    }

    private void printEffects(){
        //TODO : printare gli effetti di ogni singola carta
        //PlaceHolder
        System.out.println("Qui printo gli effetti delle carte");
    }

    private synchronized void setReadyToProcede(){
        this.isReadyToProcede = true;
    }
}
