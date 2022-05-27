package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Island;
import it.polimi.ingsw.view.cli.page.LoadingPage;
import it.polimi.ingsw.view.cli.page.UndoException;

import java.io.IOException;
import java.util.ArrayList;

public class Cli implements ViewHandler {
    private final Game game;
    private final String os;
    private final ClientController controller;
    private boolean pageHasChanged;
    private Page currentPage;
    private final Object pageLock = new Object();
    public Cli(Game game, ClientController controller){
        this.game = game;
        this.controller = controller;
        this.os = System.getProperty("os.name");
        this.currentPage = new LoadingPage(this);
        this.pageHasChanged = false;
        this.start();
    }

    private void start(){
        Thread t = new Thread(()->{
            while (this.controller.isRunning()){
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

    public void changePage(Page page){
        synchronized (this.pageLock){
            this.clearConsole();
            this.currentPage = page;
            this.pageHasChanged = true;
        }
    }

    @Override
    public AssistantCard selectCard(ArrayList<AssistantCard> cards) {
        this.changePage();
    }

    @Override
    public Student chooseStudentToMove() {
        return null;
    }

    @Override
    public String choosePlace() {
        return null;
    }

    @Override
    public Island chooseIsland(ArrayList<Island> islands) {
        return null;
    }
}
