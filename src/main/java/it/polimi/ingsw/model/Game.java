package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;
import it.polimi.ingsw.model.pawn.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * This class is used to manage most of the game's mechanics
 * @author Luca Muroni
 * @author Davide Grazzani
 */
public class Game {
    private final MotherNature motherNature;
    private final ArrayList<Cloud> clouds;
    private final ArrayList<Professor> professors;
    private final ArrayList<Island> islands;
    private final Bag bag;
    private final ArrayList<Gamer> gamers;
    private Gamer currentPlayer;
    private int turnNumber;

    /**
     * Class constructor
     * @param gamers is the ArrayList of the players connected in this game
     */
    public Game (ArrayList<Gamer> gamers){
        this.gamers = new ArrayList<>(gamers);
        this.clouds = new ArrayList<Cloud>();
        for(Gamer gamer : this.gamers){
            this.clouds.add(new Cloud());
        }
        this.professors = new ArrayList<Professor>();
        for (PawnColor color : PawnColor.values()){
            professors.add(new Professor(color));
        }
        this.islands = new ArrayList<Island>();
        for(int numIslands=0; numIslands<12;numIslands++){
            this.islands.add(new Island());
        }
        Random random = new Random();
        this.motherNature = new MotherNature(this.islands.get(random.nextInt(this.islands.size())));
        this.bag = new Bag();
        initiatePlayersOrder();
        this.turnNumber = 0;
    }

    /**
     * Method used to initiate the order of players
     */
    private void initiatePlayersOrder(){
        ArrayList<Gamer> players = new ArrayList<>(this.gamers);
        this.gamers.clear();
        Random rand = new Random();
        currentPlayer = players.get(rand.nextInt(players.size()));
        this.gamers.add(currentPlayer);
        players.remove(currentPlayer);
        int size = players.size();
        for (int i = 0;i<size;i++){
            Gamer player = players.get(rand.nextInt(players.size()));
            this.gamers.add(player);
            players.remove(player);
        }
    }
    /**
     * This function is used to fill a cloud chosen by a gamer at the end of the round
     * @param students is the ArrayList of students drawn by the controller from bag
     * @param cloud represent the cloud filled
     */
    public void fillCloud(ArrayList<Student> students, Cloud cloud){
        cloud.pushStudents(students);
    }

    /**
     * This method is called at the start of the game when all the islands, except for the MotherNature's one and its opposite,
     * need a student
     * @param students is the ArrayList of students, drawn from bag, that will be used to fill the islands
     */
    public void initIsland (ArrayList<Student> students){
        int motherNatureIsland = this.islands.indexOf(this.motherNature.getPlace());
        int oppositeIsland = (motherNatureIsland+6)%12;
        for (Island island : this.islands){
            if(!island.equals(motherNatureIsland) && !island.equals(oppositeIsland)){
                island.addStudents(students.get(0));
                students.remove(0);
            }
        }
    }

    /**
     * This method is called by the controller when the currentPlayer moves MotherNature
     * @param island is the new Island where MotherNature will be moved on
     */
    public void moveMotherNature (Island island){
        this.motherNature.setPlace(island);
    }

    /**
     * This method is called by the controller to show all the islands where the student can move MotherNature (it depends on the
     * AssistantCard chosen by him)
     * @return an ArrayList of the only possible islands that the player can choose
     */
    public ArrayList<Island> getMotherNatureDestination (){
        ArrayList<Island> result = new ArrayList<Island>();
        int motherNatureIndex = this.islands.indexOf(this.motherNature.getPlace());
        int maxIndexMove = currentPlayer.getAssistantCardDeck().getCurrentSelection().getMovement();
        for(int i=0;i<maxIndexMove;i++){
            int index = motherNatureIndex+i;
            if(index >= this.islands.size()){
                index = index % islands.size();
            }
            result.add(this.islands.get(index));
        }
        return result;
    }

    /**
     * This method is called every time the currentPlayer moves a student from his WaitingRoom to one of the tables of his Hall
     * @param color is the color of the Professor, the owner of which will be changed
     */
    public Gamer changeProfessorOwner (PawnColor color) throws Exception {
        Optional<Gamer> oldOwner= this.professors.stream().filter(x->x.getColor().equals(color)).map(x->x.getOwner()).findFirst().orElse(null);
        Gamer newOwner = new Gamer();
        if(oldOwner == null){       //DUBBIO: nel caso sia null e venga lanciata l'eccezione, poi cosa succede? Cioè, in qualche modo lo si deve dire se l'owner cambia o meno. Lo gestisce l'eccezione?
            throw new Exception();  //Se al posto dell'eccezione mettessimo newOwner = currentPlayer avrebbe senso?
        }
        else if(oldOwner.equals(currentPlayer)) {
            return currentPlayer;
        }
        else {
            int oldInfluence = oldOwner.getDashboard().checkInfluence(color);   //oldInfluence represents the influence of the old owner
            int currentInfluence = currentPlayer.getDashboard().checkInfluence(color);  //currentInfluence represents the influence of the current player
            if (currentInfluence > oldInfluence) {
                newOwner = currentPlayer;
            }
            //else newOwner = oldOwner;
        }
        return newOwner;
    }

    /**
     * This method is called when the currentPlayer move a student from his WaitingRoom to an Island
     * @return the owner of the Island
     */
    public Optional<Gamer> checkIslandOwner (){
        //prendo l'isola da controllare
        Island islandToCheck = this.motherNature.getPlace();    //DUBBIO: Perchè deve prendere l'isola di madre natura? MN la sposto dopo che finisco di spostare gli studenti
        //creo un ArrayList con i colori dei prof posseduti dal currentPlayer
        ArrayList<PawnColor> colors = new ArrayList<PawnColor>();
        //cerco i prof posseduti dal currentPlayer
        for (Professor professor : this.professors){
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
        for (Professor professor : this.professors){
            if (professor.getOwner().equals(ownerIsland)){ //TODO: Metodo da modificare: l'equals dovrebbe controllare il token
                colorsOwner.add(professor.getColor());
            }
        }
        //calcolo influenza del giocatore possessore dell'isola
        int influenceOwner = islandToCheck.getInfluenceByColor(colorsOwner) + islandToCheck.getNumTowers();
        if(influenceOwner < influence){
            //return currentPlayer;
            //TODO: Problema: conflitto con l'uso di Optional<Gamer> anziché Gamer
            ownerIsland = Optional.ofNullable(currentPlayer);   //Da controllare perchè non ne sono sicura.
        }
        return ownerIsland;
    }

    /**
     * This method updates the players' order
     */
    public void updatePlayersOrder (){      //DUBBIO: non dovrebbe basarsi sul numero della carta assistente?
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
     * @return all the professors
     */
    public ArrayList<Professor> getProfessors() {
        return professors;
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
     * @param currentPlayer is the new current player
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
