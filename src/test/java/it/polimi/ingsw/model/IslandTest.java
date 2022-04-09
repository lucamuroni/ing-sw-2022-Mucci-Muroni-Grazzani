package it.polimi.ingsw.model;


import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
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
        Island isolaDaEliminare = new Island();
        Island mainIsola = new Island();
        Bag borsa = new Bag();
        ArrayList<Student> studenti= new ArrayList<Student>();
        isolaDaEliminare.addTower();
        isolaDaEliminare.addTower();
        mainIsola.addTower();
        studenti.addAll(borsa.pullStudents(12));
        for (Student s :studenti){
            isolaDaEliminare.addStudents(s);
        }
        studenti.removeAll(studenti);
        assertEquals(0,studenti.size());
        studenti.addAll(borsa.pullStudents(2));
        for (Student s :studenti){
            mainIsola.addStudents(s);
        }
        mainIsola.mergeIsland(isolaDaEliminare);
        assertEquals(3,mainIsola.getNumTowers());
    }

    @Test
    void getNumTowers() {
        Island isola = new Island();
        assertEquals(0,isola.getNumTowers());

    }

    @Test
    void setOwner() {
        Island isola = new Island();
        Gamer giocatore = new Gamer(123,"luca");
        Optional<Gamer> gamer = Optional.of(giocatore);
        Optional<Gamer> gamerEmpty = Optional.empty();
        assertEquals(gamerEmpty,isola.getOwner());
        isola.setOwner(giocatore);
        assertEquals(gamer,isola.getOwner());
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
        Island isola = new Island();
        isola.addStudents(s1);
        isola.addStudents(s2);
        isola.addStudents(s3);
        isola.addStudents(s4);
        isola.addStudents(s5);
        isola.addStudents(s6);
        isola.addStudents(s7);
        isola.addStudents(s8);
        isola.addStudents(s9);
        isola.addStudents(s0);
        ArrayList<PawnColor> colors = new ArrayList<PawnColor>();
        colors.add(s1.getColor());
        colors.add(s2.getColor());
        colors.add(s3.getColor());
        assertEquals(3,isola.getInfluenceByColor(colors));
        colors.add(PawnColor.YELLOW);
        assertEquals(3,isola.getInfluenceByColor(colors));
        colors.add(s4.getColor());
        colors.add(s5.getColor());
        assertEquals(5,isola.getInfluenceByColor(colors));
        colors.remove(0);
        colors.remove(0);
        colors.remove(0);
        assertEquals(2,isola.getInfluenceByColor(colors));
        colors.add(s0.getColor());
        colors.add(s0.getColor());
        colors.add(s0.getColor());
        colors.add(s0.getColor());
        assertEquals(6,isola.getInfluenceByColor(colors));
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