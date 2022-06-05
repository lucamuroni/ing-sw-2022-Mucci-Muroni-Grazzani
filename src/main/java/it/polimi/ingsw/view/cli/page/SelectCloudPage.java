package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectCloudPage implements Page {
    private ArrayList<Cloud> clouds;
    private Game game;
    private Cli cli;
    private boolean killed;
    private boolean readyToProceed = false;

    public SelectCloudPage(Cli cli, Game game, ArrayList<Cloud> clouds) {
        this.cli = cli;
        this.game = game;
        this.clouds = clouds;
        killed = false;
    }

    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for(Cloud cloud : this.clouds){
                options.add("Cloud " + cloud.getId());
        }
        int choice;
        Menù menù = new Menù(options);
        menù.clear();
        menù.addOptions(options);
        menù.setContext("Which cloud do you want to choose?");
        menù.print();
        choice = this.cli.readInt(options.size(), menù, false);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        //cli.clearConsole();
        game.setChosenCloud(clouds.get(choice-1));
        this.setReadyToProcede();
    }

    @Override
    public boolean isReadyToProceed() {
        if(!this.readyToProceed){
            return false;
        }else {
            this.readyToProceed = false;
            return true;
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
