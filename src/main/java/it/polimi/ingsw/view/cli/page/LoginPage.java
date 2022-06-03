package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.controller.server.GameType;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;
import it.polimi.ingsw.view.cli.Menù;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * Class that represents the login page
 */
public class LoginPage implements Page {
    private final Cli cli;
    private Menù menù;
    private Game game;
    private final Scanner scanner;
    private boolean readyToProcede = false;
    private boolean killed;

    /**
     * Class constructor
     * @param cli represents the cli associated to the game
     * @param game represents the game
     */
    public LoginPage(Cli cli, Game game){
        this.cli = cli;
        scanner = new Scanner(System.in);
        this.game = game;
        this.killed = false;
    }

    /**
     * Method that handles the login page
     */
    @Override
    public void handle() {
        Thread t = new Thread(()->{
            System.out.println(AnsiColor.GREEN +"Found a Server"+ AnsiColor.RESET);
            System.out.print("Please insert your nickName: ");
            String nick = scanner.nextLine();
            this.game.getSelf().setUsername(nick);
            ArrayList<String> opt = new ArrayList<>();
            opt.add("Normal");
            opt.add("Expert");
            Menù menù = new Menù(opt);
            menù.setContext("Do you want to play a Standard game or an Expert one: ");
            menù.print();
            boolean doNotProcede = true;
            int choice;
            while (doNotProcede){
                choice = scanner.nextInt();
                if(choice<1 || choice>opt.size()){
                    System.out.println(AnsiColor.RED+"No choice with that number");
                    System.out.println("Retry"+AnsiColor.RESET);
                    menù.print();
                }else {
                    switch (choice) {
                        case 1 -> this.game.setType(GameType.NORMAL);
                        case 2 -> this.game.setType(GameType.EXPERT);
                    }
                    doNotProcede = false;
                }
            }
            doNotProcede = true;
            while (doNotProcede){
                System.out.println("Insert th number of players you wish to play with");
                choice = scanner.nextInt();
                if(choice<2 || choice>3){
                    System.out.println(AnsiColor.RED+"Choices ranges from 2 to 3 players");
                    System.out.println("Retry"+AnsiColor.RESET);
                }else {
                    switch (choice) {
                        case 2 -> this.game.setLobbySize(2);
                        case 3 -> this.game.setLobbySize(3);
                    }
                    doNotProcede = false;
                }
            }
            cli.clearConsole();
            LoadingBar loadingBar = new LoadingBar(80);
            this.readyToProcede = true;
            //TODO: riguardare come usare l'attributo killed
            while(/*!this.isKilled()*/){
                System.out.println("Please wait until we reach the server");
                loadingBar.print();
                this.cli.clearConsole();
            }
            System.out.println(AnsiColor.GREEN+"Lobby has been founded"+AnsiColor.RESET);
            loadingBar = new LoadingBar(80);
            while (/*!this.isKilled()*/){
                System.out.println("Please wait until the game starts");
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
    public synchronized boolean isProcessReady() {
        if(!this.readyToProcede){
            return false;
        }else {
            this.readyToProcede = false;
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
}
