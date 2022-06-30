package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * @author Davide Grazzani
 * Class that represents a cloud
 */
public class Cloud {
    private final ArrayList<Student> students;
    private final int id;

    /**
     * Class constructor
     * @param id is the unique id associated with a cloud
     */
    public Cloud(int id) {
        this.students = new ArrayList<>();
        this.id = id;
    }

    /**
     * Method that returns the students on a cloud and, at the same time, it empties the cloud
     * @return students present on the cloud.
     */
    public ArrayList<Student> pullStudent() {
        ArrayList<Student> cloudStudent;
        cloudStudent = new ArrayList<>(students);
        students.clear();
        return cloudStudent;
    }

    /**
     * Method that adds students to the cloud
     * @param studentsToAdd represents students that must be put on the cloud
     */
    public void pushStudents(ArrayList<Student> studentsToAdd) {
        students.addAll(studentsToAdd);
    }

    /**
     * Method used to check is a cloud is empty or not
     * @return true if the cloud is empty, false otherwise
     */
    public boolean isEmpty() {
        return (students.isEmpty());
    }

    /**
     * Method used to get the id associated with a cloud
     * @return the unique id of a cloud
     */
    public Integer getID(){
        return this.id;
    }

    /**
     * Method used to get the students on the cloud
     * @return a copy of the students on a cloud
     */
    public ArrayList<Student> getStudents(){
        return new ArrayList<>(this.students);
    }
}
