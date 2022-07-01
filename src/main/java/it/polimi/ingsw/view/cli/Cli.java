package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.*;
import it.polimi.ingsw.view.cli.page.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Davide Grazzani
 * Class that represents the cli for the game used to interact with the player
 */
public class Cli implements ViewHandler {
    private ClientController controller;
    private boolean pageHasChanged;
    private Page currentPage;
    private final Object pageLock = new Object();
    private final Scanner scanner;
    private Game game;
    private AsciiArchipelago archipelago;
    private ArrayList<AsciiDashBoard> dashBoards;
    private ArrayList<AsciiCloud> clouds;
    private ArrayList<AsciiAssistantCard> cards;

    /**
     * Constructor of the class
     */
    public Cli(){
        this.currentPage = new LoadingPage(this);
        this.pageHasChanged = true;
        this.scanner = new Scanner(System.in);
        this.start();
    }

    /**
     * Method used to set the controller of client side
     * @param controller is the controller of the client
     */
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
     * Method used to change the shown page
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

    /**
     * Method used to read an int input from keyboard
     * @param range is the max value that can be accepted
     * @param menù contains the list of possible options
     * @param goBack is true if the player can redo the choice, false otherwise
     * @return the choice of the player
     * @throws UndoException when the player wants to redo a choice
     */
    public int readInt(int range,Menù menù, boolean goBack) throws UndoException{
        return this.readInt(1,range,true,goBack,menù,null);
    }

    /**
     * Method used to read an int input from keyboard
     * @param min is the min value that can be accepted
     * @param max is the max value that can be accepted
     * @param string is the question asked the player
     * @return the choice of the player
     * @throws UndoException when the player wants to redo a choice
     */
    public int readInt(int min,int max,String string) throws UndoException{
        return this.readInt(min,max,false,false,null,string);
    }

    /**
     * Method used to read an int input from keyboard
     * @param min is the min value that can be accepted
     * @param max is the max value that can be accepted
     * @param isMenù is true if there are options to choose from, false if the player has to wright by his own the answer
     * @param goBack is true if is possible to redo a choice, false otherwise
     * @param menù contains the list of possible options
     * @param string is the question asked the player
     * @return the choice of the player
     * @throws UndoException when the player wants to redo a choice
     */
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

    /**
     * Method used to read a string input from keyboard
     * @param string is the question asked the player
     * @return the answer of the player
     * @throws UndoException when the player wants to redo a choice
     */
    public String readString(String string) throws UndoException{
        ArrayList<String> empty = new ArrayList<>();
        return this.readString(string,empty,true);
    }

    /**
     * Method used to read a string input from keyboard
     * @param string is the question asked the player
     * @param options are the possible options to chose from/that can't be chosen
     * @param inclusivity is true if options must be the possible choices, false otherwise
     * @return the answer of the player
     * @throws UndoException when the player wants to redo a choice
     */
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

    /**
     * Method used to advertise the player of a non-acceptable input
     * @param s is the message printed
     */
    private void printChoiceError(String s){
        System.out.print("\n");
        System.out.println(AnsiColor.RED+s);
        System.out.println("Please retry"+AnsiColor.RESET);
        System.out.print("\n");
    }

    /**
     * Getter method
     * @return the controller of the client
     */
    public ClientController getController(){
        return this.controller;
    }

    /**
     * Method that returns the assistantCard chosen by the player
     * @param cards represents the arrayList of possible cards
     * @return the chosen assistantCard
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
     * Method that returns the place where the player wants to move a student
     * @return the chosen place (dashboard: 0, island: 1-12)
     */
    @Override
    public int choosePlace() {
        int place = 0;
        if(this.game.getChosenIsland() != null) {
            place = this.game.getChosenIsland().getId();
            this.game.setChosenIsland(null);
        }
        return place;
    }

    /**
     * Private method that handles both the choice of which student to move and where to move it
     */
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
     * @param expert is true if this method is called by a characterCard, false if called by MotherNaturePhase
     * @return the chosen island
     */
    @Override
    public Island chooseIsland(ArrayList<Island> islands, boolean expert) {
        Page p = new MoveMotherNaturePage(this, game, islands, expert);
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
        if (expert)
            return this.game.getSelf().getSelectedIsland();
        return this.game.getMotherNaturePosition();
    }

    /**
     * Method that returns the cloud chosen by the player
     * @param clouds is the arrayList of possible clouds
     * @return the chosen cloud
     */
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

    /**
     * Method that returns the deck figure chosen by the player
     * @param figures is the arrayList of possible decks
     * @return the chosen deck
     */
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

    /**
     * Method used at the start of the application to ask the player nickname, which type of game he wants to play and
     * with how many players does he want to play with
     */
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

