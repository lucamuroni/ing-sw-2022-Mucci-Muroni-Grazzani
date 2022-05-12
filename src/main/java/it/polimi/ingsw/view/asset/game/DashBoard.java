package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import java.util.ArrayList;

public class DashBoard {
    private final Gamer gamer;
    private ArrayList<Student> waitingRoom;
    private ArrayList<Student> hall;
    private int numTower;
    private ArrayList<PawnColor> professors;

    public DashBoard(Gamer gamer){
        this.gamer = gamer;
    }

    public void updateDashBoard(int numTower, ArrayList<Student> studentsToWaitingRoom, ArrayList<Student> studentsToHall, ArrayList<PawnColor> professors){
        this.numTower = numTower;
        this.waitingRoom.clear();
        this.waitingRoom.addAll(studentsToWaitingRoom);
        this.hall.clear();
        this.hall.addAll(studentsToHall);
        this.professors.clear();
        this.professors.addAll(professors);
    }

    public void moveStudentToHall(Student student) throws AssetErrorException {
        this.waitingRoom.remove(this.waitingRoom.stream().filter(x->x.getColor().equals(student.getColor())).findFirst().orElseThrow(AssetErrorException::new));
        this.hall.add(student);
    }
    
    public void moveStudentToIsland(Island island){}

}
