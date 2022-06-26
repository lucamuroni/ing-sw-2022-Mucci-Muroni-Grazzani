package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.List;

public class SelectColorPage implements Page {
    private boolean killed;
    private boolean readyToProceed = false;
    private final Cli cli;
    private final Game game;
    private final String name;

    public SelectColorPage(Cli cli, Game game, String name) {
        this.cli = cli;
        this.game = game;
        this.name = name;
        this.killed = false;
    }


    @Override
    public void handle() throws UndoException {
        ArrayList<PawnColor> colors = new ArrayList<>(List.of(PawnColor.values()));
        if (name.equals("Merchant")) {
            this.cli.drawArchipelago();
        }
        this.cli.drawDashboard();
        ArrayList<String> options = new ArrayList<>();
        for (PawnColor color : colors) {
            options.add(color.name());
        }
        Menù menù = new Menù(options);
        menù.setContext("Choose a color: ");
        int choice = this.cli.readInt(options.size(), menù, false);
        PawnColor color = colors.get(choice-1);
        options.clear();
        options.add("y");
        options.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        this.game.getSelf().setSelectedColor(color);
        this.setReadyToProcede();
    }

    @Override
    public boolean isReadyToProceed() {
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
