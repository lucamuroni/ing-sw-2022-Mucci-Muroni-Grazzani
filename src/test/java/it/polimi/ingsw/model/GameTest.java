package it.polimi.ingsw.model;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;
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
        assertFalse(students.isEmpty());
        Cloud cloud = new Cloud();
        game.fillCloud(students, cloud);
        assertFalse(cloud.isEmpty());
        assertEquals(students.size(), cloud.pullStudent().size());
        assertTrue(cloud.pullStudent().containsAll(students));
    }
    @Test
    void initIsland() {
        ArrayList<Student> students = new ArrayList<Student>(game.getBag().pullStudents(10));
        assertFalse(students.isEmpty());
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
        MotherNature motherNature = new MotherNature(game.getIslands().get(0));
        game.moveMotherNature(game.getIslands().get(1));
        assertEquals(game.getIslands().get(1), motherNature.getPlace());
    }

    @Test
    void getMotherNatureDestination() {
        Gamer gamer = new Gamer(123, "nome");
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        AssistantCard assistantCard = new AssistantCard(1, 3);
        gamer.getDeck().setCurrentSelection(assistantCard);
        int n = gamer.getDeck().getCurrentSelection().getMovement();
        Island island = new Island();
        Island island1 = new Island();
        Island island2 = new Island();
        ArrayList<Island> islands = new ArrayList<Island>();
        assertTrue(islands.isEmpty());
        islands.add(island);
        islands.add(island1);
        islands.add(island2);
        assertEquals(islands, game.getMotherNatureDestination());
    }

    @Test
    void changeProfessorOwner() throws Exception {
        Student student = new Student(PawnColor.BLUE);
        Student student1 = new Student(PawnColor.BLUE);
        Gamer gamer = new Gamer(123, "nome");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamer.getDashboard().hall.add(student);
        gamer2.getDashboard().hall.add(student1);
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        assertTrue(gamers.isEmpty());
        gamers.add(gamer);
        gamers.add(gamer2);
        assertFalse(gamers.isEmpty());
        assertEquals(2, gamers.size());
        Game game = new Game(gamers);
        Professor professor = new Professor(PawnColor.BLUE);
        professor.setOwner(gamer);
        Student s =new Student(PawnColor.BLUE);
        ArrayList<Student> studentToAdd = new ArrayList<Student>();
        studentToAdd.add(s);
        gamer2.getDashboard().addStudentsWaitingRoom(studentToAdd);
        gamer2.getDashboard().moveStudent(s);
        assertEquals(gamer2, game.changeProfessorOwner(PawnColor.BLUE));
    }

    @Test
    void checkIslandOwner() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        assertTrue(gamers.isEmpty());
        Gamer gamer = new Gamer(123, "nome");
        Gamer gamer1 = new Gamer(456, "nome2");
        gamers.add(gamer);
        gamers.add(gamer1);
        Student student = new Student(PawnColor.BLUE);
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.BLUE);
        gamer.getDashboard().hall.add(student);
        gamer.getDashboard().hall.add(student1);
        gamer1.getDashboard().hall.add(student2);
        Game game = new Game(gamers);
        Island island = new Island();
        MotherNature mn = new MotherNature(island);
        mn.getPlace().setOwner(gamer1);
        assertEquals(gamer, game.checkIslandOwner());
        Island island1 = new Island();
        MotherNature mn2 = new MotherNature(island1);
        mn2.getPlace().setOwner(gamer);
        assertEquals(gamer, game.checkIslandOwner());
    }

    @Test
    void updatePlayersOrder() {
        //Devono ancora essere fatte delle modifiche in Game
    }

    @Test
    void getMotherNature() {
        MotherNature motherNature = new MotherNature(new Island());
        assertEquals(motherNature, game.getMotherNature());
    }

    @Test
    void getClouds() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        assertTrue(gamers.isEmpty());
        Gamer gamer1 = new Gamer(123, "nome");
        Gamer gamer2 = new Gamer(456, "nome2");
        Gamer gamer3 = new Gamer(789, "nome3");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(2, game.getClouds().size());
    }

    @Test
    void getProfs() {
        assertEquals(5, game.getProfessors().size());
        //DUBBIO: Controllare i colori?
    }

    @Test
    void getIslands() {
        assertEquals(12, game.getIslands().size());
    }

    @Test
    void getBag() {
        Bag bag = new Bag();
        assertEquals(bag, game.getBag());
    }

    @Test
    void getGamers() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        assertTrue(gamers.isEmpty());
        Gamer gamer1 = new Gamer(123, "nome");
        Gamer gamer2 = new Gamer(456, "nome2");
        Gamer gamer3 = new Gamer(789, "nome3");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(gamers, game.getGamers());
        assertFalse(game.getGamers().contains(gamer3));
    }

    @Test
    void getCurrentPlayer() {
        Gamer gamer = new Gamer(123, "luca");
        game.setCurrentPlayer(gamer);
        Gamer currentPlayer = game.getCurrentPlayer();
        assertEquals(gamer, currentPlayer);
    }

    @Test
    void setCurrentPlayer() {
        Gamer gamer = new Gamer(123, "luca");
        game.setCurrentPlayer(gamer);
        assertEquals(gamer, game.getCurrentPlayer());
    }

    @Test
    void getTurnNumber() {
        int turnNumber = 1;
        game.getTurnNumber();
        assertEquals(1, turnNumber);
    }

    @Test
    void setTurnNumber() {
        int turnNumber = 0;
        game.setTurnNumber();
        assertEquals(1, turnNumber);
    }
}