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
    final ArrayList<Professor> professors;
    final ArrayList<Island> islands;
    private final Bag bag;
    private final ArrayList<Gamer> gamers;
    private Gamer currentPlayer;
    private int turnNumber;
    private final InfluenceCalculator influenceCalculator;

    /**
     * Class constructor
     * @param gamers is the ArrayList of the players connected in this game
     */
    public Game (ArrayList<Gamer> gamers){
        this.gamers = new ArrayList<>(gamers);
        this.clouds = new ArrayList<>();
        int counter = 1;
        for(Gamer ignored : this.gamers){
            this.clouds.add(new Cloud(counter));
            counter++;
        }
        this.professors = new ArrayList<>();
        for (PawnColor color : PawnColor.values()){
            professors.add(new Professor(color));
        }
        this.islands = new ArrayList<>();
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
     * This method is called by the controller to show all the islands where the gamer can move MotherNature (it depends on the
     * AssistantCard chosen by him)
     * @return an ArrayList of the only possible islands that the player can choose ordered from the closest to the farthest
     */
    public ArrayList<Island> getMotherNatureDestination (){
        ArrayList<Island> result = new ArrayList<>();
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
     * This method is called every time the currentPlayer moves a student from his waitingRoom to hall
     * @param color is the color of the Pprofessor, the owner of which will be changed
     */
    public Gamer changeProfessorOwner (PawnColor color) {
        Optional<Gamer> oldOwner= this.professors.stream().filter(x->x.getColor().equals(color)).map(Professor::getOwner).findFirst().orElse(Optional.empty());
        if(oldOwner.isEmpty() || oldOwner.get().equals(currentPlayer)) {
            Objects.requireNonNull(this.professors.stream().filter(x -> x.getColor().equals(color)).findFirst().orElse(null)).setOwner(currentPlayer);
            return currentPlayer;
        }
        else {
            int oldInfluence = oldOwner.get().getDashboard().checkInfluence(color);
            int currentInfluence = currentPlayer.getDashboard().checkInfluence(color);
            if (currentInfluence > oldInfluence) {
                Objects.requireNonNull(this.professors.stream().filter(x -> x.getColor().equals(color)).findFirst().orElse(null)).setOwner(currentPlayer);
                return currentPlayer;
            }else{
                return oldOwner.get();
            }
        }
    }

    /**
     * This method is called whenever is needed to calculate the influence on a particular island
     * @return the owner of the Island
     */
    public Optional<Gamer> checkIslandOwner (Island islandToCheck){
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
     * Method used to get motherNature
     * @return motherNature
     */
    public MotherNature getMotherNature() {
        return motherNature;
    }

    /**
     * Method used to get the clouds
     * @return the arrayList of clouds
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * Method used to get the professors
     * @return the arraylist of professors
     */
    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    /**
     * Method used to get the islands
     * @return the arraylist of islands
     */
    public ArrayList<Island> getIslands() {
        return islands;
    }

    /**
     * Method used to get the bag
     * @return the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * Method used to get the gamers
     * @return the arrayList of gamers
     */
    public ArrayList<Gamer> getGamers() {
        return gamers;
    }

    /**
     * method used to get the current gamer
     * @return the current gamer
     */
    public Gamer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method used to get the turn
     * @return the turn's number
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    public InfluenceCalculator getInfluenceCalculator() {
        return influenceCalculator;
    }

    /**
     * Method that calculate if there is a winner
     * @return the arraylist of winners (if there are)
     */
    public ArrayList<Gamer> checkWinner() {
        ArrayList<Gamer> gamers = new ArrayList<>();
        for(Gamer gamer : this.gamers){
            if(gamer.isActive()){
                gamers.add(gamer);
            }
        }
        if(gamers.size()==1){
            return gamers;
        }
        ArrayList<Gamer> winners = new ArrayList<>();
        for(Gamer gamer : gamers){
            if(gamer.getDashboard().getNumTowers()==0){
                winners.add(gamer);
                return winners;
            }
        }
        for(Gamer gamer1 : gamers){
            boolean victorious = true;
            for(Gamer gamer2 : gamers){
                if(!gamer1.equals(gamer2) && gamer1.getDashboard().getNumTowers()>=gamer2.getDashboard().getNumTowers()){
                    victorious = false;
                }
                if(victorious){
                    winners.add(gamer1);
                    return winners;
                }
            }
        }
        for(Gamer gamer1 : gamers){
            boolean victorious = true;
            for(Gamer gamer2 : gamers){
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

    /**
     * Method used to get the professors owned by a gamer
     * @param gamer is the gamer to check
     * @return the arrayList of owned professors
     */
    public ArrayList<Professor> getProfessorsByGamer(Gamer gamer) {
        InfluenceCalculator influenceCalculator = new InfluenceCalculator(this.gamers,this.professors);
        return influenceCalculator.getProfessorsOwnedByPlayer(gamer);
    }

    /**
     * Method used to set the current gamer
     * @param currentPlayer is the new current gamer
     */
    public void setCurrentPlayer(Gamer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Method used to set a new turn
     */
    public void setTurnNumber() {
        this.turnNumber++;
    }
}
