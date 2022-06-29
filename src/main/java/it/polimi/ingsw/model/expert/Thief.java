package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

/**
 * Card Thief from the deck of CharacterCards
 * @author Davide Grazzani
 */
public class Thief extends CharacterCardInterface {
    private final PawnColor color;

    /**
     * Constructor of the class
     * @param card is the enum associated with the character
     * @param game is the current game
     * @param color is the color chosen by the gamer
     */
    public Thief(CharacterCard card, ExpertGame game,PawnColor color){
        super(card, game);
        this.color = color;
    }

    /**
     * Method that handles the effect of the card
     */
    @Override
    public void handle() {
        ExpertGame game = this.getGame();
        for (Gamer gamer1 : game.getGamers()) {
            ExpertGamer g = (ExpertGamer) gamer1;
            int num = (int) g.getDashboard().getHall().stream().filter(x -> x.getColor().equals(this.color)).count();
            if (num >= 3) {
                for (int i = 0; i<3; i++) {
                    Student stud = g.getDashboard().getHall().stream().filter(x -> x.getColor().equals(this.color)).findFirst().orElse(null);
                    g.getDashboard().getHall().remove(stud);
                    game.getBag().pushStudent(stud);
                }
            } else {
                for (int i = 0; i<num; i++) {
                    Student stud = g.getDashboard().getHall().stream().filter(x -> x.getColor().equals(this.color)).findFirst().orElse(null);
                    g.getDashboard().getHall().remove(stud);
                    game.getBag().pushStudent(stud);
                }
            }
        }
    }
}
