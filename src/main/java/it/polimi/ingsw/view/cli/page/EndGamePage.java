package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Results;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;

/**
 * @author Davide Grazzani
 * This class is used to show to a player if he has won, lost or tied the game
 */
public class EndGamePage implements Page {
    private final Cli cli;
    private boolean killed = false;
    private final Results results;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param results is the result is the outcome of the player's game
     */
    public EndGamePage(Cli cli, Results results){
        this.cli = cli;
        this.results = results;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException {
        this.cli.clearConsole();
        if(results == Results.WIN){
            System.out.println(AnsiColor.GREEN.toString() +"\n" +
                    "██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗ \n" +
                    "██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗\n" +
                    "██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝\n" +
                    "██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗\n" +
                    "╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║\n" +
                    " ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝"+"\n"+AnsiColor.RESET.toString());
        }else if(results == Results.TIE || results == Results.ERROR){
            System.out.println(AnsiColor.YELLOW.toString()+"\n" +
                    "██████╗ ██████╗  █████╗ ██╗    ██╗\n" +
                    "██╔══██╗██╔══██╗██╔══██╗██║    ██║\n" +
                    "██║  ██║██████╔╝███████║██║ █╗ ██║\n" +
                    "██║  ██║██╔══██╗██╔══██║██║███╗██║\n" +
                    "██████╔╝██║  ██║██║  ██║╚███╔███╔╝\n" +
                    "╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚══╝╚══╝ "+"\n"+AnsiColor.RESET.toString());
        }else{
            System.out.println(AnsiColor.RED.toString()+"\n" +
                    "██╗      ██████╗ ███████╗███████╗██████╗ \n" +
                    "██║     ██╔═══██╗██╔════╝██╔════╝██╔══██╗\n" +
                    "██║     ██║   ██║███████╗█████╗  ██████╔╝\n" +
                    "██║     ██║   ██║╚════██║██╔══╝  ██╔══██╗\n" +
                    "███████╗╚██████╔╝███████║███████╗██║  ██║\n" +
                    "╚══════╝ ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝"+AnsiColor.RESET.toString());
        }
        System.out.println("\n");
        if(results == Results.ERROR){
            System.out.println(AnsiColor.YELLOW.toString()+"The game has ended due to a gamer error "+AnsiColor.RESET.toString());
        }
        System.out.println("\n");
        System.out.println("Thank you for playing this game");
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public boolean isReadyToProceed() {
        return true;
    }

    /**
     * Method used to terminate the page in case of threading
     */
    @Override
    public void kill() {
        this.killed = true;
    }
}