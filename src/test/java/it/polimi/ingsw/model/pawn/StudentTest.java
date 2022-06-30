package it.polimi.ingsw.model.pawn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {
    @Test
    void genericTest(){
        Student student = new Student(PawnColor.BLUE);
        assertEquals(PawnColor.BLUE,student.getColor());
    }
}