package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ExpertDashboardTest {
    ArrayList<Student> students = new ArrayList<>();

    @Test
    void getCoins() {
        ExpertDashboard expertDashboard = new ExpertDashboard(students, 6);
        assertEquals(1, expertDashboard.getCoins());
        expertDashboard.setCoins(2);
        assertEquals(3, expertDashboard.getCoins());
    }

    @Test
    void setCoins(){
        ExpertDashboard expertDashboard = new ExpertDashboard(students, 6);
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
        ExpertDashboard expertDashboard = new ExpertDashboard(students, 6);
        expertDashboard.moveStudent(students.get(0));
        expertDashboard.moveStudent(students.get(1));
        expertDashboard.moveStudent(students.get(2));
        assertEquals(1, expertDashboard.getCoins());
        assertEquals(students, expertDashboard.getHall());
    }

    @Test
    void removeStudentFromHall() {
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.RED));
        ExpertDashboard expertDashboard = new ExpertDashboard(students, 6);
        expertDashboard.moveStudent(students.get(0));
        expertDashboard.moveStudent(students.get(1));
        expertDashboard.moveStudent(students.get(2));
        Student s = expertDashboard.getHall().get(2);
        expertDashboard.removeStudentFromHall(PawnColor.RED);
        assertFalse(expertDashboard.getHall().contains(students.get(2)));
    }

    @Test
    void setGame() {
        ArrayList<Gamer> gamers = new ArrayList<>();
        gamers.add(new Gamer(1, "nome", TowerColor.GREY));
        gamers.add(new Gamer(2, "nome", TowerColor.WHITE));
        ExpertGame expertGame = new ExpertGame(gamers);
        ExpertDashboard dashboard = new ExpertDashboard(students, 6);
        dashboard.setGame(expertGame);
    }
}