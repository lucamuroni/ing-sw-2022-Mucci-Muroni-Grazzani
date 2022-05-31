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
import it.polimi.ingsw.view.cli.page.UndoException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * Class that represents the cli for the game
 */
public class Cli implements ViewHandler {
    private final Game game;
    private final String os;
    private final ClientController controller;
    private boolean pageHasChanged;
    private Page currentPage;
    private final Object pageLock = new Object();

    /**
     * Class constructor
     * @param game represents the current game
     * @param controller represents the controller associated to the game
     */
    public Cli(Game game, ClientController controller){
        this.game = game;
        this.controller = controller;
        this.os = System.getProperty("os.name");
        this.currentPage = new LoadingPage(this);
        this.pageHasChanged = false;
        this.start();
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
        try {
            if(os.contains("Windows")){
                Runtime.getRuntime().exec("cls");
            }else{
                Runtime.getRuntime().exec("clear");
            }
        }catch (IOException e){
            System.out.println(AnsiColor.RED.toString()+"OS error while trying to refresh cli interface"+AnsiColor.RESET.toString());
        }
    }

    /**
     * Method to change the shown page
     * @param page represents the new current page
     */
    public void changePage(Page page){
        synchronized (this.pageLock){
            this.clearConsole();
            this.currentPage = page;
            this.pageHasChanged = true;
        }
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

    }

    @Override
    public void goToIdle() {

    }

    @Override
    public void showEndGamePage(Results win) {

    }

}
