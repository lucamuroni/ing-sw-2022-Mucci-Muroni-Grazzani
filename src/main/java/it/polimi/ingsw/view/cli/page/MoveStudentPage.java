package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Menù;ù
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * Class that represents the page to decide where to move a student (?)
 */
public class MoveStudentPage implements Page {
    private boolean clearance = false;
    private boolean isProcessReady = false;
    private Scanner scanner;

    /**
     * Class constructor
     */
    public MoveStudentPage(){
        this.scanner = new Scanner(System.in);
    }

    /**
     * Method that handles the page
     * @throws UndoException launched if the player returns back to the possible choices (?)
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Hall");
        options.add("Island");
        options.add("Back");
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
            } else if(choice == 3){
                throw new UndoException();
            }else {
                doNotProcede = false;
            }
        }
        if(choice==2){
            doNotProcede = true;
            while(doNotProcede){
                options.clear();
                options.add("island 1");
                options.add("island 2");
                options.add("island 3");
                options.add("island 4");
                options.add("island 5");
                options.add("island 6");
                options.add("island 7");
                options.add("island 8");
                options.add("island 9");
                options.add("island 10");
                options.add("island 11");
                options.add("island 12");
                options.add("Back");
                menù.clear();
                menù.addOptions(options);
                menù.setContext("Which island do you want to choose ?");
                menù.print();
                doNotProcede = true;
                while (doNotProcede) {
                    choice = scanner.nextInt();
                    if(choice<1 || choice>options.size()){
                        System.out.println(AnsiColor.RED+"No choice with that number");
                        System.out.println("Retry"+AnsiColor.RESET);
                        menù.print();
                    }else if (choice == 13){
                        throw new UndoException();

                    }else {
                        //TODO: controller salva info
                        doNotProcede = false;
                    }
                }
            }
        }
        options.clear();
        //TODO: choose a student

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

    /**
     * Setter method
     * @param clearance represents
     */
    @Override
    public synchronized void setClearance(boolean clearance) {
        this.clearance = clearance;
    }

    /**
     * Getter method
     * @return the
     */
    private synchronized boolean getClearance(){
        return this.clearance;
    }
}
