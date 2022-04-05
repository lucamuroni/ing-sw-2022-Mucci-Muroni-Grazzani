package it.polimi.ingsw.model;

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
        assertTrue(expertGamer.getExpertDashboard().getStudents().containsAll(students));
        assertEquals(towers, expertGamer.getExpertDashboard().getTowers());
    }

    @Test
    void selectCloud(){
        Bag bag = new Bag();
        ArrayList<Student> students = new ArrayList<Student>(bag.pullStudents(1));
        expertGamer.initGamer(students, 6);
        Cloud cloud = new Cloud();
        ArrayList<Student> students1 = new ArrayList<Student>(bag.pullStudents(3));
        cloud.pushStudents(students1);
        expertGamer.selectCloud(cloud);
        assertTrue(expertGamer.getExpertDashboard().getStudents().containsAll(students1));
    }
}