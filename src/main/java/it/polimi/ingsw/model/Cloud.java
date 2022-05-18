package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements a colud.
 */

public class Cloud {
    private ArrayList<Student> students;
    private final int id;

    /**
     * Class constructor
     * @param id is the unique id given to a Cloud
     */
    public Cloud(int id) {
        this.students = new ArrayList<Student>();
        this.id = id;
    }

    /**
     * method that returns the students on the cloud and empties the cloud.
     * @return students present on the cloud.
     */
    public ArrayList<Student> pullStudent() {
        ArrayList<Student> cloudStudent;
        cloudStudent = new ArrayList<Student>(students);
        students.clear();
        return cloudStudent;
    }

    /**
     * Method that adds students to the cloud.
     * @param studentsToAdd represents students that have to be added to the cloud.
     */
    public void pushStudents(ArrayList<Student> studentsToAdd) {
        for (Student student: studentsToAdd) {
            students.add(student);
        }
    }

    /**
     * @return true if the cloud is empty.
     */
    public boolean isEmpty() {
        return (students.isEmpty());
    }

    public Integer getID(){
        return this.id;
    }
}
