package it.polimi.ingsw.model;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.influenceCalculator.InfluenceCalculator;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class GameTest{
    ArrayList<Gamer> gamers = new ArrayList<>();
    Cloud cloud = new Cloud(1);

    @Test
    void fillCloud(){
        Gamer gamer1 = new Gamer(123, "nome1", TowerColor.BLACK);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        ArrayList<Student> students = new ArrayList<>(game.getBag().pullStudents(4));
        assertFalse(students.isEmpty());
        assertEquals(4, students.size());
        game.fillCloud(students, cloud);
        ArrayList<Student> s = new ArrayList<>(cloud.pullStudent());
        assertFalse(s.isEmpty());
        assertEquals(students.size(), s.size());
        assertTrue(s.containsAll(students));

    }
    @Test
    void initIsland() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.WHITE);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.BLACK);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(12, game.getIslands().size());
        ArrayList<Student> students = new ArrayList<>(game.getBag().pullStudents(10));
        ArrayList<Student> s = new ArrayList<>(students);
        assertFalse(students.isEmpty());
        assertFalse(s.isEmpty());
        game.initIsland(students);
        assertTrue(students.isEmpty());
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
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.WHITE);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.WHITE);
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
        Gamer gamer1 = new Gamer(123, "nome1", TowerColor.BLACK);
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 7);
        gamers.add(gamer1);
        Game game = new Game(gamers);
        game.setCurrentPlayer(gamer1);
        gamer1.getDeck().setCurrentSelection(gamer1.getDeck().getCardList().get(0));
        ArrayList<Island> islands = new ArrayList<>();
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
    void changeProfessorOwner() {
        Student student = new Student(PawnColor.BLUE);
        Student student1 = new Student(PawnColor.BLUE);
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 7);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamer2.initGamer(students, 7);
        gamer1.getDashboard().hall.add(student);
        gamer2.getDashboard().hall.add(student1);
        gamers.add(gamer1);
        gamers.add(gamer2);
        assertEquals(2, gamers.size());
        Game game = new Game(gamers);
        game.setCurrentPlayer(gamer2);
        game.getProfessors().get(0).setOwner(gamer1);
        Student s = new Student(PawnColor.BLUE);
        ArrayList<Student> studentToAdd = new ArrayList<>();
        studentToAdd.add(s);
        gamer2.getDashboard().addStudentsWaitingRoom(studentToAdd);
        gamer2.getDashboard().moveStudent(s);
        assertEquals(gamer2, game.changeProfessorOwner(PawnColor.BLUE));
    }

    @Test
    void checkIslandOwner() {
        ArrayList<Student> students = new ArrayList<>();
        Student student = new Student(PawnColor.BLUE);
        Student student1 = new Student(PawnColor.BLUE);
        Student student2 = new Student(PawnColor.BLUE);
        students.add(student2);
        ArrayList<Student> students2 = new ArrayList<>();
        students2.add(student);
        students2.add(student1);
        Gamer gamer1 = new Gamer(123, "nome",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamer1.initGamer(students, 7);
        gamer2.initGamer(students2, 7);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
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
        ArrayList<PawnColor> colors = new ArrayList<>();
        colors.add(PawnColor.BLUE);
        assertEquals(1, islandToCheck.getInfluenceByColor(colors));
        assertEquals(game.checkIslandOwner(), Optional.of(game.getCurrentPlayer()));
        game.getMotherNature().getPlace().setOwner(gamer2);
        game.getMotherNature().getPlace().addStudents(new Student(PawnColor.BLUE));
        assertEquals(gamer2, game.getMotherNature().getPlace().getOwner().get());
        game.getMotherNature().getPlace().setOwner(game.getCurrentPlayer());
        game.getIslands().get(game.getIslands().indexOf(game.getMotherNature().getPlace())).addTower();
        assertEquals(2, game.getMotherNature().getPlace().getNumTowers());
        assertEquals(gamer2, game.checkIslandOwner().get());
        Student student3 = new Student(PawnColor.BLUE);
        Student student4 = new Student(PawnColor.BLUE);
        gamer2.getDashboard().hall.add(student3);
        gamer2.getDashboard().hall.add(student4);
        assertEquals(4, gamer2.getDashboard().checkInfluence(PawnColor.BLUE));
        assertEquals(gamer2, game.checkIslandOwner().get());

    }

    /*@Test
    void updatePlayersOrder() {
        Student student = new Student(PawnColor.BLUE);
        Student student1 = new Student(PawnColor.BLUE);
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 6);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamer2.initGamer(students, 6);
        gamer1.getDashboard().hall.add(student);
        gamer1.getDeck().setCurrentSelection(AssistantCard.LEOPARD);
        gamer2.getDashboard().hall.add(student1);
        gamer2.getDeck().setCurrentSelection(AssistantCard.CAT);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        gamer1.getDeck().setPastSelection();
        gamer2.getDeck().setPastSelection();
        gamer1.getDeck().setCurrentSelection(AssistantCard.CAT);
        gamer2.getDeck().setCurrentSelection(AssistantCard.LEOPARD);
        game.updatePlayersOrder();
        assertEquals(gamer2, game.getCurrentPlayer());
    }*/

    @Test
    void getMotherNature() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        MotherNature motherNature = game.getMotherNature();
        assertEquals(motherNature, game.getMotherNature());
    }

    @Test
    void getClouds() {
        Gamer gamer1 = new Gamer(123, "nome",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        Gamer gamer3 = new Gamer(789, "nome3",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(2, game.getClouds().size());
        gamers.add(gamer3);
        Game game1 = new Game(gamers);
        assertEquals(3, game1.getClouds().size());
    }

    @Test
    void getProfessors() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(5, game.getProfessors().size());
        assertEquals(PawnColor.BLUE, game.getProfessors().get(0).getColor());
        assertEquals(PawnColor.PINK, game.getProfessors().get(1).getColor());
        assertEquals(PawnColor.YELLOW, game.getProfessors().get(2).getColor());
        assertEquals(PawnColor.RED, game.getProfessors().get(3).getColor());
        assertEquals(PawnColor.GREEN, game.getProfessors().get(4).getColor());
    }

    @Test
    void getIslands() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        assertEquals(12, game.getIslands().size());
    }

    @Test
    void getBag() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        Bag bag = game.getBag();
        assertEquals(bag, game.getBag());
    }

    @Test
    void getGamers() {
        Gamer gamer1 = new Gamer(123, "nome",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        Gamer gamer3 = new Gamer(789, "nome3",TowerColor.GREY);
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
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        Gamer gamer = new Gamer(123, "luca",TowerColor.GREY);
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
    void getTurnNumber() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        game.setTurnNumber();
        assertEquals(1, game.getTurnNumber());
    }

    @Test
    void getInfluenceCalculator() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        InfluenceCalculator calculator = game.getInfluenceCalculator();
        assertEquals(calculator, game.getInfluenceCalculator());
    }

    /*@Test
    void checkWinner() {
        Gamer gamer1 = new Gamer(123, "nome1", TowerColor.GREY);
        ArrayList<Student> students = new ArrayList<>();
        gamer1.initGamer(students, 6);
        Gamer gamer2 = new Gamer(456, "nome2", TowerColor.GREY);
        gamer2.initGamer(students, 6);
        gamers.add(gamer1);
        Game game = new Game(gamers);
        assertEquals(gamer1, game.checkWinner().get(0));
        gamers.add(gamer2);
        Game game1 = new Game(gamers);
        gamer1.getDashboard().moveTower(-6);
        assertEquals(gamer1, game1.checkWinner().get(0));
        Gamer gamer3 = new Gamer(1, "nome", TowerColor.BLACK);
        Gamer gamer4 = new Gamer(2, "nome", TowerColor.WHITE);
        ArrayList<Gamer> gamers1 = new ArrayList<>();
        gamers1.add(gamer3);
        gamers1.add(gamer4);
        Game game2 = new Game(gamers1);
        gamer3.initGamer(students, 4);
        gamer4.initGamer(students, 6);
        assertEquals(gamer3, game2.checkWinner().get(0));
        Gamer gamer5 = new Gamer(1, "nome", TowerColor.WHITE);
        Gamer gamer6 = new Gamer(2, "nome", TowerColor.GREY);
        ArrayList<Gamer> gamers3 = new ArrayList<>();
        gamers3.add(gamer5);
        gamers3.add(gamer6);
        Game game3 = new Game(gamers3);
        gamer5.initGamer(students, 6);
        gamer6.initGamer(students, 6);
        game.getProfessors().get(0).setOwner(gamer5);
        game.getProfessors().get(1).setOwner(gamer5);
        game.getProfessors().get(2).setOwner(gamer5);
        game.getProfessors().get(3).setOwner(gamer6);
        game.getProfessors().get(4).setOwner(gamer6);
        assertEquals(gamer5, game3.checkWinner().get(0));
    }*/

    @Test
    void getProfessorsByGamer() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        game.getProfessors().get(0).setOwner(gamer1);
        game.getProfessors().get(1).setOwner(gamer1);
        ArrayList<Professor> p = new ArrayList<>();
        p.add(game.getProfessors().get(0));
        p.add(game.getProfessors().get(1));
        assertEquals(p, game.getProfessorsByGamer(gamer1));
    }

    @Test
    void setCurrentPlayer() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        Gamer gamer = new Gamer(123, "luca",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        gamers.add(gamer);
        Game game = new Game(gamers);
        game.setCurrentPlayer(gamer);
        assertEquals("luca", game.getCurrentPlayer().getUsername());
        assertEquals(gamer, game.getCurrentPlayer());
    }

    @Test
    void setTurnNumber() {
        Gamer gamer1 = new Gamer(123, "nome1",TowerColor.GREY);
        Gamer gamer2 = new Gamer(456, "nome2",TowerColor.GREY);
        gamers.add(gamer1);
        gamers.add(gamer2);
        Game game = new Game(gamers);
        game.setTurnNumber();
        assertEquals(1, game.getTurnNumber());
    }
}