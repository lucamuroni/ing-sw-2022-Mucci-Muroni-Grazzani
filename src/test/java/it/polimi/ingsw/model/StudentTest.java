package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void genericTest(){
        Student student = new Student(PawnColor.BLUE);
        assertEquals(PawnColor.BLUE,student.getColor());
    }
}