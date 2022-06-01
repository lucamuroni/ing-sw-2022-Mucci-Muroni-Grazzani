package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;

/**
 * @author Davide Grazzani
 * Class that represents the loading page
 */
public class LoadingPage implements Page {
    private final LoadingBar loadingBar;
    private final Cli cli;
    private boolean killed;


    /**
     * Class constructor
     * @param cli represents the cli associated to the current game
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
        this.cli.clearConsole();
    }

    /**
     * Method that handles the loading page
     */
    @Override
    public void handle() {
        Thread t = new Thread(()->{
            while(!this.isKilled()){
                this.draw();
                //TODO : rimuovere linea
                this.kill();
            }
        });
        t.start();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public synchronized boolean isProcessReady() {
        return true;
    }

    @Override
    public synchronized void kill() {
        this.killed = true;
    }

    private synchronized boolean isKilled(){
        return this.killed;
    }

}
