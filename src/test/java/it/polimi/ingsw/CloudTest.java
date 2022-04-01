package it.polimi.ingsw;

import it.polimi.ingsw.debug.Student;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Cloud;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class CloudTest {
    @Test
    void pullStudent(){
        Cloud cloud = new Cloud();
        ArrayList<Student> studenti = new ArrayList<Student>();
        studenti.addAll(cloud.pullStudent());
        System.out.println("pull completed.");
        System.out.println("test completed.");
    }

    @Test
    void pushStudents() {
        Bag borsa = new Bag();
        ArrayList<Student> studenti = new ArrayList<Student>();
        Cloud cloud = new Cloud();
        studenti.addAll(borsa.pullStudents(3));
        for (Student s: studenti) {
            cloud.pushStudents(studenti);
        }
        System.out.println("test completato.");
    }

    @Test
    void isEmpty() {
        Cloud cloud = new Cloud();
        assert(cloud.isEmpty() == true);
    }
}
