package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectAssistantCardPage implements Page {
    private ArrayList<AssistantCard> cards;
    private boolean clearance = false;
    private boolean isProcessReady = false;
    private Scanner scanner;

    public SelectAssistantCardPage(ArrayList<AssistantCard> cards){
        this.cards = new ArrayList<AssistantCard>(cards);
        scanner = new Scanner(System.in);
    }

    @Override
    public void handle() {
        ArrayList<String> options = new ArrayList<String>();
        for(AssistantCard card : this.cards){
            options.add(card.getName()+" ("+card.getTurnValue()+", "+card.getMovement()+")");
        }
        Menù menù = new Menù(options);
        menù.setContext("Please select a card ");
        boolean doNotProcede = true;
        int choice = 0;
        while (doNotProcede){
            choice = scanner.nextInt();
            if(choice<1 || choice>options.size()){
                System.out.println(AnsiColor.RED+"No choice with that number");
                System.out.println("Retry"+AnsiColor.RESET);
                menù.print();
            }else {
                // TODO log choice to the asset
                doNotProcede = false;
                this.isProcessReady = true;
            }
        }
    }

    @Override
    public synchronized boolean isProcessReady() {
        return this.isProcessReady;
    }

    @Override
    public synchronized void setClearance(boolean clearance) {
        this.clearance = clearance;
    }

    private synchronized boolean getClearance(){
        return this.clearance;
    }
}
