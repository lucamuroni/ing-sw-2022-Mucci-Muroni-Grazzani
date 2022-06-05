package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Menù;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * Class that represents the page to decide where to move a student (?)
 */
public class MoveStudentPage implements Page {
    private boolean killed;
    private boolean isProcessReady = false;
    private Scanner scanner;
    private Game assetGame;

    /**
     * Class constructor
     */
    public MoveStudentPage(Game assetGame){
        this.scanner = new Scanner(System.in);
        this.assetGame = assetGame;
        this.killed = false;
    }

    /**
     * Method that handles the page
     * @throws UndoException launched if the player returns back to the possible choices (?)
     */
    @Override
    public void handle() /*throws UndoException*/ {
        Thread t = new Thread(()->{
            ArrayList<String> options = new ArrayList<>();
            options.add("Hall");
            options.add("Island");
            //options.add("Back");
            Menù menù= new Menù(options);
            menù.setContext("Where do you want to move your player?");
            menù.print();
            boolean doNotProcede = true;
            int choice = 0;
            while (doNotProcede){
                choice = scanner.nextInt();
                if(choice<1 || choice>options.size()){
                    System.out.println(AnsiColor.RED+"No choice with that number");
                    System.out.println("Retry"+AnsiColor.RESET);
                    menù.print();
                } //else if(choice == 3){
                    //throw new UndoException();
                else {
                    doNotProcede = false;
                }
            }
            if(choice==2){
                doNotProcede = true;
                while(doNotProcede){
                    options.clear();
                    for(Island island : this.assetGame.getIslands()){
                        options.add("Island " + island.getId());
                    }
                    //options.add("Back");
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
                        }//else if (choice == options.size()){
                            //throw new UndoException();
                        else {
                            assetGame.setChosenIsland(this.assetGame.getIslands().get(choice-1));
                            doNotProcede = false;
                        }
                    }
                }
            }
            options.clear();
            options.add("Red");
            options.add("Blue");
            options.add("Yellow");
            options.add("Green");
            options.add("Pink");
            //options.add("Back");
            menù.clear();
            menù.addOptions(options);
            menù.setContext("Which type of student do you want to move?");
            menù.print();
            doNotProcede = true;
            while (doNotProcede) {
                choice = scanner.nextInt();
                if(choice<1 || choice>options.size()){
                    System.out.println(AnsiColor.RED+"No choice with that number");
                    System.out.println("Retry"+AnsiColor.RESET);
                    menù.print();
                }//else if (choice == 6){
                    //throw new UndoException();
                else {
                    switch (choice) {
                        case 1 -> assetGame.setChosenColor(PawnColor.RED);
                        case 2 -> assetGame.setChosenColor(PawnColor.BLUE);
                        case 3 -> assetGame.setChosenColor(PawnColor.YELLOW);
                        case 4 -> assetGame.setChosenColor(PawnColor.GREEN);
                        case 5 -> assetGame.setChosenColor(PawnColor.PINK);
                    }
                    doNotProcede = false;
                }
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
        if(isProcessReady){
            isProcessReady = false;
            return true;
        }else{
            return false;
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
