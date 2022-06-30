package it.polimi.ingsw.model;

import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GamerTest {
    Gamer gamer = new Gamer(1, "luca", TowerColor.GREY);
    Bag bag = new Bag();
    Cloud cloud = new Cloud(1);

    @Test
    public void selectCloud() {
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(1));
        gamer.initGamer(students, 6);
        ArrayList<Student> students1 = new ArrayList<>(bag.pullStudents(3));
        cloud.pushStudents(students1);
        gamer.selectCloud(cloud);
        assertTrue(gamer.getDashboard().getWaitingRoom().containsAll(students1));
    }

    @Test
    void initGamer() {
        ArrayList<Student> students = new ArrayList<>();
        Student s0 = new Student(PawnColor.PINK);
        Student s1 = new Student(PawnColor.BLUE);
        Student s2 = new Student(PawnColor.YELLOW);
        Student s3 = new Student(PawnColor.GREEN);
        students.add(s1);
        students.add(s2);
        students.add(s3);
        int towers = 6;
        gamer.initGamer(students, towers);
        assertEquals(3, gamer.getDashboard().getWaitingRoom().size());
        assertEquals(6, gamer.getDashboard().getNumTowers());
        assertFalse(gamer.getDashboard().getWaitingRoom().contains(s0));
        assertTrue(gamer.getDashboard().getWaitingRoom().containsAll(students));
        assertEquals(students, gamer.getDashboard().getWaitingRoom());
    }

    @Test
    void getDeck() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(PawnColor.BLUE));
        gamer.initGamer(students, 3);
        AssistantCardDeck deck = gamer.getDeck();
        assertNotNull(deck);
    }

    @Test
    void getToken() {
        assertEquals(1, gamer.getToken());
    }

    @Test
    void getUsername() {
        assertEquals("luca", gamer.getUsername());
    }

    @Test
    void getDashboard() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(PawnColor.BLUE));
        gamer.initGamer(students, 3);
        Dashboard dashboard = gamer.getDashboard();
        assertEquals(dashboard.getNumTowers(), gamer.getDashboard().getNumTowers());
        assertEquals(dashboard.getWaitingRoom(), gamer.getDashboard().getWaitingRoom());
    }

    @Test
    void getTowerColor() {
        assertEquals(TowerColor.GREY, gamer.getTowerColor());
    }

    @Test
    void isActive() {
        assertTrue(gamer.isActive());
    }

    @Test
    void setInactivity() {
        gamer.setInActivity();
        assertFalse(gamer.isActive());
    }
}