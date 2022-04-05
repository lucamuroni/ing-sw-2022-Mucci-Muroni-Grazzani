package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.debug.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest{
    ArrayList<Gamer> gamers = new ArrayList<Gamer>();
    Game game = new Game(gamers);

    @Test
    void fillCloud(){
        ArrayList<Student> students = new ArrayList<Student>(game.getBag().pullStudents(4));
        Cloud cloud = new Cloud();
        game.fillCloud(students, cloud);
        assertTrue(!cloud.isEmpty());
        assertEquals(students.size(), cloud.pullStudent().size());
        assertTrue(cloud.pullStudent().containsAll(students));
    }
    @Test
    void initIsland() {
        ArrayList<Student> students = new ArrayList<Student>(game.getBag().pullStudents(10));
        game.initIsland(students);
        MotherNature motherNature = new MotherNature(game.getIslands().get(0));
        ArrayList<PawnColor> colors = new ArrayList<PawnColor>();
        colors.add(PawnColor.BLUE);
        colors.add(PawnColor.GREEN);
        colors.add(PawnColor.YELLOW);
        colors.add(PawnColor.RED);
        colors.add(PawnColor.PINK);
        int cont = 0;
        for(Island island : game.getIslands()){
            cont = cont + island.getInfluenceByColor(colors);
        }
        assertEquals(cont, students.size());
    }

    @Test
    void moveMotherNature() {
    }

    @Test
    void getMotherNatureDestination() {
    }

    @Test
    void changeProfessorOwner() {
    }

    @Test
    void checkIslandOwner() {
    }

    @Test
    void updatePlayersOrder() {
    }

    @Test
    void getMotherNature() {
    }

    @Test
    void getClouds() {
    }

    @Test
    void getProfs() {
    }

    @Test
    void getIslands() {
    }

    @Test
    void getBag() {
    }

    @Test
    void getGamers() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void setCurrentPlayer() {
    }

    @Test
    void getTurnNumber() {
    }

    @Test
    void setTurnNumber() {
    }
}