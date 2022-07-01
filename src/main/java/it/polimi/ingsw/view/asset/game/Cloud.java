package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class represents a cloud
 */
public class Cloud {
    private final int id;
    ArrayList<Student> students;

    /**
     * Constructor of the class
     * @param id identifies the cloud
     */
    public Cloud(int id){
        this.id = id;
        this.students = new ArrayList<>();
    }

    /**
     * This update method is used when the server notifies that the cloud is now empty
     */
    public void update() {
        this.students.clear();
    }

    /**
     * This update method is used when the server notifies that the cloud has been filled again
     * @param students is the arrayList containing the new students that are on the cloud
     */
    public void update(ArrayList<Student> students) {
        this.students.clear();
        if(!students.isEmpty()){
            this.students.addAll(students);
        }
    }

    /**
     * Getter method
     * @return the id associated with the cloud
     */
    public int getId() {
        return id;
    }

    /**
     * GetterMethod
     * @return the arrayList of students that are on the cloud
     */
    public ArrayList<Student> getStudents() {
        return students;
    }
}