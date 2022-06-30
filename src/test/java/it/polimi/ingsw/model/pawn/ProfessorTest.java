package it.polimi.ingsw.model.pawn;

import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorTest {
    @Test
    void setOwner() {
        Professor professor = new Professor(PawnColor.PINK);
        Gamer gamer = new Gamer(123, "luca", TowerColor.BLACK);
        Optional<Gamer> gamer1 = Optional.of(gamer);
        Optional<Gamer> gamerEmpty = Optional.empty();
        assertEquals(gamerEmpty, professor.getOwner());
        professor.setOwner(gamer);
        assertEquals(gamer1, professor.getOwner());
    }

    @Test
    void getOwner() {
        Professor professor = new Professor(PawnColor.PINK);
        Optional<Gamer> gamer = Optional.empty();
        assertEquals(gamer, professor.getOwner());
    }
}
