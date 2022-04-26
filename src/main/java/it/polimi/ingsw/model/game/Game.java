package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.MotherNature;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to manage most of the game's mechanics
 * @author Luca Muroni
 * @author Davide Grazzani
 * @author Sara Mucci
 */
public class Game {
    final MotherNature motherNature;
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

    protected Game(int playersNumber) {
        this.gamers = null;
        this.clouds = new ArrayList<Cloud>();
        for(int i=0; i<playersNumber; i++){
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
     * This function is used to fill a cloud chosen by a Gamer at the end of the round
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
        Island motherNatureIsland = this.motherNature.getPlace();
        int motherNatureIndex = this.islands.indexOf(this.motherNature.getPlace());
        Island oppositeIsland = this.islands.get((motherNatureIndex+6)%12);
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
        int maxIndexMove = currentPlayer.getDeck().getCurrentSelection().getMovement();
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
        if(oldOwner==null){
            throw new Exception();
        }
        else if(oldOwner.isEmpty() || oldOwner.equals(currentPlayer)) {
            this.professors.stream().filter(x->x.getColor().equals(color)).findFirst().orElse(null).setOwner(currentPlayer);
            return currentPlayer;
        }
        else {
            int oldInfluence = oldOwner.get().getDashboard().checkInfluence(color);
            int currentInfluence = currentPlayer.getDashboard().checkInfluence(color);
            if (currentInfluence > oldInfluence) {
                this.professors.stream().filter(x->x.getColor().equals(color)).findFirst().orElse(null).setOwner(currentPlayer);
                return currentPlayer;
            }else{
                return oldOwner.get();
            }
            //else newOwner = oldOwner;
        }
    }

    /**
     * This method is called when the currentPlayer moves MotherNature
     * @return the owner of the Island
     */
    public Optional<Gamer> checkIslandOwner (){
        Island islandToCheck = this.motherNature.getPlace();
        Gamer newOwner;
        int maxInfluence;
        if(islandToCheck.getOwner().isPresent()){
           newOwner = islandToCheck.getOwner().get();
           maxInfluence = islandToCheck.getInfluenceByColor(this.professors.stream().filter(x -> x.getOwner().equals(islandToCheck.getOwner())).map(x -> x.getColor()).collect(Collectors.toCollection(ArrayList::new))) + islandToCheck.getNumTowers();
        }else{
           newOwner = null;
           maxInfluence = 0;
        }
        for(Gamer gamer : this.gamers){
            ArrayList<PawnColor> colors = this.professors.stream().filter(x -> x.getOwner().equals(gamer)).map(x -> x.getColor()).collect(Collectors.toCollection(ArrayList::new));
            int gamerInfluence = islandToCheck.getInfluenceByColor(colors);
            if(islandToCheck.getOwner().equals(gamer)){
                gamerInfluence += islandToCheck.getNumTowers();
            }
            if(gamerInfluence > maxInfluence){
                newOwner = gamer;
                maxInfluence = gamerInfluence;
            }
        }
        islandToCheck.setOwner(newOwner);
        return Optional.of(newOwner);
    }

    /**
     * This method updates the players' order
     */
    public void updatePlayersOrder (){

        //TODO: controllare se è qui che si chiama setPastSelection per tutti i giocatori oppure lo si fa fare dal controller

        Collections.sort(gamers, new Comparator<Gamer>() {
            @Override
            public int compare(Gamer g1, Gamer g2) {
                if(g1.getDeck().getPastSelection().getTurnValue() > g2.getDeck().getPastSelection().getTurnValue())
                    return 1;
                else
                    return -1;
            }
        });
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
