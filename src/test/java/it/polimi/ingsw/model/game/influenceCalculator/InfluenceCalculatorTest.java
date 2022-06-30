package it.polimi.ingsw.model.game.influenceCalculator;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InfluenceCalculatorTest {
    Island island = new Island(2);
    Gamer gamer1 = new Gamer(111,"nome1", TowerColor.GREY);
    Gamer gamer2 = new Gamer(111,"nome2", TowerColor.WHITE);
    ArrayList<Gamer> gamers = new ArrayList<>();
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
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 6);
        gamer2.initGamer(students, 6);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        InfluenceCalculator test = new InfluenceCalculator(game.getGamers(), game.getProfessors());
        assertTrue(test.execute(island).isEmpty());
        game.getProfessors().get(0).setOwner(gamer1);
        game.getProfessors().get(1).setOwner(gamer1);
        game.getProfessors().get(4).setOwner(gamer1);
        assertTrue(test.execute(island).isEmpty());
        island.addStudents(s1);
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
        game.getProfessors().get(0).setOwner(gamer2);
        game.getProfessors().get(1).setOwner(gamer2);
        assertEquals(gamer2, test.execute(island).get());
    }

    @Test
    void setTowerInclusion() {
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 6);
        gamer2.initGamer(students, 6);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        InfluenceCalculator test = new InfluenceCalculator(game.getGamers(), game.getProfessors());
        test.setTowerInclusion(false);
        assertTrue(test.execute(island).isEmpty());
        game.getProfessors().get(0).setOwner(gamer2);
        game.getProfessors().get(1).setOwner(gamer2);
        game.getProfessors().get(4).setOwner(gamer1);
        island.addStudents(s1);
        island.addStudents(s2);
        island.addStudents(s3);
        island.addStudents(s4);
        island.addStudents(s6);
        island.addStudents(s7);
        island.addStudents(s8);
        island.addStudents(s9);
        island.addStudents(s10);
        island.addStudents(s11);
        island.addStudents(s12);
        island.addStudents(s13);
        island.addTower();
        assertEquals(gamer2, test.execute(island).get());
        test.setTowerInclusion(true);
        game.getProfessors().get(3).setOwner(gamer1);
        island.setOwner(gamer1);
        assertEquals(gamer1, test.execute(island).get());
    }

    /*@Test
    void setMoreInfluence() {
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 6);
        gamer2.initGamer(students, 6);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        InfluenceCalculator test = new InfluenceCalculator(game.getGamers(), game.getProfessors());
        assertTrue(test.execute(island).isEmpty());
        game.getProfessors().get(0).setOwner(gamer1);
        game.getProfessors().get(1).setOwner(gamer1);
        game.getProfessors().get(4).setOwner(gamer2);
        island.addStudents(s1);
        island.addStudents(s2);
        island.addStudents(s3);
        island.addStudents(s4);
        island.addStudents(s6);
        island.addStudents(s7);
        island.addStudents(s8);
        island.addStudents(s9);
        island.addStudents(s10);
        island.addStudents(s11);
        island.addStudents(s12);
        island.addStudents(s13);
        assertEquals(gamer1, test.execute(island).get());
        game.getProfessors().get(3).setOwner(gamer2);
        test.setMoreInfluence(gamer2);
        assertEquals(gamer2, test.execute(island).get());
    }*/

    @Test
    void addColorExclusion() {
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 6);
        gamer2.initGamer(students, 6);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        InfluenceCalculator test = new InfluenceCalculator(game.getGamers(), game.getProfessors());
        assertTrue(test.execute(island).isEmpty());
        game.getProfessors().get(0).setOwner(gamer1);
        game.getProfessors().get(1).setOwner(gamer1);
        game.getProfessors().get(4).setOwner(gamer2);
        island.addStudents(s1);
        island.addStudents(s2);
        island.addStudents(s3);
        island.addStudents(s4);
        island.addStudents(s6);
        island.addStudents(s7);
        island.addStudents(s8);
        island.addStudents(s9);
        island.addStudents(s10);
        island.addStudents(s11);
        island.addStudents(s12);
        island.addStudents(s13);
        assertEquals(gamer1, test.execute(island).get());
        ArrayList<PawnColor> colors = new ArrayList<>();
        colors.add(PawnColor.BLUE);
        test.addColorExclusion(colors);
        assertEquals(gamer2, test.execute(island).get());
    }

    @Test
    void reset() {
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 6);
        gamer2.initGamer(students, 6);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        InfluenceCalculator test = new InfluenceCalculator(game.getGamers(), game.getProfessors());
        assertTrue(test.execute(island).isEmpty());
        ArrayList<PawnColor> colors = new ArrayList<>();
        colors.add(PawnColor.BLUE);
        test.addColorExclusion(colors);
        test.setMoreInfluence(gamer1);
        test.setTowerInclusion(false);
        test.reset();
        game.getProfessors().get(0).setOwner(gamer2);
        game.getProfessors().get(1).setOwner(gamer2);
        game.getProfessors().get(4).setOwner(gamer1);
        island.addStudents(s1);
        island.addStudents(s2);
        island.addStudents(s3);
        island.addStudents(s4);
        island.addStudents(s6);
        island.addStudents(s7);
        island.addStudents(s8);
        island.addStudents(s9);
        island.addStudents(s10);
        island.addStudents(s11);
        island.addStudents(s12);
        island.addStudents(s13);
        island.addTower();
        assertEquals(gamer2, test.execute(island).get());
    }
}