package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Student;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements a colud.
 */

public class Cloud {
    private ArrayList<Student> students;

    /**
     * Class constructor
     */
    public Cloud() {
        this.students = new ArrayList<Student>();
    }

    /**
     * method that returns the students on the cloud and empties the cloud.
     * @return students present on the cloud.
     */
    public ArrayList<Student> pullStudent() {
        ArrayList<Student> cloudStudent;
        cloudStudent = new ArrayList<Student>(students);
        for (Student student: students) {
            cloudStudent.add(student);
        }
        students.clear();
        return cloudStudent;
    }

    /**
     * @param studentsToAdd represents students that have to be added at the cloud.
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
}
