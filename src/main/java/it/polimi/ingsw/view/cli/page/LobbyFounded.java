package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;

public class LobbyFounded implements Page {
    private final LoadingBar loadingBar;
    private final Cli cli;
    private boolean killed;

    public LobbyFounded(Cli cli){
        this.cli = cli;
        loadingBar = new LoadingBar( 80);
        this.killed = false;
    }

    @Override
    public void handle() throws UndoException {
        Thread t = new Thread(() ->{
            while(!this.isKilled()){
                System.out.print("\n"+ AnsiColor.GREEN.toString()+"A LOOBY WAS FOUND"+AnsiColor.RESET.toString()+"\n");
                System.out.print("Please be patient while we initialize the game"+"\n"+"   ");
                loadingBar.print();
                //TODO rimuovere
                this.kill();
            }
        });
        t.start();
    }

    @Override
    public boolean isReadyToProceed() {
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
