package it.polimi.ingsw.model;

import it.polimi.ingsw.model.dashboard.ExpertDashboard;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGamerTest {
    ExpertGamer expertGamer = new ExpertGamer(123, "luca");

    @Test
    void initGamer() {
        ArrayList<Student> students = new ArrayList<Student>();
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.GREEN));
        students.add(new Student(PawnColor.RED));
        students.add(new Student(PawnColor.BLUE));
        int towers = 6;
        expertGamer.initGamer(students, towers);
        assertTrue(expertGamer.getDashboard().getWaitingRoom().containsAll(students));
        assertEquals(towers, expertGamer.getDashboard().getNumTowers());
    }

    @Test
    void selectCloud() {
        Bag bag = new Bag();
        ArrayList<Student> students = new ArrayList<Student>(bag.pullStudents(1));
        expertGamer.initGamer(students, 6);
        Cloud cloud = new Cloud(1);
        ArrayList<Student> students1 = new ArrayList<Student>(bag.pullStudents(3));
        cloud.pushStudents(students1);
        expertGamer.selectCloud(cloud);
        assertTrue(expertGamer.getDashboard().getWaitingRoom().containsAll(students1));
    }

    @Test
    void getDashboard() {
        Bag bag = new Bag();
        ArrayList<Student> students = new ArrayList<Student>(bag.pullStudents(1));
        expertGamer.initGamer(students, 6);
        assertTrue(expertGamer.getDashboard() instanceof ExpertDashboard);
        assertEquals(expertGamer.getDashboard().getCoins(), 0);
        expertGamer.getDashboard().setCoins(2);
        assertEquals(expertGamer.getDashboard().getCoins(), 2);
        expertGamer.getDashboard().moveStudent(new Student(PawnColor.BLUE));
        expertGamer.getDashboard().moveStudent(new Student(PawnColor.BLUE));
        assertEquals(expertGamer.getDashboard().getCoins(), 2);
        expertGamer.getDashboard().moveStudent(new Student(PawnColor.BLUE));
        assertEquals(expertGamer.getDashboard().getCoins(), 3);
    }
}