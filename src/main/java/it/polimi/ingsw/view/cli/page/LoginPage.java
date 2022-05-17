package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.Scanner;

public class LoginPage implements Page {
    private Cli cli;
    private Menù menù;
    Scanner scanner;

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
            System.out.print("Do you want to play a Standard game or an Expert one: ");
        });
        t.start();
    }

    @Override
    public void kill() {

    }
}
