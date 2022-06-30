package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.cli.AsciiArchipelago;
import it.polimi.ingsw.view.cli.Cli;

/**
 * @author Luca Muroni
 * Class that represents the idlePage. an idlePage is
 */
public class IdlePage implements Page {
    private boolean killed = false;
    private final AsciiArchipelago archipelago;
    private final Cli cli;
    private String popUp;
    private boolean popUpSettled;
    private final Object popUpLock = new Object();

    public IdlePage(Cli cli, AsciiArchipelago archipelago){
        this.cli = cli;
        this.archipelago = archipelago;
        this.killed = false;
        this.popUp = "";
        this.popUpSettled = false;
    }

    @Override
    public void handle() throws UndoException {
        Thread t = new Thread(()->{
            int i = 0;
            while(!this.isKilled()){
                if(i%5==0){
                    this.cli.clearConsole();
                    try {
                        this.archipelago.draw();
                    } catch (AssetErrorException e) {
                        this.cli.getController().handleError("Could not print archipelago");
                    }
                    System.out.print("\n");
                    this.cli.drawCloudsAndCards();
                    System.out.print("\n");
                    this.cli.drawDashboard();
                    System.out.print("\n");
                    if(getPopUpPresence()){
                        System.out.println("Event : "+this.popUp);
                        if(i%60 == 0){
                            setPopUpOff();
                        }
                    }
                }
                synchronized (this){
                    try {
                        this.wait(500);
                    } catch (InterruptedException e) {}
                }
                i++;
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

    public void setPopUp(String event){
        synchronized (popUpLock){
            this.popUp = event;
            this.popUpSettled = true;
        }
    }

    private boolean getPopUpPresence(){
        boolean result;
        synchronized (popUpLock){
            result = popUpSettled;
        }
        return result;
    }

    private void setPopUpOff(){
        synchronized (popUpLock){
            this.popUpSettled = false;
        }
    }

}
