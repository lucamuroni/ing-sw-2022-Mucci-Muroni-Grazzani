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
    private ArrayList<Gamer> gamers;
    private ArrayList<Professor> professors;
    private ArrayList<PawnColor> notIncludedPawnColor;
    private boolean areTowersConsidered;
    private Island island;
    public InfluenceCalculator(ArrayList<Gamer> gamers,ArrayList<Professor> professors){
        this.gamers = gamers;
        this.professors = professors;
        this.notIncludedPawnColor = null;
        this.areTowersConsidered = true;
    }

    public Optional<Gamer> execute(Island island){
        this.island = island;
        this.areTowersConsidered = true;
        this.notIncludedPawnColor.clear();

    }

    private ArrayList<Professor> getProfessorsOwnedByPlayer(Gamer gamer){
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

    private int checkOldOwnerScore(){
        if(!island.getOwner().isPresent()){
            return 0;
        }
        ArrayList<Professor> professors = this.getProfessorsOwnedByPlayer(island.getOwner().get());

    }

    private int score(ArrayList<Professor> professors){
        if(!notIncludedPawnColor.isEmpty()){
            for(PawnColor color : this.notIncludedPawnColor){
                for(Professor prof : professors){
                    if(prof.getColor()==color){
                        professors.remove(prof);
                    }
                }
            }
        }
        int result = this.island.getInfluenceByColor(professors.stream().map(x->x.getColor()).collect(Collectors(ArrayList::new)));

    }
}
