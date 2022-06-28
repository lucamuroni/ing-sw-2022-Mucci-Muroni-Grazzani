package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

public class MoveMotherNaturePage implements Page {
    private final Game game;
    private final Cli cli;
    private final ArrayList<Island> islands;
    private boolean readyToProceed = false;
    private boolean killed;
    private final boolean expert;

    public MoveMotherNaturePage(Cli cli, Game game, ArrayList<Island> islands, boolean expert) {
        this.cli = cli;
        this.game = game;
        this.killed = false;
        this.islands = islands;
        this.expert = expert;
    }

    @Override
    public void handle() throws UndoException {
        this.cli.drawArchipelago();
        ArrayList<String> options = new ArrayList<>();
        ArrayList<Integer> possibleIslands = new ArrayList<>();
        for(Island island : this.islands){
            if (!island.isMerged()){
                options.add("Island " + island.getId());
                possibleIslands.add(island.getId());
            }
        }
        //options.add("Back");
        Menù menù = new Menù(options);
        menù.setContext("Which island do you want to choose?");
        int choice = this.cli.readInt(options.size(), menù, false);
        Island selectedIsland =  null;
        for(Island island : this.game.getIslands()){
            if(island.getId() == possibleIslands.get(choice-1)){
                selectedIsland = island;
            }
        }
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        //cli.clearConsole();
        if (expert)
            game.getSelf().setSelectedIsland(selectedIsland);
        else
            game.setMotherNaturePosition(selectedIsland);
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
