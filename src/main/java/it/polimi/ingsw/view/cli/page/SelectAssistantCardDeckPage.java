package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectAssistantCardDeckPage implements Page {
    private ArrayList<AssistantCardDeckFigures> figures;
    private Gamer self;
    private boolean killed;
    private boolean isProcessReady = false;
    private Scanner scanner;
    
    public SelectAssistantCardDeckPage(Gamer self, ArrayList<AssistantCardDeckFigures> figures) {
        this.self = self;
        this.figures = figures;
        this.killed = false;
        scanner = new Scanner(System.in);
    }
    
    @Override
    public void handle() /*throws UndoException*/ {
        Thread t = new Thread(() -> {
            ArrayList<String> options = new ArrayList<>();
            for(AssistantCardDeckFigures figures : this.figures){
                options.add(figures.name());
            }
            Menù menù = new Menù(options);
            menù.setContext("Please select a deck ");
            boolean doNotProcede = true;
            int choice = 0;
            while (doNotProcede){
                choice = scanner.nextInt();
                if(choice<1 || choice>options.size()){
                    System.out.println(AnsiColor.RED+"No choice with that number");
                    System.out.println("Retry"+AnsiColor.RESET);
                    menù.print();
                }else {
                    self.setFigure(this.figures.get(choice-1));
                    doNotProcede = false;
                    synchronized (this){
                        this.isProcessReady = true;
                    }
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
