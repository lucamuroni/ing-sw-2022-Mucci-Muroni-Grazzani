package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Gamer;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.dashboard.StudentNotFoundException;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    @Test
    void moveTower() {
        ArrayList<Student> students = new ArrayList<Student>();
        int torri = 5;
        assertEquals(true, students.isEmpty());
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
        ArrayList<Student> s = new ArrayList<Student>();    //arraylist generico di studenti.
        assertEquals(true, s.isEmpty());
        ArrayList<Student> studentsToAddCloud = new ArrayList<Student>();
        assertEquals(true, studentsToAddCloud.isEmpty());
        ArrayList<Student> studentsToAddWaitingRoom = new ArrayList<Student>();
        assertEquals(true, studentsToAddWaitingRoom.isEmpty());
        ArrayList<Student> students = new ArrayList<Student>(); //studenti presenti sulla dashboard.
        assertEquals(true, students.isEmpty());
        students.addAll(borsa.pullStudents(4));
        assertEquals(false, students.isEmpty());
        Dashboard dashboard = new Dashboard(students, 5);
        studentsToAddCloud.addAll(borsa.pullStudents(3));
        assertEquals(false, studentsToAddCloud.isEmpty());
        assertEquals(3, studentsToAddCloud.size());
        cloud.pushStudents(studentsToAddCloud);
        assertEquals(false, cloud.isEmpty());
        studentsToAddWaitingRoom.addAll(cloud.pullStudent());
        dashboard.addStudentsWaitingRoom(studentsToAddWaitingRoom);
        s.addAll(students);
        s.addAll(studentsToAddWaitingRoom);
        assertEquals(s, dashboard.getWaitingRoom());

    }

    @Test
    void checkInfluence() {
        Gamer gamer = new Gamer();
        ArrayList<Student> students = new ArrayList<Student>();
        assertEquals(true, students.isEmpty());
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.PINK);
        Student student3 = new Student(PawnColor.BLUE);
        Student student4 = new Student(PawnColor.BLUE);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        assertEquals(false, students.isEmpty());
        Dashboard dashboard = new Dashboard(students, 5);
        try {
            dashboard.moveStudent(student1);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 1 lanciata.");
        }
        try {
            dashboard.moveStudent(student2);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 2 lanciata.");
        }
        try {
            dashboard.moveStudent(student3);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 3 lanciata.");
        }
        try {
            dashboard.moveStudent(student4);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 4 lanciata.");
        }
        PawnColor color = PawnColor.BLUE;
        assertEquals(2, dashboard.checkInfluence(color));
    }

    @Test
    void moveStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        assertEquals(true, students.isEmpty());
        Student student1 = new Student(PawnColor.RED);
        Student student2 = new Student(PawnColor.BLUE);
        students.add(student1);
        assertEquals(false, students.isEmpty());
        Dashboard dashboard = new Dashboard(students, 7);
        try {
            dashboard.moveStudent(student1);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 1 lanciata.");
        }
        try {
            dashboard.moveStudent(student2);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 2 lanciata.");
        }
    }

    @Test
    void testMoveStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        assertEquals(true, students.isEmpty());
        Island island = new Island();
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.RED);
        students.add(student1);
        Dashboard dashboard = new Dashboard(students, 7);
        try {
            dashboard.moveStudent(student1, island);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 1 lanciata.");
        }
        try {
            dashboard.moveStudent(student2, island);
        } catch (StudentNotFoundException e) {
            System.out.println("Eccezione 2 lanciata.");
        }
    }

    @Test
    void getNumTowers() {
        ArrayList<Student> students = new ArrayList<Student>();
        assertEquals(true, students.isEmpty());
        Dashboard dashboard = new Dashboard(students, 5);
        Dashboard dashboard1 = new Dashboard(students, 7);
        assertEquals(5, dashboard.getNumTowers());
        assertNotEquals(5, dashboard1.getNumTowers());
    }
}