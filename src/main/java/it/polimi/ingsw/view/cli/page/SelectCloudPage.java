package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectCloudPage implements Page {
    private ArrayList<Cloud> clouds;
    private Game game;
    private boolean killed;
    private boolean readyToProcede = false;
    private Scanner scanner;

    public SelectCloudPage(Game game, ArrayList<Cloud> clouds) {
        this.game = game;
        this.clouds = clouds;
        scanner = new Scanner(System.in);
        killed = false;
    }

    @Override
    public void handle() /*throws UndoException*/ {
        Thread t = new Thread(() -> {
            ArrayList<String> options = new ArrayList<>();
            for(Cloud cloud : this.clouds){
                options.add("Cloud " + cloud.getId());
            }
            boolean doNotProcede = true;
            int choice = 0;
            Menù menù = new Menù(options);
            menù.clear();
            menù.addOptions(options);
            menù.setContext("Which cloud do you want to choose?");
            menù.print();
            while (doNotProcede) {
                choice = scanner.nextInt();
                if(choice<1 || choice>options.size()){
                    System.out.println(AnsiColor.RED+"No choice with that number");
                    System.out.println("Retry"+AnsiColor.RESET);
                    menù.print();
                } else {
                    game.setChosenCloud(clouds.get(choice-1));
                    doNotProcede = false;
                }
            }
        });
    }

    @Override
    public boolean isReadyToProceed() {
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
