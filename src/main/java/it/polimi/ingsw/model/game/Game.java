package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.MotherNature;
import it.polimi.ingsw.model.game.influenceCalculator.InfluenceCalculator;
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
    final ArrayList<Island> islands;
    private final Bag bag;
    private final ArrayList<Gamer> gamers;
    private Gamer currentPlayer;
    private int turnNumber;
    private InfluenceCalculator influenceCalculator;

    /**
     * Class constructor
     * @param gamers is the ArrayList of the players connected in this game
     */
    public Game (ArrayList<Gamer> gamers){
        this.gamers = new ArrayList<>(gamers);
        this.clouds = new ArrayList<Cloud>();
        int counter = 1;
        for(Gamer gamer : this.gamers){
            this.clouds.add(new Cloud(counter));
            counter++;
        }
        this.professors = new ArrayList<Professor>();
        for (PawnColor color : PawnColor.values()){
            professors.add(new Professor(color));
        }
        this.islands = new ArrayList<Island>();
        for(int numIslands=0; numIslands<12;numIslands++){
            this.islands.add(new Island(numIslands+1));
        }
        Random random = new Random();
        this.motherNature = new MotherNature(this.islands.get(random.nextInt(this.islands.size())));
        this.bag = new Bag();
        initiatePlayersOrder();
        this.turnNumber = 0;
        this.influenceCalculator = new InfluenceCalculator(this.gamers, this.professors);
    }

    Game(int playersNumber) {
        this.gamers = null;
        this.clouds = new ArrayList<Cloud>();
        for(int i=0; i<playersNumber; i++){
            this.clouds.add(new Cloud(i+1));
        }
        this.professors = new ArrayList<Professor>();
        for (PawnColor color : PawnColor.values()){
            professors.add(new Professor(color));
        }
        this.islands = new ArrayList<Island>();
        for(int numIslands=0; numIslands<12;numIslands++){
            this.islands.add(new Island(numIslands+1));
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
     * @return an ArrayList of the only possible islands that the player can choose ordered from the closest to the farthest
     */
    public ArrayList<Island> getMotherNatureDestination (){
        ArrayList<Island> result = new ArrayList<Island>();
        int motherNatureIndex = this.islands.indexOf(this.motherNature.getPlace());
        int maxIndexMove = currentPlayer.getDeck().getCurrentSelection().getMovement();
        for(int i=1;i<=maxIndexMove;i++){
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
        Optional<Gamer> oldOwner= this.professors.stream().filter(x->x.getColor().equals(color)).map(x->x.getOwner()).findFirst().orElse(Optional.empty());
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
     * This method is called whenever is needed to calculate the influence on a particular island
     * @return the owner of the Island
     */
    public Optional<Gamer> checkIslandOwner (Island islandToCheck){
        //InfluenceCalculator influenceCalculator = new InfluenceCalculator(this.gamers,this.professors);
        return this.influenceCalculator.execute(islandToCheck);

    }
    /**
     * This method is called when the currentPlayer moves MotherNature
     * @return the owner of the Island
     */
    public Optional<Gamer> checkIslandOwner (){
        return checkIslandOwner(this.motherNature.getPlace());
    }

    /**
     * This method updates the players' order for the next turn
     */
    public void updatePlayersOrder (){
        ArrayList<Gamer> gamers;
        gamers = this.gamers.stream().sorted(Comparator.comparingInt(x-> x.getDeck().getPastSelection().getTurnValue())).collect(Collectors.toCollection(ArrayList::new));
        this.gamers.clear();
        this.gamers.addAll(gamers);
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

    public InfluenceCalculator getInfluenceCalculator() {
        return influenceCalculator;
    }

    /**
     * Setter method, it is called only after a player's turn ends
     */
    public void setTurnNumber() {
        this.turnNumber++;
    }
    
    public ArrayList<Gamer> checkWinner() {
        ArrayList<Gamer> winners = new ArrayList<Gamer>();
        for(Gamer gamer : this.gamers){
            if(gamer.getDashboard().getNumTowers()==0){
                winners.add(gamer);
                return winners;
            }
        }
        for(Gamer gamer1 : this.gamers){
            boolean victorious = true;
            for(Gamer gamer2 : this.gamers){
                if(!gamer1.equals(gamer2) && gamer1.getDashboard().getNumTowers()>=gamer2.getDashboard().getNumTowers()){
                    victorious = false;
                }
                if(victorious){
                    winners.add(gamer1);
                    return winners;
                }
            }
        }
        for(Gamer gamer1 : this.gamers){
            boolean victorious = true;
            for(Gamer gamer2 : this.gamers){
                if(!gamer1.equals(gamer2) && this.getProfessorsByGamer(gamer1).size()<this.getProfessorsByGamer(gamer2).size()){
                    victorious = false;
                }
                if(victorious){
                    winners.add(gamer1);
                }
            }
        }
        return winners;
    }

    public ArrayList<Professor> getProfessorsByGamer(Gamer gamer) {
        InfluenceCalculator influenceCalculator = new InfluenceCalculator(this.gamers,this.professors);
        return influenceCalculator.getProfessorsOwnedByPlayer(gamer);
    }
}
