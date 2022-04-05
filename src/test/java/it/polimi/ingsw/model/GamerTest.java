package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GamerTest {

    @Test
    public void selectCloud() {
        Cloud cloud = new Cloud();

    }

    @Test
    void initGamer() {
        Gamer gamer = new Gamer(123, "Luca");
        ArrayList<Student> students = new ArrayList<Student>();
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
        assertEquals(6, gamer.getDashboard().getTowers());
        assertEquals(false, gamer.getDashboard().getWaitingRoom().contains(s0));
        assertEquals(true, gamer.getDashboard().getWaitingRoom().containsAll(students));
        assertEquals(students, gamer.getDashboard().getWaitingRoom());
    }

    @Test
    void getDeck() {

    }

    @Test
    void getToken() {

    }

    @Test
    void getUsername() {

    }

    @Test
    void getDashboard() {

    }

    @Test
    void isActive() {
        Gamer gamer = new Gamer(1, "nome");
        assertEquals(true, gamer.isActive());
    }

    @Test
    void setActivity() {
        boolean activity = true;
    }
}