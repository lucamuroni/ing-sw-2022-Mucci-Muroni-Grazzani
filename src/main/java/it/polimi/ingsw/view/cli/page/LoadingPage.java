package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;

import java.io.IOException;

public class LoadingPage implements Page {
    private final LoadingBar loadingBar;
    private final Cli cli;
    private boolean isKilled;

    public LoadingPage(Cli cli){
        this.cli = cli;
        this.loadingBar = new LoadingBar(15);
        this.isKilled = false;
    }

    @Override
    public void draw() {
        String padding = "   ";
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
        System.out.print("Please wait until a connection with the server is established"+padding);
        this.loadingBar.print();
        this.cli.clearConsole();
    }

    @Override
    public void handle() {
        Thread t = new Thread(()->{
            while(this.isAlive()){
                this.draw();
            }
        });
        t.start();
    }

    public synchronized void kill(){
        this.isKilled = true;
    }

    private synchronized boolean isAlive(){
        return  !this.isKilled;
    }
}
