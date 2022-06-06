package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Results;

public class EndGamePage implements Page {
    private Results result;
    private boolean readyToProceed;
    private boolean killed;

    public EndGamePage(Results result) {
        this.result = result;
        this.killed = false;
    }
    @Override
    public void handle() throws UndoException {
        switch (result) {
            case WIN -> System.out.println("Hai vinto la partita :)");
            case TIE -> System.out.println("Hai pareggiato :|");
            case LOSS -> System.out.println("Hai perso :(");
        }
        this.setReadyToProcede();
    }

    @Override
    public boolean isReadyToProceed() {
        if(!this.readyToProceed){
            return false;
        }else {
            this.readyToProceed = false;
            return true;
        }
    }

    private synchronized void setReadyToProcede(){
        this.readyToProceed = true;
    }

    @Override
    public synchronized void kill() {
        this.killed = true;
    }

    private synchronized boolean isKilled(){
        return this.killed;
    }
}
