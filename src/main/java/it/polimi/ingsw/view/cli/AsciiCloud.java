package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.Cloud;

/**
 * @author Davide Grazzani
 * Class that represents a cloud in the cli
 */
public class AsciiCloud {
    private static final int height = 6;
    private static final int width = 13;
    private final Cloud cloud;

    /**
     * Class constructor
     * @param cloud represents the cloud to show
     */
    public AsciiCloud(Cloud cloud){
        this.cloud = cloud;
    }

    /**
     * Method that draws a line of the cloud
     * @param line represents a line in the cli
     * @return the length of the string
     */
    public int draw(int line){
        String string = "";
        switch (line){
            case 0:
                string = "    _____";
                System.out.print(string);
                break;
            case 1:
                string = " __|  "+cloud.getId()+"  |__";
                System.out.print(string);
                break;
            case 2:
                if(this.cloud.getStudents().isEmpty()){
                    string = "|           |";
                    System.out.print(string);
                }else{
                    string = "|   "+this.fromPawnToAnsi(this.cloud.getStudents().get(0).getColor())+ AnsiChar.PAWN + AnsiColor.RESET +"    "+this.fromPawnToAnsi(this.cloud.getStudents().get(1).getColor())+ AnsiChar.PAWN + AnsiColor.RESET +"  |";
                    System.out.print(string);
                }
                break;
            case 3:
                if(this.cloud.getStudents().isEmpty()){
                    string = "|           |";
                    System.out.print(string);
                }else{
                    if(this.cloud.getStudents().size()==3){
                        string = "|     "+this.fromPawnToAnsi(this.cloud.getStudents().get(2).getColor())+ AnsiChar.PAWN + AnsiColor.RESET +"     |";
                        System.out.print(string);
                    }else{
                        string = "|   "+this.fromPawnToAnsi(this.cloud.getStudents().get(2).getColor())+ AnsiChar.PAWN + AnsiColor.RESET +"    "+this.fromPawnToAnsi(this.cloud.getStudents().get(3).getColor())+ AnsiChar.PAWN + AnsiColor.RESET +"  |";
                        System.out.print(string);
                    }
                }
                break;
            case 4:
                string = " --|     |--";
                System.out.print(string);
                break;
            case 5:
                string = "    -----";
                System.out.print(string);
                break;
        }
        if(cloud.getStudents().isEmpty()){
            return string.length();
        }else{
            if(line == 2){
                return (string.length()-18);
            } else if (line == 3) {
                if(cloud.getStudents().size() == 3){
                    return (string.length() -9);
                }else {
                    return (string.length() -9);
                }
            }else{
                return string.length();
            }
        }
    }

    /**
     * Method that returns the color associated to the pawn as an ansiColor
     * @param color represents the considered color
     * @return the color as a string or null if the color isn't a valid one
     */
    private String fromPawnToAnsi(PawnColor color){
        for(AnsiColor ansiColor : AnsiColor.values()){
            if(ansiColor.getColor() == color){
                return ansiColor.toString();
            }
        }
        return null;
    }

    /**
     * Getter method
     * @return the height of the cloud
     */
    public static int getHeight(){
        return height;
    }

    /**
     * Getter method
     * @return the width of the cloud
     */
    public static int getWidth(){
        return width;
    }
}
