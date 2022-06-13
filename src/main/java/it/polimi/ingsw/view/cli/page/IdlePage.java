package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.cli.AsciiArchipelago;
import it.polimi.ingsw.view.cli.AsciiCloud;
import it.polimi.ingsw.view.cli.AsciiDashBoard;
import it.polimi.ingsw.view.cli.Cli;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that represents the idlePage. an idlePage is
 */
public class IdlePage implements Page {
    private boolean killed = false;
    private final AsciiArchipelago archipelago;
    private final Cli cli;
    private final ArrayList<AsciiCloud> clouds;
    private final ArrayList<AsciiDashBoard> dashBoards;

    public IdlePage(Cli cli, AsciiArchipelago archipelago, ArrayList<AsciiCloud> clouds, ArrayList<AsciiDashBoard> dashBoards){
        this.cli = cli;
        this.archipelago = archipelago;
        this.clouds = clouds;
        this.dashBoards = dashBoards;
        this.killed = false;
    }

    @Override
    public void handle() throws UndoException {
        Thread t = new Thread(()->{
            while(!this.isKilled()){
                try {
                    this.archipelago.draw();
                } catch (AssetErrorException e) {
                    this.cli.getController().handleError("Could not print archipelago");
                }
                System.out.print("\n");
                this.cli.drawClouds();
                System.out.print("\n");
                this.cli.drawDashboard();
                synchronized (this){
                    try {
                        this.wait(1000);
                    } catch (InterruptedException e) {}
                }
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

    @Override
    public synchronized void kill() {
        this.killed = true;
    }

    private synchronized boolean isKilled(){
        return this.killed;
    }

}
