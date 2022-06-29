package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that represents the dashboard of the player when he plays the expert version of the game.
 * It differs from his father class only for the presence of the coins.
 */
public class ExpertDashboard extends Dashboard{
    private int coins;
    private ExpertGame game;

    /**
     * Class constructor
     * @param students represents the students that are present at start of the game in the gamer waiting room
     * @param numTowers represents the initial number of towers that are present at start of the game in the gamer waiting room
     */
    public ExpertDashboard(ArrayList<Student> students, int numTowers){
        super(students, numTowers);
        this.coins = 1;
    }

    /**
     * Method that returns the number of coins possessed by the gamer
     * @return the number of coins
     */
    public int getCoins(){
        return this.coins;
    }

    /**
     * This method is called by the player when he gets some coins from his dashboard or
     * when he uses them to play a CharacterCard
     * @param numCoins is the number of coins that the player receives/uses (this int can be either positive or negative)
     */
    public void setCoins(int numCoins){
        this.coins += numCoins;
    }

    /**
     * Method used to move a student from waitingRoom to hall
     * @param student represents a student in waitingRoom that must be moved
     */
    @Override
    public void moveStudent(Student student){
        this.hall.add(student);
        this.waitingRoom.remove(student);
        checkCoins(student);
    }

    /**
     * Method used to check if the insertion of a student into the hall generates a new coin
     * @param student is the student that has been added
     */
    private void checkCoins(Student student){
        int number = Math.toIntExact(hall.stream().filter(stud -> stud.getColor().equals(student.getColor())).count());
        if(number%3==0) {
            if(this.game.getCoinBank()>0){
                setCoins(1);
                this.game.setCoinBank(-1);
            }
        }
    }

    /**
     * Method used to remove a student from hall
     * @param color is the color of the student that must be removed
     * @return the removed student
     */
    public Student removeStudentFromHall(PawnColor color) {
        Student student = this.hall.stream().filter(x->x.getColor().equals(color)).findFirst().orElse(null);
        this.hall.remove(student);
        return student;
    }

    /**
     * Method used to create a link between an expertDashboard and an expertGame
     * @param game is the current game
     */
    public void setGame(ExpertGame game){
        this.game = game;
    }
}
