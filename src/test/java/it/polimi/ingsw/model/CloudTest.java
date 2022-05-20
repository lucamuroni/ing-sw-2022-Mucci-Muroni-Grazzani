package it.polimi.ingsw.model;


import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class CloudTest {
    @Test
    void pullStudent(){
        Cloud cloud = new Cloud(1);
        Bag borsa = new Bag();
        assertEquals(true,cloud.isEmpty());
        ArrayList<Student> studenti = new ArrayList<Student>();
        cloud.pushStudents(borsa.pullStudents(3));
        assertEquals(false, cloud.isEmpty());
        assertEquals(true, studenti.isEmpty());
        studenti.addAll(cloud.pullStudent());
        assertEquals(true, cloud.isEmpty());
        assertEquals(3, studenti.size());
        System.out.println("Pull completed.");
    }

    @Test
    void pushStudents() {
        Bag borsa = new Bag();
        ArrayList<Student> studenti = new ArrayList<Student>();
        Cloud cloud = new Cloud(2);
        studenti.addAll(borsa.pullStudents(3));
        assertEquals(3, studenti.size());
        for (Student s: studenti) {
            cloud.pushStudents(studenti);
        }
        System.out.println("Push completed.");
    }

    @Test
    void isEmpty() {
        Cloud cloud = new Cloud(1);
        assertEquals(true, cloud.isEmpty());
        System.out.println("The cloud is empty.");
    }
}
