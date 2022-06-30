package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    Bag bag = new Bag();
    Island island = new Island(1);
    @Test
    void addStudents() {
        assertEquals(0, island.getStudents().size());
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(10));
        for(Student s : students){
            island.addStudents(s);
        }
        assertEquals(students, island.getStudents());
        assertEquals(10, island.getStudents().size());
    }

    @Test
    void mergeIsland() {
        Island islandToEliminate = new Island(2);
        Island mainIsland = new Island(3);
        islandToEliminate.addTower();
        islandToEliminate.addTower();
        mainIsland.addTower();
        ArrayList<Student> studentsEliminate = new ArrayList<>(bag.pullStudents(12));
        for (Student s :studentsEliminate){
            islandToEliminate.addStudents(s);
        }
        ArrayList<Student> studentsMain = new ArrayList<>(bag.pullStudents(2));
        for (Student s :studentsMain){
            mainIsland.addStudents(s);
        }
        mainIsland.mergeIsland(islandToEliminate);
        assertEquals(3, mainIsland.getNumTowers());
        assertEquals(14, mainIsland.getStudents().size());
    }

    @Test
    void getNumTowers() {
        assertEquals(0, island.getNumTowers());
    }

    @Test
    void getStudents() {
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(5));
        for (Student s : students) {
            island.addStudents(s);
        }
        assertEquals(students, island.getStudents());
    }

    @Test
    void setOwner() {
        Gamer gamer = new Gamer(123,"luca", TowerColor.GREY);
        Optional<Gamer> gamer1 = Optional.of(gamer);
        Optional<Gamer> gamerEmpty = Optional.empty();
        assertEquals(gamerEmpty, island.getOwner());
        island.setOwner(gamer);
        assertEquals(gamer1, island.getOwner());
    }

    @Test
    void getInfluenceByColor() {
        Student s1 = new Student(PawnColor.BLUE);
        Student s2 = new Student(PawnColor.BLUE);
        Student s3 = new Student(PawnColor.BLUE);
        Student s4 = new Student(PawnColor.RED);
        Student s5 = new Student(PawnColor.RED);
        Student s6 = new Student(PawnColor.GREEN);
        Student s7 = new Student(PawnColor.PINK);
        Student s8 = new Student(PawnColor.PINK);
        Student s9 = new Student(PawnColor.PINK);
        Student s0 = new Student(PawnColor.PINK);
        island.addStudents(s1);
        island.addStudents(s2);
        island.addStudents(s3);
        island.addStudents(s4);
        island.addStudents(s5);
        island.addStudents(s6);
        island.addStudents(s7);
        island.addStudents(s8);
        island.addStudents(s9);
        island.addStudents(s0);
        ArrayList<PawnColor> colors = new ArrayList<>();
        colors.add(s1.getColor());
        assertEquals(3, island.getInfluenceByColor(colors));
        colors.add(PawnColor.YELLOW);
        assertEquals(3, island.getInfluenceByColor(colors));
        colors.add(s4.getColor());
        assertEquals(5, island.getInfluenceByColor(colors));
        colors.remove(0);
        assertEquals(2, island.getInfluenceByColor(colors));
        colors.add(s0.getColor());
        assertEquals(6, island.getInfluenceByColor(colors));
    }

    @Test
    void getOwner() {
        Optional<Gamer> gamer = Optional.empty();
        assertEquals(gamer, island.getOwner());
    }

    @Test
    void addTower() {
        assertEquals(0, island.getNumTowers());
        int i;
        for(i = 0; i < 4; i++){
            island.addTower();
        }
        assertEquals(4, island.getNumTowers());
    }

    @Test
    void getId() {
        assertEquals(1, island.getId());
    }
}