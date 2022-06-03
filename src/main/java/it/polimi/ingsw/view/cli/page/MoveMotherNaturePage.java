package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveMotherNaturePage implements Page {
    private Menù menù;
    private Game game;
    private ArrayList<Island> islands;
    private final Scanner scanner;
    private boolean readyToProcede = false;
    private boolean killed;

    public MoveMotherNaturePage(Game game, ArrayList<Island> islands) {
        this.game = game;
        scanner = new Scanner(System.in);
        this.killed = false;
        this.islands = islands;
    }

    @Override
    public void handle() throws UndoException {
        Thread t = new Thread(() -> {
            ArrayList<String> options = new ArrayList<>();
            for (Island island : this.islands) {
                options.add("Island " + island.getId());
            }
            boolean doNotProcede = true;
            int choice = 0;
            menù.clear();
            menù.addOptions(options);
            menù.setContext("Which island do you want to choose?");
            menù.print();
            while (doNotProcede) {
                choice = scanner.nextInt();
                if(choice<1 || choice>options.size()){
                    System.out.println(AnsiColor.RED+"No choice with that number");
                    System.out.println("Retry"+AnsiColor.RESET);
                    menù.print();
                } else {
                    game.setMotherNaturePosition(this.islands.get(choice-1));
                    doNotProcede = false;
                }
            }
        });
        t.start();
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
    public synchronized void kill() {
        this.killed = true;
    }

    private synchronized boolean isKilled(){
        return this.killed;
    }
}
