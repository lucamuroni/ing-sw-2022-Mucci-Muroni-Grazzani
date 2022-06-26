package it.polimi.ingsw.model.game.influenceCalculator;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InfluenceCalculatorTest {
    /*Island island = new Island(2);
    Gamer gamer1 = new Gamer(111,"bufu", TowerColor.GREY);
    Gamer gamer2 = new Gamer(111,"babbo",TowerColor.GREY);
    ArrayList<Gamer> gamers = new ArrayList<>();
    ArrayList<Professor> professors = new ArrayList<>();

    Professor prof1 = new Professor(PawnColor.BLUE);
    Professor prof2 = new Professor(PawnColor.YELLOW);
    Professor prof3 = new Professor(PawnColor.PINK);
    Professor prof4 = new Professor(PawnColor.GREEN);
    Professor prof5 = new Professor(PawnColor.RED);
    Student s1 = new Student(PawnColor.BLUE);
    Student s2 = new Student(PawnColor.BLUE);
    Student s3 = new Student(PawnColor.BLUE);
    Student s4 = new Student(PawnColor.RED);
    Student s5 = new Student(PawnColor.RED);
    Student s6 = new Student(PawnColor.GREEN);
    Student s7 = new Student(PawnColor.GREEN);
    Student s8 = new Student(PawnColor.GREEN);
    Student s9 = new Student(PawnColor.PINK);
    Student s10 = new Student(PawnColor.YELLOW);
    Student s11 = new Student(PawnColor.YELLOW);
    Student s12 = new Student(PawnColor.YELLOW);
    Student s13 = new Student(PawnColor.YELLOW);


    @Test
    void execute() {
        gamers.add(gamer1);
        gamers.add(gamer2);
        professors.add(prof1);
        professors.add(prof2);
        professors.add(prof3);
        professors.add(prof4);
        professors.add(prof5);
        InfluenceCalculator test = new InfluenceCalculator(gamers,professors);
        assertTrue(test.execute(island).isEmpty());
        prof1.setOwner(gamer1);
        prof2.setOwner(gamer1);
        prof5.setOwner(gamer1);
        assertTrue(test.execute(island).isEmpty());
        island.addStudents(s2);
        island.addStudents(s3);
        island.addStudents(s4);
        island.addStudents(s5);
        island.addStudents(s6);
        island.addStudents(s7);
        island.addStudents(s8);
        island.addStudents(s9);
        island.addStudents(s10);
        island.addStudents(s11);
        island.addStudents(s12);
        island.addStudents(s13);
        island.addStudents(s1);
        prof1.setOwner(gamer2);
        prof2.setOwner(gamer2);
        assertEquals(gamer2,test.execute(island).get());
    }

    @Test
    void setTowerInclusion() {
    }

    @Test
    void addColorExclusion() {
    }*/
}