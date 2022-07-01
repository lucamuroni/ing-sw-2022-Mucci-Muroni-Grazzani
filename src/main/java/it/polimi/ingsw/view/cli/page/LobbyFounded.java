package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;

/**
 * @author Davide Grazzani
 * This class is used to show a player that a lobby has been founded
 */
public class LobbyFounded implements Page {
    private final LoadingBar loadingBar;
    private final Cli cli;
    private boolean killed;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     */
    public LobbyFounded(Cli cli){
        this.cli = cli;
        loadingBar = new LoadingBar( 80);
        this.killed = false;
    }

    /**
     * This is the main method that manages the page
     */
    @Override
    public void handle() {
        Thread t = new Thread(() ->{
            while(!this.isKilled()){
                System.out.print("\n"+ AnsiColor.GREEN +"A LOOBY WAS FOUND"+ AnsiColor.RESET +"\n");
                System.out.print("Please be patient while we initialize the game"+"\n"+"   ");
                loadingBar.print();
                synchronized (this){
                    try {
                        this.wait(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.cli.clearConsole();
            }
        });
        t.start();
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