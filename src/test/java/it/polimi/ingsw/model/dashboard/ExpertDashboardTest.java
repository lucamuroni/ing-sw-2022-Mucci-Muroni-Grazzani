package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExpertDashboardTest {
    ArrayList<Student> students = new ArrayList<Student>();
    int towers = 4;

    @Test
    void getCoins() {
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.GREEN));
        students.add(new Student(PawnColor.PINK));
        students.add(new Student(PawnColor.RED));
        ExpertDashboard expertDashboard = new ExpertDashboard(students, towers);
        assertEquals(1, expertDashboard.getCoins());
        expertDashboard.setCoins(2);
        assertEquals(3, expertDashboard.getCoins());
    }

    @Test
    void setCoins(){
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.GREEN));
        students.add(new Student(PawnColor.PINK));
        students.add(new Student(PawnColor.RED));
        ExpertDashboard expertDashboard = new ExpertDashboard(students, towers);
        expertDashboard.setCoins(2);
        assertEquals(3, expertDashboard.getCoins());
        expertDashboard.setCoins(-2);
        assertEquals(1, expertDashboard.getCoins());
        expertDashboard.setCoins(-2);
        assertEquals(-1, expertDashboard.getCoins());
    }

    @Test
    void moveStudent(){
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.RED));
        students.add(new Student(PawnColor.BLUE));
        ExpertDashboard expertDashboard = new ExpertDashboard(students, towers);
        expertDashboard.moveStudent(students.get(0));
        expertDashboard.moveStudent(students.get(1));
        expertDashboard.moveStudent(students.get(2));
        assertEquals(1, expertDashboard.getCoins());
    }
}