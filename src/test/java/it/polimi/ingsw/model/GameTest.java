package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Cloud;
import it.polimi.ingsw.debug.Gamer;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest{
    ArrayList<Gamer> gamers = new ArrayList<Gamer>();
    Game game = new Game(gamers);

    @Test
    void fillCloud(){
        Bag bag = new Bag();
        ArrayList<Student> students = new ArrayList<Student>(bag.pullStudents(4));
        Cloud cloud = new Cloud();
        game.fillCloud(students, cloud);
    }
    @Test
    void initIsland() {
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