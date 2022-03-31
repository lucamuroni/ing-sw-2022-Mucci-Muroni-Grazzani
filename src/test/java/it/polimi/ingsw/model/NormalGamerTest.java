package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NormalGamerTest {

    @Test
    public void selectCloud() {
    }

    @Test
    void initGamer() {
        NormalGamer normalGamer = new NormalGamer(123, "Luca");
        ArrayList<Student> students = new ArrayList<Student>();
        Student s0 = new Student(PawnColor.PINK);
        Student s1 = new Student(PawnColor.BLUE);
        Student s2 = new Student(PawnColor.YELLOW);
        Student s3 = new Student(PawnColor.GREEN);
        students.add(s1);
        students.add(s2);
        students.add(s3);
        int towers = 6;
        normalGamer.initGamer(students, towers);
        assertEquals(3, normalGamer.getDashboard().getWaitingRoom().size());
        assertEquals(6, normalGamer.getDashboard().getTowers());
        assertEquals(false, normalGamer.getDashboard().getWaitingRoom().contains(s0));
        assertEquals(true,normalGamer.getDashboard().getWaitingRoom().containsAll(students));
        assertEquals(students,normalGamer.getDashboard().getWaitingRoom());
    }
}