package it.polimi.ingsw.debug;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.MotherNature;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.Random;

public class Test {
    Bag borsa;
    MotherNature madrenatura;
    ArrayList<Student> students;
    ArrayList<Island> islands;

    public Test(){
        borsa = new Bag();
        for(int i=0;i<2;i++){
            Island isola = new Island();
            islands.add(isola);
        }
        madrenatura = new MotherNature(islands.get(0));
        students = new ArrayList<Student>();
    }


    public static void main(){
        Test test = new Test();
        int i = test.test();
        if(i != 0){
            System.out.println("Something went wrong : Test Failed");
            return;
        }
        System.out.println("Test Passed");
        return;
    }

    public int test(){
        students.addAll(borsa.pullStudents(3));
        if(students.size()!=3){
            System.out.println("Error occurred when grabbing students from bag");
            System.out.println("Wrong size returned");
            return 1;
        }
        students.addAll(borsa.pullStudents(120));
        if(students.size()!=120){
            System.out.println("Error occurred when grabbing students from bag");
            System.out.println("Bad thing appens when pulling out to much students");
            return 1;
        }
        students.addAll(borsa.pullStudents(1));
        if(students.size()!=120){
            System.out.println("Error occurred when grabbing students from bag");
            System.out.println("Bad thing appens when pulling out when no student are present");
            return 1;
        }
        for (PawnColor color: PawnColor.values()){
            int numstudents = (int)students.stream().filter(x -> color.equals(x.getColor())).count();
            if (numstudents != 24){
                System.out.println("Error while checking if the bag was getting ount the student correctly");
                return 1;
            }
        }
        if(!madrenatura.getPlace().equals(islands.get(0))){
            System.out.println("Error while fetcing motherNature position");
            return 1;
        }
        madrenatura.setPlace(islands.get(1));
        if(!madrenatura.getPlace().equals(islands.get(1))){
            System.out.println("Error while setting motherNature position");
            return 1;
        }
        return 0;
    }
}
