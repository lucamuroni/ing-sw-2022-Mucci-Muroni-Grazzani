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
     * @param students represent the initial students that are present at the start of the game in the waiting room of a Gamer
     * @param numTowers represent the initial number of towers that are present at the start of the game in the waiting room of a Gamer
     */
    public ExpertDashboard(ArrayList<Student> students, int numTowers){
        super(students, numTowers);
        this.coins = 1;
    }

    /**
     * Getter method
     * @return the coins possessed by the player
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
     * Method used to move a Student from the staging area to a Professor table
     * @param student represent a Student in the staging area (waitingRoom) which must be moved
     */
    @Override
    public void moveStudent(Student student){
        this.hall.add(student);
        this.waitingRoom.remove(student);
        checkCoins(student);
    }

    /**
     * Method used to check if the insertion generates a new coin
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

    public Student removeStudentFromHall(PawnColor color) {
        Student student = this.hall.stream().filter(x->x.getColor().equals(color)).findFirst().orElse(null);
        this.hall.remove(student);
        return student;
    }

    public void setGame(ExpertGame game){
        this.game = game;
    }
}
