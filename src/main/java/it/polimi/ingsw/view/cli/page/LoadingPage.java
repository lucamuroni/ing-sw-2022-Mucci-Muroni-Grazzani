package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;

/**
 * @author Davide Grazzani
 * this class is used to welcome the player when the application starts
 */
public class LoadingPage implements Page {
    private final LoadingBar loadingBar;
    private final Cli cli;
    private boolean killed;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     */
    public LoadingPage(Cli cli){
        this.cli = cli;
        this.loadingBar = new LoadingBar(15);
        this.killed = false;
    }

    /**
     * Method that draws the loading page
     */
    public void draw() {
        String padding = "                        ";
        System.out.println("Welcome to " +AnsiColor.GREEN.toString()+
                "\n" +
                "███████╗██████╗ ██╗   ██╗ █████╗ ███╗   ██╗████████╗██╗███████╗\n" +
                "██╔════╝██╔══██╗╚██╗ ██╔╝██╔══██╗████╗  ██║╚══██╔══╝██║██╔════╝\n" +
                "█████╗  ██████╔╝ ╚████╔╝ ███████║██╔██╗ ██║   ██║   ██║███████╗\n" +
                "██╔══╝  ██╔══██╗  ╚██╔╝  ██╔══██║██║╚██╗██║   ██║   ██║╚════██║\n" +
                "███████╗██║  ██║   ██║   ██║  ██║██║ ╚████║   ██║   ██║███████║\n" +
                "╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚═╝╚══════╝\n" +
                "                                                               \n" +
                "\n"+AnsiColor.RESET.toString());
        System.out.print("Please wait until a connection with the server is established\n"+padding);
        this.loadingBar.print();
        synchronized (this){
            try {
                this.wait(100);
            } catch (InterruptedException e) {}
        }
        this.cli.clearConsole();
    }

    /**
     * This is the main method that manages the page
     */
    @Override
    public void handle() {
        Thread t = new Thread(()->{
            while(!this.isKilled()){
                this.draw();
            }
        });
        t.start();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public synchronized boolean isReadyToProceed() {
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