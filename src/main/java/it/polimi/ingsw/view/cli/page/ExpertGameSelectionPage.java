package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;

import java.util.ArrayList;

public class ExpertGameSelectionPage implements Page {

    private final Cli cli;
    private boolean response;
    private boolean readyToProcede;

    public ExpertGameSelectionPage(Cli cli){
        this.cli = cli;
        this.readyToProcede = false;
    }
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

    @Override
    public synchronized boolean isReadyToProceed() {
        return this.readyToProcede;
    }

    @Override
    public void kill() {

    }

    public boolean getAnswer(){
        return this.response;
    }

    private synchronized void setReadyToProcede(){
        this.readyToProcede = true;
    }
}
