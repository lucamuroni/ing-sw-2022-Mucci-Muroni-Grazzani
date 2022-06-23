package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

public class Thief extends CharacterCardInterface {

    private PawnColor color;

    public Thief(CharacterCard card, ExpertGame game,PawnColor color){
        super(card, game);
        this.color = color;
    }
    @Override
    public void handle() {
        ExpertGame game = this.getGame();
        for (ExpertGamer gamer1 : game.getExpertGamers()) {
            int num = (int) gamer1.getDashboard().getHall().stream().filter(x -> x.getColor().equals(this.color)).count();
            if (num >= 3) {
                for (int i = 0; i<3; i++) {
                    Student stud = gamer1.getDashboard().getHall().stream().filter(x -> x.getColor().equals(this.color)).findFirst().get();
                    gamer1.getDashboard().getHall().remove(stud);
                    game.getBag().pushStudent(stud);
                }
            } else {
                for (int i = 0; i<num; i++) {
                    Student stud = gamer1.getDashboard().getHall().stream().filter(x -> x.getColor().equals(this.color)).findFirst().get();
                    gamer1.getDashboard().getHall().remove(stud);
                    game.getBag().pushStudent(stud);
                }
            }
        }
    }
}
