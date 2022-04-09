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
    void getCoins() throws CoinsException {
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.GREEN));
        students.add(new Student(PawnColor.PINK));
        students.add(new Student(PawnColor.RED));
        ExpertDashboard expertDashboard = new ExpertDashboard(students, towers);
        assertEquals(0, expertDashboard.getCoins());
        expertDashboard.setCoins(2);
        assertEquals(2, expertDashboard.getCoins());
    }

    @Test
    void setCoins() throws CoinsException {
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.GREEN));
        students.add(new Student(PawnColor.PINK));
        students.add(new Student(PawnColor.RED));
        ExpertDashboard expertDashboard = new ExpertDashboard(students, towers);
        expertDashboard.setCoins(2);
        assertEquals(2, expertDashboard.getCoins());
        expertDashboard.setCoins(-2);
        assertEquals(0, expertDashboard.getCoins());
        try {
            expertDashboard.setCoins(-2);
        }catch (CoinsException e){
            System.out.println("Coins <0");
        }
    }

    @Test
    void moveStudent(){
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.RED));
        students.add(new Student(PawnColor.BLUE));
        ExpertDashboard expertDashboard = new ExpertDashboard(students, towers);
        try {
            expertDashboard.moveStudent(students.get(0));
            expertDashboard.moveStudent(students.get(1));
            expertDashboard.moveStudent(students.get(2));
            //expertDashboard.moveStudent(students.get(3));
        }catch (StudentNotFoundException e){
            System.out.println("Eccezione 1 lanciata");
        }
        assertEquals(0, expertDashboard.getCoins());

        try {
            expertDashboard.moveStudent(students.get(3));
        }catch (StudentNotFoundException e){
            System.out.println("Eccezione 2 lanciata.");
        }
        assertEquals(1, expertDashboard.getCoins());

    }
}