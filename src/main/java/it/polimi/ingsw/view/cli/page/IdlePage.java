package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.cli.AsciiArchipelago;
import it.polimi.ingsw.view.cli.Cli;

import java.util.Date;

/**
 * @author Davide Grazzani
 * This class is used to show all game's objetcs (islands, clouds, ...) while a player is waiting his turn to play
 */
public class IdlePage implements Page {
    private boolean killed = false;
    private final AsciiArchipelago archipelago;
    private final Cli cli;
    private String popUp;
    private boolean popUpSettled;
    private final Object popUpLock = new Object();
    private long popUpTime;
    private boolean show;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param archipelago is the archipelago of merged islands
     */
    public IdlePage(Cli cli, AsciiArchipelago archipelago){
        this.cli = cli;
        this.archipelago = archipelago;
        this.killed = false;
        this.popUp = "";
        this.popUpSettled = false;
        this.show = true;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException {
        Thread t = new Thread(()->{
            int i = 0;
            while(!this.isKilled()){
                if(getShowStatus()){
                    setShow(false);
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
                        long currentTime = System.currentTimeMillis();
                        if(currentTime-this.popUpTime > 30000){
                            setPopUpOff();
                        }
                    }
                }
                synchronized (this){
                    try {
                        this.wait(1000);
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

    public void setPopUp(String event){
        synchronized (popUpLock){
            this.popUpTime = System.currentTimeMillis();
            this.popUp = event;
            this.popUpSettled = true;
        }
    }

    /**
     * Method used to check if a pop-up is currently present in the view
     * @return true if present, false otherwise
     */
    private boolean getPopUpPresence(){
        boolean result;
        synchronized (popUpLock){
            result = popUpSettled;
        }
        return result;
    }

    /**
     * Method used to stop showing a pop-up
     */
    private void setPopUpOff(){
        synchronized (popUpLock){
            this.popUpSettled = false;
        }
    }

    public synchronized void setShow(boolean show) {
        this.show = show;
    }

    private synchronized boolean getShowStatus(){
        return this.show;
    }
}