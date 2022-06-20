package it.polimi.ingsw.model.game.influenceCalculator;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO : nel game getTowerCOlor
public class InfluenceCalculator {
    private final ArrayList<Gamer> gamers;
    private ArrayList<Professor> professors;
    private ArrayList<PawnColor> notIncludedPawnColor;
    private boolean areTowersConsidered;
    private Island island;
    public InfluenceCalculator(ArrayList<Gamer> gamers,ArrayList<Professor> professors){
        this.gamers = gamers;
        this.professors = professors;
        this.notIncludedPawnColor = new ArrayList<PawnColor>();
        this.areTowersConsidered = true;
    }

    public Optional<Gamer> execute(Island island){
        this.island = island;
        this.areTowersConsidered = true;
        this.notIncludedPawnColor.clear();
        return this.checkIslandOwner();
    }

    public ArrayList<Professor> getProfessorsOwnedByPlayer(Gamer gamer){
        ArrayList<Professor> result = new ArrayList<Professor>();
        for(Professor prof : this.professors){
            if(prof.getOwner().isPresent()){
                if(prof.getOwner().get().equals(gamer)){
                    result.add(prof);
                }
            }
        }
        return result;
    }

    private int getOldOwnerScore(){
        if(!island.getOwner().isPresent()){
            return 0;
        }
        int result = scoreCalculator(this.getProfessorsOwnedByPlayer(island.getOwner().get()));
        if(areTowersConsidered){
            return result+this.island.getNumTowers();
        }
        return result;
    }

    private int getPlayerScore(Gamer gamer){
        return scoreCalculator(this.getProfessorsOwnedByPlayer(gamer));
    }

    private int scoreCalculator(ArrayList<Professor> professors){
        ArrayList<Professor> copy = new ArrayList<Professor>(professors);
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

    public void setTowerInclusion(boolean inclusion){
        this.areTowersConsidered = inclusion;
    }

    public void addColorExclusion(ArrayList<PawnColor> colors){
        this.notIncludedPawnColor.clear();
        this.notIncludedPawnColor.addAll(colors);
    }

    private Optional<Gamer> checkIslandOwner(){
        Optional<Gamer> result = Optional.empty();;
        ArrayList<Gamer> gamersToCheck;
        boolean first = false;
        int oldOwnerScore = this.getOldOwnerScore();
        if(oldOwnerScore!=0){
            result = this.island.getOwner();
            gamersToCheck = new ArrayList<Gamer>(this.gamers);
            gamersToCheck.remove(this.island.getOwner().get());
        }else{
            gamersToCheck = this.gamers;
            first = true;
        }
        for(Gamer gamer : gamersToCheck){
            if(getPlayerScore(gamer)>oldOwnerScore){
                oldOwnerScore = getPlayerScore(gamer);
                result = Optional.of(gamer);
            }
        }
        if (oldOwnerScore!=0) {
            if (first) {
                result.get().getDashboard().moveTower(-1);
                this.island.addTower();
            } else {
                result.get().getDashboard().moveTower(-this.island.getNumTowers());
                this.island.getOwner().get().getDashboard().moveTower(this.island.getNumTowers());
            }
            this.island.setOwner(result.get());
        }
        return result;
    }
}
