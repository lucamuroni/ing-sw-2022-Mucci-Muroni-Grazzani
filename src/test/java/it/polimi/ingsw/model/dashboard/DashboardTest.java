package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    @Test
    void moveTower() {
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        int torri = 5;
        Dashboard dashboard = new Dashboard(students, torri);
        dashboard.moveTower(1);
        assertEquals(6, dashboard.getNumTowers());
        dashboard.moveTower(-2);
        assertEquals(4, dashboard.getNumTowers());
    }

    @Test
    void addStudentsWaitingRoom() {
        Bag borsa = new Bag();
        Cloud cloud = new Cloud();
        ArrayList<Student> s = new ArrayList<Student>();
        assertTrue(s.isEmpty());
        ArrayList<Student> studentsToAddCloud = new ArrayList<Student>();
        assertTrue(studentsToAddCloud.isEmpty());
        ArrayList<Student> studentsToAddWaitingRoom = new ArrayList<Student>();
        assertTrue(studentsToAddWaitingRoom.isEmpty());
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        students.addAll(borsa.pullStudents(4));
        assertFalse(students.isEmpty());
        assertEquals(4, students.size());
        Dashboard dashboard = new Dashboard(students, 7);
        studentsToAddCloud.addAll(borsa.pullStudents(3));
        assertFalse(studentsToAddCloud.isEmpty());
        assertEquals(3, studentsToAddCloud.size());
        cloud.pushStudents(studentsToAddCloud);
        assertFalse(cloud.isEmpty());
        studentsToAddWaitingRoom.addAll(cloud.pullStudent());
        dashboard.addStudentsWaitingRoom(studentsToAddWaitingRoom);
        s.addAll(students);
        s.addAll(studentsToAddWaitingRoom);
        assertEquals(s, dashboard.getWaitingRoom());

    }

    @Test
    void checkInfluence() {
        Gamer gamer = new Gamer(123, "nome");
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.PINK);
        Student student3 = new Student(PawnColor.BLUE);
        Student student4 = new Student(PawnColor.BLUE);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        assertFalse(students.isEmpty());
        Dashboard dashboard = new Dashboard(students, 7);
        dashboard.moveStudent(student1);
        dashboard.moveStudent(student2);
        dashboard.moveStudent(student3);
        //dashboard.moveStudent(student4);
        PawnColor color = PawnColor.BLUE;
        assertEquals(2, dashboard.checkInfluence(color));
    }

    @Test
    void moveStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        Student student1 = new Student(PawnColor.RED);
        Student student2 = new Student(PawnColor.BLUE);
        students.add(student1);
        assertFalse(students.isEmpty());
        Dashboard dashboard = new Dashboard(students, 7);
        dashboard.moveStudent(student1);
        //dashboard.moveStudent(student2);
        assertEquals(1, dashboard.hall.size());
    }

    @Test
    void testMoveStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        Island island = new Island();
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.RED);
        students.add(student1);
        Dashboard dashboard = new Dashboard(students, 7);
        dashboard.moveStudent(student1, island);
        //dashboard.moveStudent(student2, island);
        //assertEquals(1, island.getStudents().size());     //getStudents() isn't public.
    }

    @Test
    void getNumTowers() {
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        Dashboard dashboard = new Dashboard(students, 5);
        Dashboard dashboard1 = new Dashboard(students, 7);
        assertEquals(5, dashboard.getNumTowers());
        assertNotEquals(5, dashboard1.getNumTowers());
    }
}