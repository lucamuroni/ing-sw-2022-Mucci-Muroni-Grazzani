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
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.YELLOW));
        students.add(new Student(PawnColor.GREEN));
        int towers = 6;
        normalGamer.initGamer(students, towers);
        assertEquals(3, normalGamer.getDashboard().getWaitingRoom().size());
        assertEquals(6, normalGamer.getDashboard().getTowers());
    }
}