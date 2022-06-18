package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.*;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.cli.page.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * Class that represents the cli for the game
 */
public class Cli implements ViewHandler {
    //TODO : non viene chiamato il metodo per settare la torre su un'isola / non viene aggiornata
    //TODO: il merge delle isole lo deve decidere Grazza
    //TODO : capita ogni tanto modelErrorException lato server action Phase 1 riga 100
    private final String os;
    private ClientController controller;
    private boolean pageHasChanged;
    private Page currentPage;
    private final Object pageLock = new Object();
    private final Scanner scanner;
    private Game game;
    private AsciiArchipelago archipelago;
    private ArrayList<AsciiDashBoard> dashBoards;
    private ArrayList<AsciiCloud> clouds;

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
        this.game = controller.getGame();
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
            try {
                this.pageLock.wait(1000);
            } catch (InterruptedException e) {
                this.controller.handleError("Error while changing page");
            }
            this.clearConsole();
            this.currentPage = page;
            this.pageHasChanged = true;
        }
    }

    public int readInt(int range,Menù menù, boolean goBack) throws UndoException{
        return this.readInt(1,range,true,goBack,menù,null);
    }

    public int readInt(int min,int max,String string) throws UndoException{
        return this.readInt(min,max,false,false,null,string);
    }

    private int readInt(int min,int max,boolean isMenù,boolean goBack, Menù menù,String string) throws UndoException{
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
            if(result == max && isMenù && goBack){
                this.scanner.nextLine();
                throw new UndoException();
            }
        }catch (NoSuchElementException e){
            this.printChoiceError("The input is not a number or the input is out of bound");
            this.scanner.nextLine();
            result = this.readInt(min,max,isMenù,goBack,menù,string);
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

    public ClientController getController(){
        return this.controller;
    }
    /**
     * Method that returns the assistant card the player chooses (?)
     * @param cards represents the arrayList of possible cards
     * @return the chosen assistant card
     */
    @Override
    public AssistantCard selectCard(ArrayList<AssistantCard> cards) {
        Page p = new SelectAssistantCardPage(this, cards, game);
        this.changePage(p);
        while (!p.isReadyToProceed()) {
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    this.controller.handleError("Could not wait for user to choose a card");
                }
            }
        }
        return this.game.getSelf().getCurrentSelection();
    }

    /**
     * Method that returns the student the player wants to move
     * @return the chosen student
     */
    @Override
    public Student chooseStudentToMove() {
        this.moveStudent();
        return game.getSelf().getDashBoard().getWaitingRoom().stream().filter(x -> x.getColor().equals(this.game.getChosenColor())).findFirst().get();
    }

    /**
     * Method that returns the place the player wants to move a student
     * @return the chosen place on the dashboard
     */
    @Override
    public int choosePlace() {
        int place = 0;
        if(this.game.getChosenIsland() != null) {
            for (Island island : game.getIslands()) {
                if (island.getId() == this.game.getChosenIsland().getId())
                    place = game.getIslands().indexOf(island) + 1;
            }
            this.game.setChosenIsland(null);
        }
        return place;

    }

    private void moveStudent() {
        Page p = new MoveStudentPage(this, game);
        this.changePage(p);
        while (!p.isReadyToProceed()) {
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    this.controller.handleError("Could not wait for user to choose the move to play");
                }
            }
        }
    }

    /**
     * Method that returns the island on which the player wants to move a student
     * @param islands represents the arrayList of possible islands
     * @return the chosen island
     */
    @Override
    public Island chooseIsland(ArrayList<Island> islands) {
        Page p = new MoveMotherNaturePage(this, game, islands);
        this.changePage(p);
        while (!p.isReadyToProceed()) {
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    this.controller.handleError("Could not wait for user to choose a deck");
                }
            }
        }
        return this.game.getMotherNaturePosition();
    }

    @Override
    public Cloud chooseCloud(ArrayList<Cloud> clouds) {
        Page p = new SelectCloudPage(this, game, clouds);
        this.changePage(p);
        while (!p.isReadyToProceed()) {
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    this.controller.handleError("Could not wait for user to choose a deck");
                }
            }
        }
        return this.game.getChosenCloud();
    }

    @Override
    public AssistantCardDeckFigures chooseFigure(ArrayList<AssistantCardDeckFigures> figures) {
        Page p = new SelectAssistantCardDeckPage(this, this.game.getSelf(), figures);
        this.changePage(p);
        while (!p.isReadyToProceed()) {
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    this.controller.handleError("Could not wait for user to choose a deck");
                }
            }
        }
        return this.game.getSelf().getFigure();
    }

    @Override
    public void getPlayerInfo() {
        Page p = new LoginPage(this,this.controller.getGame());
        this.changePage(p);
        while(!p.isReadyToProceed()){
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
        Page p = new IdlePage(this,this.archipelago,this.clouds,this.dashBoards);
        this.changePage(p);
        while(!p.isReadyToProceed()){
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
    public void showEndGamePage(Results win) {

    }

    @Override
    public void lobbyFounded() {
        this.changePage(new LobbyFounded(this));
    }

    public void init(){
        ArrayList<AsciiIsland> islands = new ArrayList<AsciiIsland>();
        for(Island island : this.game.getIslands()){
            AsciiIsland asciiIsland = new AsciiIsland(island);
            islands.add(asciiIsland);
        }
        this.archipelago = new AsciiArchipelago(islands);
        this.clouds = new ArrayList<AsciiCloud>();
        for(Cloud cloud : this.game.getClouds()){
            this.clouds.add(new AsciiCloud(cloud));
        }
        this.dashBoards = new ArrayList<AsciiDashBoard>();
        this.dashBoards.add(new AsciiDashBoard(this,this.game.getSelf().getDashBoard()));
        for(Gamer gamer : this.game.getGamers()){
            if(!gamer.equals(this.game.getSelf())){
                this.dashBoards.add(new AsciiDashBoard(this,gamer.getDashBoard()));
            }
        }
    }

    @Override
    public void setMergedIsland(int islandId1, int islandId2) {
        this.archipelago.mergeIsland(islandId1,islandId2);
    }

    public void drawClouds(){
        for(int i = 0; i < AsciiCloud.getHeight(); i++){
            for(AsciiCloud cloud : this.clouds){
                int space = cloud.draw(i);
                this.printSpace(AsciiCloud.getWidth()-space);
                this.printSpace(AsciiCloud.getWidth()/2);
            }
            System.out.print("\n");
        }
    }

    public void drawDashboard(){
        for(int i = 0; i < AsciiDashBoard.getHeight(); i++){
            for(AsciiDashBoard dashBoard : this.dashBoards){
                dashBoard.draw(i);
                this.printSpace(5);
            }
            System.out.print("\n");
        }
    }

    public void printSpace(int number){
        for(int i = 0;i < number ; i++){
            System.out.print(" ");
        }
    }

    public void drawArchipelago(){
        try {
            this.archipelago.draw();
        } catch (AssetErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
