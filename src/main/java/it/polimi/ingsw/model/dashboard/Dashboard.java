package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * Class that represent a gamer dashboard, witch include the gamer's towers, the students present at the entrace and
 * the professor's table
 */

public class Dashboard {
    private ArrayList<Student> waitingRoom;
    private int towers;
    private ArrayList<Student> hall;

    public Dashboard(ArrayList<Student> students, int numTowers){
        this.waitingRoom = new ArrayList<Student>(students);
        this.towers = numTowers;
        this.hall = new ArrayList<Student>();
    }

    public void moveTower(int number){
        this.towers += number;
    }

    public void addStudentsWaitingRoom(ArrayList<Student> students){
        this.waitingRoom.addAll(students);
    }

    public int checkInfluence(PawnColor color){
        int result = Math.toIntExact(this.hall.stream().filter(x -> x.getColor().equals(color)).count());
        return result;
    }

    public void moveStudent(Student student) throws StudentNotFoundException{
        if(!this.waitingRoom.contains(student)){
            throw new StudentNotFoundException("Student not founded in the waiting room");
        }
        this.hall.add(student);
    }

    public void moveStudent(Student student, Island island) throws StudentNotFoundException{
        if(!this.waitingRoom.contains(student)){
            throw new StudentNotFoundException("Student not founded in the waiting room");
        }
        island.addStudents(student);
        this.waitingRoom.remove(student);
    }
}
