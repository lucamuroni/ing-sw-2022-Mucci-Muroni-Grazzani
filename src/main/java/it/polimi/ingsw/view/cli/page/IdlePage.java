package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;

public class IdlePage implements Page {
    private boolean clearance = false;
    private boolean readyToProcede = false;


    @Override
    public void handle() throws UndoException {
        Thread t = new Thread(()->{

        });
        t.start();
        }
    }

    @Override
    public boolean isProcessReady() {
        if(!this.readyToProcede){
            return false;
        }else {
            this.readyToProcede = false;
            return true;
        }
    }

    @Override
    public void setClearance(boolean clearance) {
        this.clearance = clearance;
    }
}
