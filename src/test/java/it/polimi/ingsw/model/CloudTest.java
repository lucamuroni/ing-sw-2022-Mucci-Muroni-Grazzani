package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CloudTest {
    Cloud cloud = new Cloud(1);
    Bag bag = new Bag();

    @Test
    void pullStudent(){
        assertTrue(cloud.isEmpty());
        cloud.pushStudents(bag.pullStudents(3));
        assertFalse(cloud.isEmpty());
        ArrayList<Student> students = new ArrayList<>(cloud.pullStudent());
        assertTrue(cloud.isEmpty());
        assertEquals(3, students.size());
        System.out.println("Pull completed.");
    }

    @Test
    void pushStudents() {
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(3));
        assertEquals(3, students.size());
        cloud.pushStudents(students);
        assertEquals(3, cloud.pullStudent().size());
        System.out.println("Push completed.");
    }

    @Test
    void isEmpty() {
        assertTrue(cloud.isEmpty());
        System.out.println("The cloud is empty.");
    }

    @Test
    void getID() {
        assertEquals(1, cloud.getID());
    }

    @Test
    void getStudents() {
        cloud.pushStudents(bag.pullStudents(3));
        assertEquals(3, cloud.getStudents().size());
        ArrayList<Student> students = new ArrayList<>(cloud.getStudents());
        assertEquals(3, students.size());
        assertEquals(students, cloud.getStudents());
    }
}
