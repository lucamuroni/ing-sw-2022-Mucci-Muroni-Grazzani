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
 * Class that represents the login page
 */
public class LoginPage implements Page {
    private final Cli cli;
    private final Game game;
    private boolean readyToProceed = false;
    private boolean killed;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param game is the current game
     */
    public LoginPage(Cli cli, Game game){
        this.cli = cli;
        this.game = game;
        this.killed = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException{
        System.out.println(AnsiColor.GREEN +"Found a Server"+ AnsiColor.RESET);
        String nick = this.cli.readString("Please insert your nickName: ");
        if(nick.length()>=13){
            System.out.println(AnsiColor.RED+"Username is to long ; please retry"+AnsiColor.RESET);
            throw new UndoException();
        }
        this.game.getSelf().setUsername(nick, true);
        ArrayList<String> opt = new ArrayList<>();
        opt.add("Normal");
        opt.add("Expert");
        opt.add("Go back");
        Menù menù = new Menù(opt);
        menù.setContext("Do you want to play a Standard game or an Expert one: ");
        int choice =  this.cli.readInt(opt.size(),menù,true);
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
                synchronized (this){
                    try {
                        this.wait(100);
                    } catch (InterruptedException e) {}
                }
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

    /**
     * Method used to set that the page has completed its task
     */
    private synchronized void setReadyToProcede(){
        this.readyToProceed = true;
    }
}