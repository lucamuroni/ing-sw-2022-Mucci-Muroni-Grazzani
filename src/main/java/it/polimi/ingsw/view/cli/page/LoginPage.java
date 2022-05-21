package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.LoadingBar;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginPage implements Page {
    private Cli cli;
    private Menù menù;
    private Scanner scanner;
    private boolean readyToProcede = false;

    public LoginPage(Cli cli){
        this.cli = cli;
        scanner = new Scanner(System.in);
    }


    @Override
    public void handle() {
        Thread t = new Thread(()->{
            System.out.println(AnsiColor.GREEN.toString()+"Found a Server"+AnsiColor.RESET.toString());
            System.out.print("Please insert your nickName: ");
            String nick = scanner.nextLine();
            //TODO : aggiornare il modello nel client per il nickname dell'utente
            ArrayList<String> opt = new ArrayList<String>();
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
                    // TODO log choice to the asset
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
            // TODO aggiungere metodo di clearance per continuare l'esecuzione e gestire metodo di readyto procede e rimuovere kill()
            this.readyToProcede = true;
            while (doNotProcede){
                System.out.println("Please wait unit we reach the server");
                loadingBar.print();
            }

        });
        t.start();
    }

    @Override
    public void kill() {

    }

    @Override
    public boolean readyToProcede() {
        if(!this.readyToProcede){
            return false;
        }else {
            this.readyToProcede = false;
            return true;
        }
    }
}
