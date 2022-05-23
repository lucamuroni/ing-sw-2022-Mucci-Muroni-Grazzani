package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveStudentPage implements Page {
    private boolean clearance = false;
    private boolean isProcessReady = false;
    private Scanner scanner;

    public MoveStudentPage(){
        this.scanner = new Scanner(System.in);
    }
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
                // TODO : select an island and populate it
            }
        }
    }

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
    public synchronized void setClearance(boolean clearance) {
        this.clearance = clearance;
    }

    private synchronized boolean getClearance(){
        return this.clearance;
    }
}
