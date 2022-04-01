package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorTest {

    @Test
    void getColor() {
        Professor professor = new Professor(PawnColor.PINK);
        assertEquals(PawnColor.PINK, professor.getColor());
    }

    @Test
    void setOwner() {
        Professor professor = new Professor(PawnColor.PINK);
        Gamer gamer = new Gamer();
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
