package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

public class MoveMotherNaturePage implements Page {
    private Menù menù;
    private Game game;
    private Cli cli;
    private ArrayList<Island> islands;
    private boolean readyToProceed = false;
    private boolean killed;

    public MoveMotherNaturePage(Cli cli, Game game, ArrayList<Island> islands) {
        this.cli = cli;
        this.game = game;
        this.killed = false;
        this.islands = islands;
    }

    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<>();
        for (Island island : this.islands) {
            options.add("Island " + island.getId());
        }
        Menù menù = new Menù(options);
        this.cli.drawArchipelago();
        menù.setContext("Which island do you want to choose?");
        int choice = this.cli.readInt(options.size(), menù, false);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        //cli.clearConsole();
        game.setMotherNaturePosition(this.islands.get(choice-1));
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
