package it.polimi.ingsw.model.game.influenceCalculator;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class used to calculate the influence on an island
 * @author Davide Grazzani
 */
public class InfluenceCalculator {
    private final ArrayList<Gamer> gamers;
    private final ArrayList<Professor> professors;
    private final ArrayList<PawnColor> notIncludedPawnColor;
    private boolean areTowersConsidered;
    private Gamer moreInfluence;
    private Island island;

    /**
     * Constructor of the class
     * @param gamers represents the arrayList of gamers
     * @param professors is the arrayList of professors
     */
    public InfluenceCalculator(ArrayList<Gamer> gamers,ArrayList<Professor> professors){
        this.gamers = gamers;
        this.professors = professors;
        this.notIncludedPawnColor = new ArrayList<>();
        this.areTowersConsidered = true;
        this.moreInfluence = null;
    }

    /**
     * Method called by the class Game to check the owner of an island
     * @param island is the island to check
     * @return the owner (if is present)
     */
    public Optional<Gamer> execute(Island island){
        this.island = island;
        return this.checkIslandOwner();
    }

    /**
     * Method used to get the professors owned by a gamer
     * @param gamer is the gamer to check
     * @return the arrayList of professors owned by the gamer
     */
    public ArrayList<Professor> getProfessorsOwnedByPlayer(Gamer gamer){
        ArrayList<Professor> result = new ArrayList<>();
        for(Professor prof : this.professors){
            if(prof.getOwner().isPresent()){
                if(prof.getOwner().get().equals(gamer)){
                    result.add(prof);
                }
            }
        }
        return result;
    }

    /**
     * Private method used to calculate the current owner's influence on an island
     * @return the score calculated
     */
    private int getOldOwnerScore(){
        int result = 0;
        if(island.getOwner().isEmpty()){
            return 0;
        } else if (moreInfluence!=null) {
            if (island.getOwner().get().getToken() == this.moreInfluence.getToken())
                result += 2;
        }
        result += scoreCalculator(this.getProfessorsOwnedByPlayer(island.getOwner().get()));
        if(areTowersConsidered){
            return result+this.island.getNumTowers();
        }
        return result;
    }

    /**
     * Private method used to calculate gamer influence on an island
     * @param gamer is the gamer to check
     * @return the score calculated
     */
    private int getPlayerScore(Gamer gamer){
        int influence = 0;
        if (moreInfluence!=null) {
            if (gamer.getToken() == this.moreInfluence.getToken())
                influence += 2;
        }
        influence += scoreCalculator(this.getProfessorsOwnedByPlayer(gamer));
        return influence;
    }

    /**
     * Private method that effectively calculates the score of the gamer on an island
     * @param professors is the arrayçist of professors owned by the gamer
     * @return the score calculated
     */
    private int scoreCalculator(ArrayList<Professor> professors){
        ArrayList<Professor> copy = new ArrayList<>(professors);
        if(!notIncludedPawnColor.isEmpty()){
            for(PawnColor color : this.notIncludedPawnColor){
                for(Professor prof : professors){
                    if(prof.getColor()==color){
                        copy.remove(prof);
                    }
                }
            }
        }
        return this.island.getInfluenceByColor(copy.stream().map(x->x.getColor()).collect(Collectors.toCollection(ArrayList::new)));
    }

    /**
     * Method used to set or not the inclusion of towers in the score calculation (Centaur effect)
     * @param inclusion will be true if towers must be considered, false otherwise
     */
    public void setTowerInclusion(boolean inclusion){
        this.areTowersConsidered = inclusion;
    }

    /**
     * Method used to set more influence on the score calculation of a gamer (Knight effect)
     * @param gamer is the gamer that has activated the effect
     */
    public void setMoreInfluence(Gamer gamer) {
        this.moreInfluence = gamer;
    }

    /**
     * Method used to set the arrayList of colors to exclude from the score calculation
     * @param colors is the arrayList of colors to exclude
     */
    public void addColorExclusion(ArrayList<PawnColor> colors){
        this.notIncludedPawnColor.addAll(colors);
    }

    /**
     * Private method that manages the calculator
     * @return the owner (if is present)
     */
    private Optional<Gamer> checkIslandOwner(){
        Optional<Gamer> result = Optional.empty();
        ArrayList<Gamer> gamersToCheck;
        boolean first = false;
        int oldOwnerScore = this.getOldOwnerScore();
        if(oldOwnerScore!=0){
            result = this.island.getOwner();
            gamersToCheck = new ArrayList<>(this.gamers);
            gamersToCheck.remove(this.island.getOwner().orElse(null));
        }else{
            gamersToCheck = this.gamers;
            first = true;
        }
        if(first){
            for(Gamer gamer1 : gamersToCheck){
                boolean owner = true;
                for(Gamer gamer2 : gamersToCheck){
                    if(!gamer1.equals(gamer2) && getPlayerScore(gamer1)<=getPlayerScore(gamer2)){
                        owner = false;
                    }
                }
                if(owner){
                    result = Optional.of(gamer1);
                    oldOwnerScore = getPlayerScore(gamer1);
                }
            }
        }else{
            for(Gamer gamer : gamersToCheck){
                if(getPlayerScore(gamer)>oldOwnerScore){
                    oldOwnerScore = getPlayerScore(gamer);
                    result = Optional.of(gamer);
                }
            }
        }
        if (oldOwnerScore!=0) {
            if (first) {
                result.get().getDashboard().moveTower(-1);
                this.island.addTower();
            } else {
                result.ifPresent(gamer -> gamer.getDashboard().moveTower(-this.island.getNumTowers()));
                this.island.getOwner().ifPresent(gamer -> gamer.getDashboard().moveTower(this.island.getNumTowers()));
            }
            this.island.setOwner(result.orElse(null));
        }
        return result;
    }

    public void reset(){
        this.notIncludedPawnColor.clear();
        this.areTowersConsidered = true;
        this.moreInfluence = null;
    }
}
