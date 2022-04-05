package it.polimi.ingsw.model;

import it.polimi.ingsw.model.dashboard.Dashboard;
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
    }

    @Test
    void addStudentsWaitingRoom() {
        ArrayList<Student> students = new ArrayList<Student>();
        Dashboard dashboard = new Dashboard(students, 5);
        ArrayList<Student> studentsToAdd = new ArrayList<Student>();
        Cloud cloud = new Cloud();
        studentsToAdd.addAll();
    }

    @Test
    void checkInfluence() {
    }

    @Test
    void moveStudent() {
    }

    @Test
    void testMoveStudent() {
    }
}