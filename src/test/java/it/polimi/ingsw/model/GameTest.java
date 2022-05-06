package it.polimi.ingsw.model;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameTest{
    @Test
    void fillCloud(){
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        ArrayList<Student> students = new ArrayList<Student>();
        students.addAll(game.getBag().pullStudents(4));
        assertFalse(students.isEmpty());
        assertEquals(4, students.size());
        Cloud cloud = new Cloud();
        game.fillCloud(students, cloud);
        ArrayList<Student> s = new ArrayList<Student>(cloud.pullStudent());
        assertFalse(s.isEmpty());
        assertEquals(students.size(), s.size());
        assertTrue(s.containsAll(students));

    }
    @Test
    void initIsland() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(12, game.getIslands().size());
        ArrayList<Student> students = new ArrayList<Student>(game.getBag().pullStudents(10));
        ArrayList<Student> s = new ArrayList<Student>(students);
        game.initIsland(students);
        assertEquals(0, students.size());
        Island motherNatureIsland = game.getMotherNature().getPlace();
        int motherNatureIndex = game.getIslands().indexOf(motherNatureIsland);
        Island oppositeIsland = game.getIslands().get((motherNatureIndex + 6) % 12);
        int i = 0;
        for (Island island: game.getIslands()) {
            if (island.equals(motherNatureIsland) || island.equals(oppositeIsland)) {
                continue;
            }
            else {
                assertEquals(s.get(i), island.getStudents().get(0));
                i++;
            }
        }
    }

    @Test
    void moveMotherNature() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(12, game.getIslands().size());
        MotherNature motherNature = game.getMotherNature();
        motherNature.setPlace(game.getIslands().get(0));
        assertEquals(motherNature.getPlace(), game.getIslands().get(0));
        game.moveMotherNature(game.getIslands().get(1));
        assertEquals(game.getIslands().get(1), motherNature.getPlace());
    }

    @Test
    void getMotherNatureDestination() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        gamer1.initGamer(students, 7);
        gamers.add(gamer1);
        Game game = new Game(gamers);
        game.setCurrentPlayer(gamer1);
        gamer1.getDeck().setCurrentSelection(gamer1.getDeck().getCardList().get(0));
        ArrayList<Island> islands = new ArrayList<Island>();
        assertTrue(islands.isEmpty());
        game.getMotherNature().setPlace(game.getIslands().get(1));
        islands.add(game.getIslands().get(2));
        assertEquals(islands, game.getMotherNatureDestination());
        islands.clear();
        game.getMotherNature().setPlace(game.getIslands().get(4));
        islands.add(game.getIslands().get(5));
        assertEquals(islands, game.getMotherNatureDestination());
        islands.clear();
        game.getMotherNature().setPlace(game.getIslands().get(11));
        islands.add(game.getIslands().get(0));
        assertEquals(islands, game.getMotherNatureDestination());
    }

    @Test
    void changeProfessorOwner() throws Exception {
        Student student = new Student(PawnColor.BLUE);
        Student student1 = new Student(PawnColor.BLUE);
        Gamer gamer1 = new Gamer(123, "nome1");
        ArrayList<Student> students = new ArrayList<Student>();
        assertTrue(students.isEmpty());
        gamer1.initGamer(students, 7);
        Gamer gamer2 = new Gamer(456, "nome2");
        gamer2.initGamer(students, 7);
        gamer1.getDashboard().hall.add(student);
        gamer2.getDashboard().hall.add(student1);
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        assertTrue(gamers.isEmpty());
        gamers.add(gamer1);
        gamers.add(gamer2);
        assertFalse(gamers.isEmpty());
        assertEquals(2, gamers.size());
        Game game = new Game(gamers);
        game.setCurrentPlayer(gamer2);
        game.getProfessors().get(0).setOwner(gamer1);
        Student s = new Student(PawnColor.BLUE);
        ArrayList<Student> studentToAdd = new ArrayList<Student>();
        studentToAdd.add(s);
        gamer2.getDashboard().addStudentsWaitingRoom(studentToAdd);
        gamer2.getDashboard().moveStudent(s);
        assertEquals(gamer2, game.changeProfessorOwner(PawnColor.BLUE));
    }

    @Test
    void checkIslandOwner() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        ArrayList<Student> students = new ArrayList<Student>();
        Gamer gamer1 = new Gamer(123, "nome");
        Gamer gamer2 = new Gamer(456, "nome2");

        gamer1.initGamer(students, 7);
        gamer2.initGamer(students, 7);
        gamers.add(gamer1);
        gamers.add(gamer2);

        Game game = new Game(gamers);

        Student student = new Student(PawnColor.BLUE);
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.BLUE);

        game.setCurrentPlayer(gamer1);
        gamer1.getDashboard().moveStudent(student2);
        try{
            game.changeProfessorOwner(PawnColor.BLUE);
        } catch (Exception e){
            e.printStackTrace();
        }
        assertEquals(Optional.of(game.getCurrentPlayer()), game.getProfessors().stream().filter(x->x.getColor().equals(PawnColor.BLUE)).findFirst().get().getOwner());

        game.setCurrentPlayer(gamer2);
        gamer2.getDashboard().moveStudent(student);
        gamer2.getDashboard().moveStudent(student1);
        try{
            game.changeProfessorOwner(PawnColor.BLUE);
        } catch (Exception e){
            e.printStackTrace();
        }
        assertEquals(Optional.of(game.getCurrentPlayer()), game.getProfessors().stream().filter(x->x.getColor().equals(PawnColor.BLUE)).findFirst().get().getOwner());

        Island islandToCheck = game.getMotherNature().getPlace();
        islandToCheck.addStudents(new Student(PawnColor.BLUE));
        islandToCheck.addStudents(new Student(PawnColor.RED));
        assertEquals(1, gamer1.getDashboard().checkInfluence(PawnColor.BLUE));
        assertEquals(2, gamer2.getDashboard().checkInfluence(PawnColor.BLUE));
        ArrayList<PawnColor> colors = new ArrayList<PawnColor>();
        colors.add(PawnColor.BLUE);
        assertEquals(1, islandToCheck.getInfluenceByColor(colors));
        //TODO: aggiustare metodo
        assertEquals(game.checkIslandOwner(), Optional.of(game.getCurrentPlayer()));

        /*
        game.getMotherNature().getPlace().setOwner(gamer2);
        game.getMotherNature().getPlace().addStudents(new Student(PawnColor.BLUE));
        assertEquals(gamer2, game.getMotherNature().getPlace().getOwner().get());
        //game.getMotherNature().getPlace().setOwner(game.getCurrentPlayer());
        game.getIslands().get(game.getIslands().indexOf(game.getMotherNature().getPlace())).addTower();
        assertEquals(1, game.getMotherNature().getPlace().getNumTowers());
        //assertEquals(gamer, game.checkIslandOwner().get());
        Student student3 = new Student(PawnColor.BLUE);
        Student student4 = new Student(PawnColor.BLUE);
        gamer2.getDashboard().hall.add(student3);
        gamer2.getDashboard().hall.add(student4);
        //assertEquals(2, gamer2.getDashboard().checkInfluence(PawnColor.BLUE));
        assertEquals(3, gamer2.getDashboard().checkInfluence(PawnColor.BLUE));
        assertEquals(gamer2, game.checkIslandOwner().get());
        */
    }

    @Test
    void updatePlayersOrder() {
        //Devono ancora essere fatte delle modifiche in Game
    }

    @Test
    void getMotherNature() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        MotherNature motherNature = game.getMotherNature();
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
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(5, game.getProfessors().size());
        //DUBBIO: Controllare i colori?
    }

    @Test
    void getIslands() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(12, game.getIslands().size());
    }

    @Test
    void getBag() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        Bag bag = game.getBag();
        assertEquals(bag, game.getBag());
    }

    @Test
    void getGamers() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        //assertTrue(gamers.isEmpty());
        Gamer gamer1 = new Gamer(123, "nome");
        Gamer gamer2 = new Gamer(456, "nome2");
        Gamer gamer3 = new Gamer(789, "nome3");
        gamers.add(gamer1);
        gamers.add(gamer2);
        assertEquals(2, gamers.size());
        Game game = new Game(gamers);
        assertEquals(2, game.getGamers().size());
        assertTrue(game.getGamers().containsAll(gamers));
        assertFalse(game.getGamers().contains(gamer3));
    }

    @Test
    void getCurrentPlayer() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        Gamer gamer = new Gamer(123, "luca");
        gamers.add(gamer1);
        gamers.add(gamer2);
        gamers.add(gamer);
        Game game = new Game(gamers);
        game.setCurrentPlayer(gamer);
        Gamer currentPlayer = game.getCurrentPlayer();
        assertEquals("luca", game.getCurrentPlayer().getUsername());
        assertEquals(gamer, currentPlayer);
    }

    @Test
    void setCurrentPlayer() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        Gamer gamer = new Gamer(123, "luca");
        gamers.add(gamer1);
        gamers.add(gamer2);
        gamers.add(gamer);
        Game game = new Game(gamers);
        game.setCurrentPlayer(gamer);
        assertEquals("luca", game.getCurrentPlayer().getUsername());
        assertEquals(gamer, game.getCurrentPlayer());
    }

    @Test
    void getTurnNumber() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        game.setTurnNumber();
        assertEquals(1, game.getTurnNumber());
    }

    @Test
    void setTurnNumber() {
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        Gamer gamer1 = new Gamer(123, "nome1");
        Gamer gamer2 = new Gamer(456, "nome2");
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        game.setTurnNumber();
        assertEquals(1, game.getTurnNumber());
    }
}