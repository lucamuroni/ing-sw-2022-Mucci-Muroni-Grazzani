package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void pullStudents() {
        Bag borsa = new Bag();
        ArrayList<Student> students = new ArrayList<Student>();
        students.addAll(borsa.pullStudents(3));
        assertEquals(3,students.size());
        System.out.println("generic pull completed");
        students.addAll(borsa.pullStudents(130));
        assertEquals(130,students.size());
        System.out.println("pull with overflow value completed");
        students.addAll(borsa.pullStudents(15));
        assertEquals(130,students.size());
        System.out.println("dry pull completed");
        for(PawnColor color : PawnColor.values()){
            int numStudents = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(color)).count());
            assertEquals(26,numStudents);
        }
        System.out.println("test on students completed");
    }
}