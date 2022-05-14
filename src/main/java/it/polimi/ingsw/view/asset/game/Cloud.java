package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * This class represents a Cloud
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
        this.students = new ArrayList<Student>();
    }

    /**
     * This method is used in client's view to show the remove of students from the cloud
     * @return
     */
    public ArrayList<Student> removeStudents(){
        ArrayList<Student> result = new ArrayList<Student>(this.students);
        this.students.clear();
        return result;
    }

    /**
     * This update method is used when the server notifies that the cloud is now empty
     */
    public void update() {
        this.students.clear();
    }

    /**
     * This update method is used when the server notifies that the cloud has been filled again
     * @param students is the array containing the new students that are on the cloud
     */
    public void update(ArrayList<Student> students) {
        if (students.isEmpty()) {
            this.students.clear();
        } else {
            this.students.addAll(students);
        }
    }
}