    /**
     * Method used by Idle to show the game's objects while the player is not playing
     */
    @Override
    public void goToIdle() {
        Page p = new IdlePage(this,this.archipelago);
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

    /**
     * Method that shows the result of the game
     * @param win contains the result for the player
     */
    @Override
    public void showEndGamePage(Results win) {
        Page p = new EndGamePage(this,win);
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

    /**
     * Method that inform the player a lobby has been founded
     */
    @Override
    public void lobbyFounded() {
        this.changePage(new LobbyFounded(this));
    }

    /**
     * Method used to init all the ascii objects (dashboards, islands, clouds and assistantCards)
     */
    public void init(){
        ArrayList<AsciiIsland> islands = new ArrayList<>();
        for(Island island : this.game.getIslands()){
            AsciiIsland asciiIsland = new AsciiIsland(island);
            islands.add(asciiIsland);
        }
        this.archipelago = new AsciiArchipelago(islands);
        this.clouds = new ArrayList<>();
        for(Cloud cloud : this.game.getClouds()){
            this.clouds.add(new AsciiCloud(cloud));
        }
        this.dashBoards = new ArrayList<>();
        this.dashBoards.add(new AsciiDashBoard(this,this.game.getSelf().getDashBoard()));
        for(Gamer gamer : this.game.getGamers()){
            if(!gamer.equals(this.game.getSelf())){
                this.dashBoards.add(new AsciiDashBoard(this,gamer.getDashBoard()));
            }
        }
        this.cards = new ArrayList<>();
        for(Gamer gamer : this.game.getGamers()){
            cards.add(new AsciiAssistantCard(gamer));
        }
    }

    /**
     * Method used to merge two islands in client's view
     * @param islandId1 is the first island that will merge
     * @param islandId2 is the second island that will merge
     */
    @Override
    public void setMergedIsland(int islandId1, int islandId2) {
        this.archipelago.mergeIsland(islandId1,islandId2);
    }

    /**
     * Method used to draw the clouds
     */
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

    /**
     * Method used to draw both clouds and cards
     */
    public void drawCloudsAndCards(){
        for(int i = 0; i < AsciiCloud.getHeight(); i++){
            for(AsciiCloud cloud : this.clouds){
                int space = cloud.draw(i);
                this.printSpace(AsciiCloud.getWidth()-space);
                this.printSpace(AsciiCloud.getWidth()/2);
            }
            for(AsciiAssistantCard card : this.cards){
                card.draw(i);
                this.printSpace(3);
            }
            System.out.print("\n");
        }
    }

    /**
     * Method used to draw the dashboards
     */
    public void drawDashboard(){
        for(int i = 0; i < AsciiDashBoard.getHeight(); i++){
            for(AsciiDashBoard dashBoard : this.dashBoards){
                dashBoard.draw(i);
                this.printSpace(5);
            }
            System.out.print("\n");
        }
    }

    /**
     * Method used to print space between objects
     * @param number is the number of spaces that must be printed
     */
    public void printSpace(int number){
        for(int i = 0;i < number ; i++){
            System.out.print(" ");
        }
    }

    /**
     * Method used to draw the islands
     */
    public void drawArchipelago(){
        try {
            this.archipelago.draw();
        } catch (AssetErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that returns if the player wants to play a characterCard or not
     * @return true if he wants to play a card, false otherwise
     */
    public boolean askToPlayExpertCard(){
        ExpertGameSelectionPage p = new ExpertGameSelectionPage(this);
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
        return p.getAnswer();
    }

    /**
     * Method that returns the characterCard chosen by the player
     * @param cards is the arrayList of possible cards
     * @return the chosen card
     */
    @Override
    public CharacterCard choseCharacterCard(ArrayList<CharacterCard> cards) {
        Page p = new CharacterCardPage(this,cards,this.game);
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
        return this.game.getSelf().getCurrentExpertCardSelection();
    }

    /**
     * Method used to ask the player which students to move due to the effect of a characterCard
     * @return the chosen students
     */
    @Override
    public ArrayList<PawnColor> choseStudentsToMove() {
        Page p = new SelectColorsPage(this, this.game);
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
        return this.game.getSelf().getSelectedColors();
    }

    /**
     * Method used to ask the player which color he wants to choose due to the effect of a characterCard
     * @param name is the name of the characterCard that has been played
     * @return the chosen color
     */
    public PawnColor chooseColor(String name) {
        Page p = new SelectColorPage(this, this.game, name);
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
        return this.game.getSelf().getSelectedColor();
    }

    /**
     * Method used to print a pop-up to inform the player of something
     * @param s is the message that will be printed
     */
    @Override
    public void popUp(String s) {
        IdlePage page = (IdlePage) currentPage;
        page.setPopUp(s);
    }

    /**
     * Method used to refresh Idle page when necessary
     */
    @Override
    public void idleShow() {
        IdlePage page = (IdlePage) this.currentPage;
        page.setShow(true);
    }
}