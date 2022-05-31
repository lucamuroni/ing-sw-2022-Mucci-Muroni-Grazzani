package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Menù;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * Class that represents the assistant card page
 */
public class SelectAssistantCardPage implements Page {
    private ArrayList<AssistantCard> cards;

    private Gamer self;
    private boolean clearance = false;
    private boolean isProcessReady = false;
    private Scanner scanner;

    /**
     * Class constructor
     * @param cards represents the arrayList of possible cards the player can choose from
     */
    public SelectAssistantCardPage(ArrayList<AssistantCard> cards, Gamer self){
        this.cards = new ArrayList<>(cards);
        scanner = new Scanner(System.in);
        this.self = self;
    }

    /**
     * Method that handles the page
     */
    @Override
    public void handle() {
        ArrayList<String> options = new ArrayList<>();
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
                self.setCurrentSelection(this.cards.get(choice-1));
                doNotProcede = false;
                synchronized (this){
                    this.isProcessReady = true;
                }
            }
        }
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
    public void kill() {

    }
}