package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Cloud;
import it.polimi.ingsw.debug.Gamer;
import it.polimi.ingsw.model.pawn.*;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This class is used to manage most of the game's mechanics
 * @author Luca Muroni
 */
public class Game {
    private MotherNature motherNature;
    private ArrayList<Cloud> clouds;
    private ArrayList<Professor> profs;
    private ArrayList<Island> islands;
    private Bag bag;
    private ArrayList<Gamer> gamers;
    private Gamer currentPlayer;
    private int turnNumber;

    /**
     * Class constructor
     * @param gamers is the ArrayList of the players connected in this game
     */
    public Game (ArrayList<Gamer> gamers){
        this.gamers = new ArrayList<>(gamers);

        //this.motherNature = new MotherNature(//TODO: manca come scegliere la prima isola casuale);

        this.clouds = new ArrayList<Cloud>();
        int numClouds = 0;
        while (numClouds<gamers.size()){
            clouds.add(new Cloud());
            numClouds++;
        }

        this.profs = new ArrayList<Professor>();
        for (PawnColor color : PawnColor.values()){
            profs.add(new Professor(color));
        }

        this.islands = new ArrayList<Island>();
        int numIslands = 0;
        while(numIslands < 12){
            islands.add(new Island());
            numIslands++;
        }

        //this. currentPlayer = //TODO: manca un metodo con cui scegliere casualmente il primo giocatore all'inizio della partita

        this.turnNumber = 1;
    }

    /**
     * This function is used to fill the clouds choose by the gamers at the end of the round
     * @param students is the ArrayList of students drawn by the controller from bag
     * @param cloud represent the cloud filled
     */
    private void fillClouds (ArrayList<Student> students, Cloud cloud){
        cloud.pushStudents(students);
    }

    /**
     * This method is called at the start of the game when all the islands, except for the MotherNature's one end its opposite,
     * need a student
     * @param students is the ArrayList of students, drawn from bag, that will be used to fill the islands
     */
    public void initIsland (ArrayList<Student> students){
        int motherNatureIsland = islands.indexOf(getMotherNature().getPlace());
        int oppositeIsland = (motherNatureIsland+6)%12;
        int cont = 0;
        for (Island island : this.islands){
            if(!island.equals(motherNatureIsland) && !island.equals(oppositeIsland)){
                island.addStudents(students.get(cont));
                cont ++;
            }
        }
    }

    /**
     * This method is called by the controller when the currentlayer moves MotherNature
     * @param island is the new Island where MotherNature will be moved on
     */
    public void moveMotherNature (Island island){
        this.motherNature.setPlace(island);
    }

    /**
     * This method is called by the controller to show all the islands where the student can move MotherNature (it depends on the
     * AssistantCard choose by him)
     * @return an ArrayList of the only possible islands that the player can choose
     */
    public ArrayList<Island> getMotherNatureDestination (){
        //TODO: necessario prima portare AssistantCard e Gamer
        return null;
    }

    /**
     * This method is called every time the currentPlayer moves a student from his WaitingRoom to one of the tables of his Hall
     * @param owner is the new owner of the professor (e.g. the currentPlayer)
     * @param color is the color of the Professor that will be changed his owner
     */
    public void changeProfessorOwner (Gamer owner, PawnColor color){
        for (Professor prof : this.profs){
            if(prof.getColor().equals(color)){
                prof.setOwner(owner);
            }
        }
    }

    /**
     * This method is called when the currentPlayer move a student from his WaitingRoom to an Island
     * @return the owner of the Island
     */
    public Optional<Gamer> checkIslandOwner (){
        //prendo l'isola da controllare
        Island islandToCheck = this.motherNature.getPlace();
        //creo un ArrayList con i colori dei prof posseduti dal currentPlayer
        ArrayList<PawnColor> colors = new ArrayList<PawnColor>();
        //cerco i profs posseduti dal currentPlayer
        for (Professor professor : this.profs){
            if (professor.getOwner().equals(currentPlayer)){ //TODO: Metodo da modificare: l'equals dovrebbe controllare il token
                colors.add(professor.getColor());
            }
        }
        //calcolo l'influenza del currentPlayer sull'isola
        int influence = islandToCheck.getInfluenceByColor(colors); //influenza del giocatore corrente

        //prendo l'owner corrente dell'isola
        Optional<Gamer> ownerIsland = islandToCheck.getOwner();
        //come in currentPlayer
        ArrayList<PawnColor> colorsOwner = new ArrayList<PawnColor>();
        //come in currentPlayer
        for (Professor professor : this.profs){
            if (professor.getOwner().equals(ownerIsland)){ //TODO: Metodo da modificare: l'equals dovrebbe controllare il token
                colorsOwner.add(professor.getColor());
            }
        }
        //calcolo influenza del giocatore possessore dell'isola
        int influenceOwner = islandToCheck.getInfluenceByColor(colorsOwner) + islandToCheck.getNumTowers();

        if(influenceOwner<influence){
            //return currentPlayer;
            //TODO: Problema: conflitto con l'uso di Optional<Gamer> anziché Gamer
            return ownerIsland;
        }
        else{
            return ownerIsland;
        }
    }

    /**
     * This method updates the players' order
     */
    public void updatePlayersOrder (){
        Gamer pastFirstPlayer = gamers.get(0);
        gamers.removeIf(gamer -> gamer.getToken()==pastFirstPlayer.getToken());
        gamers.add(pastFirstPlayer);
    }

    /**
     * Getter method
     * @return motherNature
     */
    public MotherNature getMotherNature() {
        return motherNature;
    }

    /**
     * Getter method
     * @return all the clouds
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * Getter method
     * @return all the profs
     */
    public ArrayList<Professor> getProfs() {
        return profs;
    }

    /**
     * Getter method
     * @return all the islands
     */
    public ArrayList<Island> getIslands() {
        return islands;
    }

    /**
     * Getter method
     * @return the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * Getter method
     * @return all the players
     */
    public ArrayList<Gamer> getGamers() {
        return gamers;
    }

    /**
     * Getter method
     * @return the current player
     */
    public Gamer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter method
     * @param currentPlayer is the new curretn player
     */
    public void setCurrentPlayer(Gamer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter method
     * @return the turn's number
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Setter method, it is called only after a player's turn ends
     */
    public void setTurnNumber() {
        this.turnNumber++;
    }
}
