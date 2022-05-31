package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;

/**
 * @author Luca Muroni
 * Class that represents the idlePage. an idlePage is
 */
public class IdlePage implements Page {
    private boolean clearance = false;
    private boolean readyToProcede = false;


    @Override
    public void handle() throws UndoException {
        Thread t = new Thread(()->{

        });
        t.start();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public boolean isProcessReady() {
        if(!this.readyToProcede){
            return false;
        }else {
            this.readyToProcede = false;
            return true;
        }
    }

    /**
     * Setter method
     * @param clearance represents
     */
    @Override
    public void setClearance(boolean clearance) {
        this.clearance = clearance;
    }
}
