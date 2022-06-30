package it.polimi.ingsw.model;

import it.polimi.ingsw.model.dashboard.ExpertDashboard;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ExpertGamerTest {
    ExpertGamer expertGamer = new ExpertGamer(123, "luca", TowerColor.GREY);
    Bag bag = new Bag();
    Cloud cloud = new Cloud(1);

    @Test
    void selectCloud() {
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(1));
        expertGamer.initGamer(students, 6);
        ArrayList<Student> students1 = new ArrayList<>(bag.pullStudents(3));
        cloud.pushStudents(students1);
        expertGamer.selectCloud(cloud);
        assertTrue(expertGamer.getDashboard().getWaitingRoom().containsAll(students1));
    }

    @Test
    void initGamer() {
        ArrayList<Student> students = new ArrayList<>();
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
    void getDashboard() {
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(1));
        expertGamer.initGamer(students, 6);
        assertNotNull(expertGamer.getDashboard());
    }

    @Test
    void coins(){
        Bag bag = new Bag();
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(5));
        ExpertGamer gamer1 = new ExpertGamer(1,"d",TowerColor.GREY);
        ExpertGamer gamer2 = new ExpertGamer(2,"p",TowerColor.BLACK);
        gamer1.initGamer(students,1);
        gamer2.initGamer(students,2);
        ArrayList<Gamer> gamers = new ArrayList<>();
        gamers.add(gamer1);
        gamers.add(gamer2);
        ExpertGame game = new ExpertGame(gamers);
        gamer1.getDashboard().setGame(game);
        gamer2.getDashboard().setGame(game);
        assertEquals(1,gamer1.getDashboard().getCoins());
        gamer1.getDashboard().moveStudent(new Student(PawnColor.RED));
        gamer1.getDashboard().moveStudent(new Student(PawnColor.RED));
        assertEquals(1,gamer1.getDashboard().getCoins());
        gamer1.getDashboard().moveStudent(new Student(PawnColor.RED));
        assertEquals(2,gamer1.getDashboard().getCoins());
        assertEquals(17,game.getCoinBank());
    }
}