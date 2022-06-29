package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CloudTest {
    @Test
    void pullStudent(){
        Cloud cloud = new Cloud(1);
        Bag borsa = new Bag();
        assertTrue(cloud.isEmpty());
        cloud.pushStudents(borsa.pullStudents(3));
        assertFalse(cloud.isEmpty());
        ArrayList<Student> studenti = new ArrayList<>(cloud.pullStudent());
        assertTrue(cloud.isEmpty());
        assertEquals(3, studenti.size());
        System.out.println("Pull completed.");
    }

    @Test
    void pushStudents() {
        Bag borsa = new Bag();
        Cloud cloud = new Cloud(2);
        ArrayList<Student> studenti = new ArrayList<>(borsa.pullStudents(3));
        assertEquals(3, studenti.size());
        for (Student s: studenti) {
            cloud.pushStudents(studenti);
        }
        System.out.println("Push completed.");
    }

    @Test
    void isEmpty() {
        Cloud cloud = new Cloud(1);
        assertTrue(cloud.isEmpty());
        System.out.println("The cloud is empty.");
    }
}
