package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {
    ArrayList<Student> students = new ArrayList<>();
    Bag bag = new Bag();
    Cloud cloud = new Cloud(1);
    Island island = new Island(1);

    @Test
    void moveTower() {
        Dashboard dashboard = new Dashboard(students, 5);
        dashboard.moveTower(1);
        assertEquals(6, dashboard.getNumTowers());
        dashboard.moveTower(-2);
        assertEquals(4, dashboard.getNumTowers());
    }

    @Test
    void addStudentsWaitingRoom() {
        ArrayList<Student> s = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(4));
        assertFalse(students.isEmpty());
        assertEquals(4, students.size());
        Dashboard dashboard = new Dashboard(students, 7);
        ArrayList<Student> studentsToAddCloud = new ArrayList<>(bag.pullStudents(3));
        assertFalse(studentsToAddCloud.isEmpty());
        assertEquals(3, studentsToAddCloud.size());
        cloud.pushStudents(studentsToAddCloud);
        assertFalse(cloud.isEmpty());
        assertEquals(3, cloud.getStudents().size());
        ArrayList<Student> studentsToAddWaitingRoom = new ArrayList<>(cloud.pullStudent());
        dashboard.addStudentsWaitingRoom(studentsToAddWaitingRoom);
        s.addAll(students);
        s.addAll(studentsToAddWaitingRoom);
        assertEquals(s, dashboard.getWaitingRoom());
    }

    @Test
    void checkInfluence() {
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.PINK);
        Student student3 = new Student(PawnColor.BLUE);
        Student student4 = new Student(PawnColor.BLUE);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        Dashboard dashboard = new Dashboard(students, 7);
        dashboard.moveStudent(student1);
        dashboard.moveStudent(student2);
        dashboard.moveStudent(student3);
        PawnColor color = PawnColor.BLUE;
        assertEquals(2, dashboard.checkInfluence(color));
        dashboard.moveStudent(student4);
        assertEquals(3, dashboard.checkInfluence(color));
    }

    @Test
    void moveStudent() {
        Student student1 = new Student(PawnColor.RED);
        Student student2 = new Student(PawnColor.BLUE);
        students.add(student1);
        Dashboard dashboard = new Dashboard(students, 7);
        dashboard.moveStudent(student1);
        assertEquals(1, dashboard.getHall().size());
        dashboard.moveStudent(student2);
        assertEquals(2, dashboard.getHall().size());
    }

    @Test
    void testMoveStudent() {
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.RED);
        students.add(student1);
        students.add(student2);
        Dashboard dashboard = new Dashboard(students, 7);
        dashboard.moveStudent(student1, island);
        assertEquals(1, island.getStudents().size());
        dashboard.moveStudent(student2, island);
        assertEquals(2, island.getStudents().size());
    }

    @Test
    void getNumTowers() {
        Dashboard dashboard = new Dashboard(students, 5);
        Dashboard dashboard1 = new Dashboard(students, 7);
        assertEquals(5, dashboard.getNumTowers());
        assertNotEquals(5, dashboard1.getNumTowers());
    }

    @Test
    void getWaitingRoom() {
        students.add(new Student(PawnColor.BLUE));
        students.add(new Student(PawnColor.PINK));
        Dashboard dashboard = new Dashboard(students, 6);
        assertEquals(students, dashboard.getWaitingRoom());
    }

    @Test
    void getHall() {
        students.add(new Student(PawnColor.BLUE));
        Dashboard dashboard = new Dashboard(students, 6);
        dashboard.moveStudent(students.get(0));
        assertEquals(students, dashboard.getHall());
        students.add(new Student(PawnColor.RED));
        dashboard.moveStudent(students.get(1));
        assertEquals(students, dashboard.getHall());
    }
}