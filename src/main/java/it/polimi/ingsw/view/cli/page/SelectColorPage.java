package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca Muroni
 * This class is used to ask a player which color of students he wants to choose to play due to the effect
 * of a characterCard (merhcant, thief)
 */
public class SelectColorPage implements Page {
    private boolean killed;
    private boolean readyToProceed = false;
    private final Cli cli;
    private final Game game;
    private final String name;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param game is the current game
     * @param name is the name of the played characterCard
     */
    public SelectColorPage(Cli cli, Game game, String name) {
        this.cli = cli;
        this.game = game;
        this.name = name;
        this.killed = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
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

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public boolean isReadyToProceed() {
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