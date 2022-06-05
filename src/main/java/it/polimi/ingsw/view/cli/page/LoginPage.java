package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;
import it.polimi.ingsw.view.cli.Menù;
import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * Class that represents the login page
 */
public class LoginPage implements Page {
    private final Cli cli;
    private Game game;
    private boolean readyToProceed = false;
    private boolean killed;

    /**
     * Class constructor
     * @param cli represents the cli associated to the game
     * @param game represents the game
     */
    public LoginPage(Cli cli, Game game){
        this.cli = cli;
        this.game = game;
        this.killed = false;
    }

    /**
     * Method that handles the login page
     * @throws UndoException to repeat the choice
     */
    @Override
    public void handle() throws UndoException{
        System.out.println(AnsiColor.GREEN +"Found a Server"+ AnsiColor.RESET);
        String nick = this.cli.readString("Please insert your nickName: ");
        this.game.getSelf().setUsername(nick);
        ArrayList<String> opt = new ArrayList<>();
        opt.add("Normal");
        opt.add("Expert");
        opt.add("Go back");
        Menù menù = new Menù(opt);
        menù.setContext("Do you want to play a Standard game or an Expert one: ");
        int choice;
        choice =  this.cli.readInt(opt.size(),menù,true);
        switch (choice){
            case 1 -> this.game.setType(GameType.NORMAL);
            case 2 -> this.game.setType(GameType.EXPERT);
        }
        choice = this.cli.readInt(2,3,"Insert th number of players you wish to play with (2/3):");
        switch (choice) {
            case 2 -> this.game.setLobbySize(2);
            case 3 -> this.game.setLobbySize(3);
        }
        opt.clear();
        opt.add("y");
        opt.add("n");
        String input = this.cli.readString("Are you satisfied with your selections (y/n): ",opt,true);
        if(input.equals("n")){
            throw new UndoException();
        }
        cli.clearConsole();
        this.setReadyToProcede();
        Thread t = new Thread(()->{
            LoadingBar loadingBar = new LoadingBar(80);
            while (!this.isKilled()){
                System.out.println("Please wait unit we reach the server");
                loadingBar.print();
                this.cli.clearConsole();
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
        if(!this.readyToProceed){
            return false;
        }else {
            this.readyToProceed = false;
            return true;
        }
    }

    @Override
    public synchronized void kill() {
        this.killed = true;
    }

    private synchronized boolean isKilled(){
        return this.killed;
    }

    private synchronized void setReadyToProcede(){
        this.readyToProceed = true;
    }
}
