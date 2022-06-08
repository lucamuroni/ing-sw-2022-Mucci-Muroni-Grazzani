package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.asset.game.Island;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsciiIslandTest {

    @Test
    void draw() {
        Island island = new Island(2);
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.YELLOW));
        AsciiIsland asciiIsland = new AsciiIsland(island);
        island.setTowerPresent();
        island.updateOwner(TowerColor.WHITE);
        asciiIsland.draw();
        System.out.print("\n\n\n");
        island.setMotherNaturePresent(true);
        island.addStudent(new Student(PawnColor.GREEN));
        asciiIsland.draw();
    }

    @Test
    void testDraw() {
    }
}