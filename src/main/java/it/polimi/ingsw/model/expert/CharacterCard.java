package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public enum CharacterCard {
    THIEF("Thief", 3, GamePhase.ALL),
    VILLAGER("Villager", 2, GamePhase.PROFESSORSINFLUENCE),
    AMBASSADOR("Ambassador", 3, GamePhase.MOTHERNATURE),
    POSTMAN("Postman", 1, GamePhase.MOTHERNATURE),
    CENTAUR("Centaur", 3, GamePhase.ISLANDINFLUENCE),
    KNIGHT("Knight", 2, GamePhase.ISLANDINFLUENCE),
    MERCHANT("Merchant", 3, GamePhase.ISLANDINFLUENCE),
    BARD("Bard", 1, GamePhase.ALL);

    private final String name;
    private final int moneyCost;
    private final GamePhase phase;

    private CharacterCard(String name, int moneyCost, GamePhase phase){
        this.phase = phase;
        this.name = name;
        this.moneyCost = moneyCost;
    }

    public void activate(ExpertGame game, Gamer gamer, Island island, ArrayList<Student> student) {
        switch (name) {
            case "Thief":
                PawnColor col = student.get(0).getColor();
                for (ExpertGamer gamer1 : game.getExpertGamers()) {
                    int num = (int) gamer1.getDashboard().getHall().stream().filter(x -> x.getColor().equals(col)).count();
                    if (num >= 3) {
                        for (int i = 0; i<3; i++) {
                            Student stud = gamer1.getDashboard().getHall().stream().filter(x -> x.getColor().equals(col)).findFirst().get();
                            gamer1.getDashboard().getHall().remove(stud);
                            game.getBag().pushStudent(stud);
                        }
                    } else {
                        for (int i = 0; i<num; i++) {
                            Student stud = gamer1.getDashboard().getHall().stream().filter(x -> x.getColor().equals(col)).findFirst().get();
                            gamer1.getDashboard().getHall().remove(stud);
                            game.getBag().pushStudent(stud);
                        }
                    }
                }
                break;
            case "Villager":
                break;
            case "Ambassador":
                game.checkIslandOwner(island);
                //TODO: fare metodo privato mergeIsland
                break;
            case "Postman":
                game.setMoreSteps();
                break;
            case "Centaur":
                game.getInfluenceCalculator().setTowerInclusion(false);
                break;
            case "Knight":
                game.getInfluenceCalculator().setMoreInfluence(gamer);
                break;
            case "Merchant":
                ArrayList<PawnColor> color = new ArrayList<>();
                color.add(student.get(0).getColor());
                game.getInfluenceCalculator().addColorExclusion(color);
                break;
            case "Bard":
                gamer.getDashboard().getWaitingRoom().remove(student.get(0));
                gamer.getDashboard().getHall().remove(student.get(1));
                gamer.getDashboard().getWaitingRoom().add(student.get(1));
                gamer.getDashboard().getHall().add(student.get(0));
                break;
        }
    }

    public String getName() {
        return name;
    }

    public int getMoneyCost() {
        return moneyCost;
    }
}
