package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Island;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AsciiArchipelagoTest {

    @Test
    void draw() {
        ArrayList<AsciiIsland> asciiIslands = new ArrayList<>();
        asciiIslands.add(new AsciiIsland(new Island(1)));
        asciiIslands.add(new AsciiIsland(new Island(2)));
        asciiIslands.add(new AsciiIsland(new Island(3)));
        asciiIslands.add(new AsciiIsland(new Island(4)));
        asciiIslands.add(new AsciiIsland(new Island(5)));
        asciiIslands.add(new AsciiIsland(new Island(6)));
        asciiIslands.add(new AsciiIsland(new Island(7)));
        asciiIslands.add(new AsciiIsland(new Island(8)));
        asciiIslands.add(new AsciiIsland(new Island(9)));
        asciiIslands.add(new AsciiIsland(new Island(10)));
        asciiIslands.add(new AsciiIsland(new Island(11)));
        asciiIslands.add(new AsciiIsland(new Island(12)));
        asciiIslands.get(0).getIsland().setMotherNaturePresent(true);
        //asciiIslands.get(0).getIsland().setTowerPresent();
        asciiIslands.get(0).getIsland().addStudent(new Student(PawnColor.RED));
        AsciiArchipelago asciiArchipelago = new AsciiArchipelago(asciiIslands);
        try {
            asciiArchipelago.draw();
        } catch (AssetErrorException e) {
            System.out.println("Errore");
        }
    }
}