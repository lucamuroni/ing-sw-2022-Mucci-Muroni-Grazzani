package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.ExpertGame;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginPage implements Page {
    private final Cli cli;
    private Menù menù;
    private final Scanner scanner;
    private boolean readyToProcede = false;
    private boolean clearance = false;
    private final int id;

    public LoginPage(Cli cli, int id){
        this.cli = cli;
        scanner = new Scanner(System.in);
        this.id = id;
    }


    @Override
    public void handle() {
        Thread t = new Thread(()->{
            System.out.println(AnsiColor.GREEN +"Found a Server"+ AnsiColor.RESET);
            System.out.print("Please insert your nickName: ");
            String nick = scanner.nextLine();
            Gamer player = new Gamer(id, nick);
            //TODO : aggiornare il modello nel client per il nickname dell'utente
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
                    if (choice == 1) {
                        //TODO: deve il controller con un metodo, ad esempio
                        Game game = new Game(player);
                    } else {
                        ExpertGame game = new ExpertGame(player);
                    }

                    doNotProcede = false;
                }
            }
            doNotProcede = true;
            while (doNotProcede){
                System.out.println("Insert th number of players you wish to play with");
                choice = scanner.nextInt();
                if(choice<2 || choice>3){
                    System.out.println(AnsiColor.RED+"Choiches ranges from 2 to 3 players");
                    System.out.println("Retry"+AnsiColor.RESET);
                }else {
                    // TODO log choice to the asset
                    doNotProcede = false;
                }
            }
            opt.clear();
            opt.add("join");
            opt.add("create");
            menù.clear();
            menù.addOptions(opt);
            menù.setContext("Do you wish to join or partecipate ?");
            menù.print();
            doNotProcede = true;
            while (doNotProcede){
                choice = scanner.nextInt();
                if(choice<1 || choice>opt.size()){
                    System.out.println(AnsiColor.RED+"No choice with that number");
                    System.out.println("Retry"+AnsiColor.RESET);
                    menù.print();
                }else {
                    // TODO log choice to the asset
                    doNotProcede = false;
                }
            }
            cli.clearConsole();
            LoadingBar loadingBar = new LoadingBar(80);
            this.readyToProcede = true;
            while (!this.getClearance()){
                System.out.println("Please wait unit we reach the server");
                loadingBar.print();
                this.cli.clearConsole();
            }
            System.out.println(AnsiColor.GREEN+"Lobby has been founded"+AnsiColor.RESET);
            loadingBar = new LoadingBar(80);
            while (!this.getClearance()){
                System.out.println("Please wait unit the game stars");
                loadingBar.print();
                this.cli.clearConsole();
            }
        });
        t.start();
    }


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
    public synchronized void setClearance(boolean clearance) {
        this.clearance = clearance;
    }

    private synchronized boolean getClearance(){
        return this.clearance;
    }
}
