package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag = new Bag();

    @Test
    void pullStudents() {
        ArrayList<Student> students = new ArrayList<>(bag.pullStudents(3));
        assertEquals(3,students.size());
        System.out.println("generic pull completed");
        students.addAll(bag.pullStudents(120));
        assertEquals(120, students.size());
        System.out.println("pull with overflow value completed");
        students.addAll(bag.pullStudents(15));
        assertEquals(120,students.size());
        System.out.println("dry pull completed");
        for(PawnColor color : PawnColor.values()){
            int numStudents = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(color)).count());
            assertEquals(24, numStudents);
        }
        System.out.println("test on students completed");
    }

    @Test
    void pushStudent() {
        Student student = new Student(PawnColor.GREEN);
        Student student1 = new Student(PawnColor.RED);
        Student student2 = new Student(PawnColor.PINK);
        Student student3 = new Student(PawnColor.YELLOW);
        Student student4 = new Student(PawnColor.BLUE);
        bag.pullStudents(130);
        bag.pushStudent(student);
        assertTrue(bag.pullStudents(1).contains(student));
        bag.pushStudent(student1);
        assertTrue(bag.pullStudents(1).contains(student1));
        bag.pushStudent(student2);
        assertTrue(bag.pullStudents(1).contains(student2));
        bag.pushStudent(student3);
        assertTrue(bag.pullStudents(1).contains(student3));
        bag.pushStudent(student4);
        assertTrue(bag.pullStudents(1).contains(student4));
    }

    @Test
    void isEmpty() {
        bag.pullStudents(130);
        assertTrue(bag.isEmpty());
    }
}