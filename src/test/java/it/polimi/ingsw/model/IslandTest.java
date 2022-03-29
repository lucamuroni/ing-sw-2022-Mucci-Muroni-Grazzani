package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    @Test
    void addStudents() {
        Bag borsa = new Bag();
        ArrayList<Student> students = new ArrayList<Student>();
        Island island = new Island();
        students.addAll(borsa.pullStudents(10));
        for(Student s : students){
            island.addStudents(s);
        }

    }

    @Test
    void mergeIsland() {
    }

    @Test
    void getNumTowers() {
        Island isola = new Island();
        assertEquals(0,isola.getNumTowers());

    }

    @Test
    void setOwner() {
        Island isola = new Island();
        Gamer giocatore = new Gamer();
        Optional<Gamer> gamer = Optional.of(giocatore);
        Optional<Gamer> gamerEmpty = Optional.empty();
        assertEquals(gamerEmpty,isola.getOwner());
        isola.setOwner(giocatore);
        assertEquals(gamer,isola.getOwner());
    }

    @Test
    void getInfluenceByColor() {
    }

    @Test
    void getOwner() {
        Island isola = new Island();
        Optional<Gamer> gamer = Optional.empty();
        assertEquals(gamer,isola.getOwner());
    }

    @Test
    void addTower() {
        Island isola = new Island();
        assertEquals(0,isola.getNumTowers());
        int i;
        for(i=0;i<4;i++){
            isola.addTower();
        }
        assertEquals(4,isola.getNumTowers());
    }
}