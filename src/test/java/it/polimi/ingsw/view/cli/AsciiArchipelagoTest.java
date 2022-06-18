package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
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
        asciiArchipelago.mergeIsland(2,3);
        try {
            asciiArchipelago.draw();
        } catch (AssetErrorException e) {
            System.out.println("Errore");
        }
        asciiArchipelago.mergeIsland(2,4);
        try {
            asciiArchipelago.draw();
        } catch (AssetErrorException e) {
            System.out.println("Errore");
        }
        asciiArchipelago.mergeIsland(5,6);
        try {
            asciiArchipelago.draw();
        } catch (AssetErrorException e) {
            System.out.println("Errore");
        }
        asciiArchipelago.mergeIsland(6,7);
        try {
            asciiArchipelago.draw();
        } catch (AssetErrorException e) {
            System.out.println("Errore");
        }
        asciiArchipelago.mergeIsland(5,8);
        try {
            asciiArchipelago.draw();
        } catch (AssetErrorException e) {
            System.out.println("Errore");
        }
        asciiArchipelago.mergeIsland(5,9);
        try {
            asciiArchipelago.draw();
        } catch (AssetErrorException e) {
            System.out.println("Errore");
        }
    }
    @Test
    void allineamento() {
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
        asciiIslands.get(1).getIsland().setMotherNaturePresent(true);
        asciiIslands.get(2).getIsland().setMotherNaturePresent(true);
        asciiIslands.get(9).getIsland().setMotherNaturePresent(true);
        asciiIslands.get(0).getIsland().setTowerPresent();
        asciiIslands.get(0).getIsland().updateOwner(TowerColor.BLACK);
        asciiIslands.get(1).getIsland().setTowerPresent();
        asciiIslands.get(1).getIsland().updateOwner(TowerColor.BLACK);
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