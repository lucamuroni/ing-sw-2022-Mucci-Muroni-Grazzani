package it.polimi.ingsw;

import it.polimi.ingsw.debug.Student;
import it.polimi.ingsw.debug.StudentsAdder;
import it.polimi.ingsw.debug.Tower;

import java.util.ArrayList;

public class Island implements IslandInterface{
    private ArrayList<Tower> towers;
    private ArrayList<StudentsAdder> students;

    public Island(){
        this.towers = new ArrayList<Tower>();
        students = new ArrayList<StudentsAdder>(5);
        int i = 0;
        for (PawnColor color : PawnColor.values()){
            StudentsAdder student = new StudentsAdder(color);
            this.students.add(student);
        }
    }

    public void addStudents(Student student){
        for(StudentsAdder students : this.students){
            if(students.getColor().equals(student.getColor())){
                students.addStudent();
            }
        }
    }

    public void mergeIsland(Island island){
        for(StudentsAdder students : this.students){
            System.out.println("Prima :"+students.getNumStudents()+"studenti di colore"+students.getColor().toString());
            for(StudentsAdder otherstudents : island.getStudents()){
                System.out.println("Studenti da aggiungere :"+otherstudents.getNumStudents()+"studenti di colore"+otherstudents.getColor().toString());
                if(students.getColor().equals(otherstudents.getColor())){
                    students.addStudent(otherstudents.getNumStudents());
                }
            }
            System.out.println("Dopo :"+students.getNumStudents()+"studenti di colore"+students.getColor().toString());
        }
        System.out.println("torri prima : "+towers.size());
        this.towers.addAll(island.getTowers());
        /**
         * QUESTION
         * aggiungendo elementi ad un arrayList questi venogono duplicati oppure si passa solo il riferminto?
         */
        System.out.println("torri dopo : "+towers.size());
    }

    public void refreshIslandState(){

    }

    private ArrayList<StudentsAdder> getStudents(){
        return this.students;
    }

    private ArrayList<Tower> getTowers(){
        return this.towers;
    }
}
