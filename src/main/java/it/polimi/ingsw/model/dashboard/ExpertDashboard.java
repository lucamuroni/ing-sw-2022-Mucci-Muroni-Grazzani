package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public class ExpertDashboard extends Dashboard{
    private int coins;

    public ExpertDashboard(ArrayList<Student> students, int towers){
        super(students, towers);
        this.coins = 0;
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
    public void setCoins(int numCoins) throws CoinsException {
        if(this.coins + numCoins<0)
            throw new CoinsException("Coins aren't enough to play this card");
        this.coins = this.coins + numCoins;
    }

    /**
     * Method used to move a Student from the staging area to a Professor table
     * @param student represent a Student in the staging area (waitingRoom) which must be moved
     * @throws StudentNotFoundException
     */
    @Override
    public void moveStudent(Student student) throws StudentNotFoundException {
        if(!this.waitingRoom.contains(student)){
            throw new StudentNotFoundException("Student not founded in the waiting room");
        }
        this.hall.add(student);
        this.waitingRoom.remove(student);
        try{
            checkCoins(student);
        }catch (CoinsException e){
            e.printStackTrace();
        }
    }

    private void checkCoins(Student student) throws CoinsException {
        int number = Math.toIntExact(hall.stream().filter(stud -> stud.getColor().equals(student.getColor())).count());
        if(number%3==0)
            setCoins(1);
    }
}
