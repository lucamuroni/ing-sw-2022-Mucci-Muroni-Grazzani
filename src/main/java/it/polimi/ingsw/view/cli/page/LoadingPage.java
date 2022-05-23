package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;

import java.io.IOException;

public class LoadingPage implements Page {
    private final LoadingBar loadingBar;
    private final Cli cli;
    private boolean clearance;

    public LoadingPage(Cli cli){
        this.cli = cli;
        this.loadingBar = new LoadingBar(15);
        this.clearance = false;
    }

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
            while(!this.getClearance()){
                this.draw();
            }
        });
        t.start();
    }

    @Override
    public synchronized boolean isProcessReady() {
        return true;
    }

    @Override
    public synchronized void setClearance(boolean clearance) {
        this.clearance = clearance;
    }

    private synchronized boolean getClearance(){
        return  this.clearance;
    }
}
