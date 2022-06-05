package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.view.asset.game.Results;
import it.polimi.ingsw.view.cli.page.LoadingPage;
import it.polimi.ingsw.view.cli.page.LobbyFounded;
import it.polimi.ingsw.view.cli.page.LoginPage;
import it.polimi.ingsw.view.cli.page.UndoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * Class that represents the cli for the game
 */
public class Cli implements ViewHandler {
    private final String os;
    private ClientController controller;
    private boolean pageHasChanged;
    private Page currentPage;
    private final Object pageLock = new Object();
    private final Scanner scanner;

    /**
     * Class constructor
     */
    public Cli(){
        this.os = System.getProperty("os.name");
        this.currentPage = new LoadingPage(this);
        this.pageHasChanged = true;
        this.scanner = new Scanner(System.in);
        this.start();
    }

    public void setController(ClientController controller){
        this.controller = controller;
    }

    /**
     * Method that starts the cli
     */
    private void start(){
        Thread t = new Thread(()->{
            while (true){
                synchronized (this.pageLock){
                    if(this.pageHasChanged){
                        try {
                            currentPage.handle();
                            this.pageHasChanged = false;
                        } catch (UndoException e) {
                            this.pageHasChanged = true;
                            this.start();
                        }
                    }
                }
            }
        });
        t.start();
    }

    /**
     * Method that clears the cli
     */
    public void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Method to change the shown page
     * @param page represents the new current page
     */
    public void changePage(Page page){
        synchronized (this.pageLock){
            this.currentPage.kill();
            this.clearConsole();
            this.currentPage = page;
            this.pageHasChanged = true;
        }
    }

    public int readInt(int range,Menù menù) throws UndoException{
        return this.readInt(1,range,true,menù,null);
    }

    public int readInt(int min,int max,String string) throws UndoException{
        return this.readInt(min,max,false,null,string);
    }

    private int readInt(int min,int max,boolean isMenù, Menù menù,String string) throws UndoException{
        int result = 0;
        if(isMenù){
            menù.print();
        }else{
            System.out.print(string);
        }
        try{
            result = scanner.nextInt();
            if(result> max || result < min){
                throw new NoSuchElementException("No element with such index");
            }
            if(result == max && isMenù){
                this.scanner.nextLine();
                throw new UndoException();
            }
        }catch (NoSuchElementException e){
            this.printChoiceError("The input is not a number or the input is out of bound");
            this.scanner.nextLine();
            result = this.readInt(min,max,isMenù,menù,string);
            return result;
        }catch (IllegalStateException e){
            this.controller.handleError("No input stream was found");
        }
        this.scanner.nextLine();
        return result;
    }

    public String readString(String string) throws UndoException{
        ArrayList<String> empty = new ArrayList<>();
        return this.readString(string,empty,true);
    }
    public String readString(String string,ArrayList<String> options, boolean inclusivity) throws UndoException{
        String result = "";
        System.out.print(string);
        try{
            result = scanner.nextLine();
            if(result.equals("")){
                throw new NoSuchElementException("invalid input");
            }
            if(options.size() != 0){
                if(inclusivity){
                    if(!options.contains(result)){
                        throw new NoSuchElementException("invalid input");
                    }
                }else{
                    if(options.contains(result)){
                        throw new NoSuchElementException("invalid input");
                    }
                }
            }
            if(result.equals("b")){
                throw new UndoException();
            }
        }catch (NoSuchElementException e){
            this.printChoiceError("The input is not a number or the input is out of bound");
            result = this.readString(string,options,inclusivity);
            return result;
        }catch (IllegalStateException e){
            this.controller.handleError("No input stream was found");
        }
        return result;
    }

    private void printChoiceError(String s){
        System.out.print("\n");
        System.out.println(AnsiColor.RED+s);
        System.out.println("Please retry"+AnsiColor.RESET);
        System.out.print("\n");
    }
    /**
     * Method that returns the assistant card the player chooses (?)
     * @param cards represents the arrayList of possible cards
     * @return the chosen assistant card
     */
    @Override
    public AssistantCard selectCard(ArrayList<AssistantCard> cards) {
        return AssistantCard.CAT;
    }

    /**
     * Method that returns the student the player wants to move
     * @return the chosen student
     */
    @Override
    public Student chooseStudentToMove() {
        return null;
    }

    /**
     * Method that returns the place the player wants to move a student
     * @return the chosen place on the dashboard
     */
    @Override
    public int choosePlace() {
        return 0;
    }

    /**
     * Method that returns the island on which the player wants to move a student
     * @param islands represents the arrayList of possible islands
     * @return the chosend island
     */
    @Override
    public Island chooseIsland(ArrayList<Island> islands) {
        return null;
    }

    @Override
    public Cloud chooseCloud(ArrayList<Cloud> clouds) {
        return null;
    }

    @Override
    public AssistantCardDeckFigures chooseFigure(ArrayList<AssistantCardDeckFigures> figures) {
        return null;
    }

    @Override
    public void getPlayerInfo() {
        Page p = new LoginPage(this,this.controller.getGame());
        this.changePage(p);
        while(!p.isProcessReady()){
            synchronized (this){
                try{
                    this.wait(100);
                }catch(InterruptedException e){
                    this.controller.handleError("Could not wait for user to complete registration");
                }
            }
        }
    }

    @Override
    public void goToIdle() {

    }

    @Override
    public void showEndGamePage(Results win) {

    }

    @Override
    public void lobbyFounded() {
        this.changePage(new LobbyFounded(this));
    }
}
