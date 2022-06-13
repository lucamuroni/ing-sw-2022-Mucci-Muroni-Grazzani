package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.game.Cloud;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AsciiCloudTest {

    @Test
    void draw() {
        Cloud cloud = new Cloud(2);
        ArrayList<Student> students = new ArrayList<>();
        cloud.update(students);
        AsciiCloud asciiCloud = new AsciiCloud(cloud);
        for(int i = 0;i< AsciiCloud.getHeight(); i++){
            asciiCloud.draw(i);
            System.out.print("\n");
        }
        students.add(new Student(PawnColor.RED));
        students.add(new Student(PawnColor.GREEN));
        students.add(new Student(PawnColor.PINK));
        for(int i = 0;i< AsciiCloud.getHeight(); i++){
            asciiCloud.draw(i);
            System.out.print("\n");
        }
        cloud.update(students);
        for(int i = 0;i< AsciiCloud.getHeight(); i++){
            asciiCloud.draw(i);
            System.out.print("\n");
        }
        students.add(new Student(PawnColor.BLUE));
        cloud.update(students);
        AsciiCloud asciiCloud1 = new AsciiCloud(cloud);
        for(int i = 0;i< AsciiCloud.getHeight(); i++){
            int j = asciiCloud.draw(i);
            while(j < AsciiCloud.getWidth()){
                System.out.print(" ");
                j++;
            }
            System.out.print("    ");
            asciiCloud1.draw(i);
            System.out.print("\n");
        }
    }
}