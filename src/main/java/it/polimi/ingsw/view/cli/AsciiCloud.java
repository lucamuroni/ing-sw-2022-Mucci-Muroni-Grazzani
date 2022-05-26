package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.Cloud;

public class AsciiCloud {
    private static final int height = 6;
    private static final int width = 13;
    private final Cloud cloud;

    public AsciiCloud(Cloud cloud){
        this.cloud = cloud;
    }
    public void draw(int line){
        switch (line){
            case 0:
                System.out.print("    _____   ");
            case 1:
                System.out.print(" __|     |__ ");
            case 2:
                if(this.cloud.getStudents().isEmpty()){
                    System.out.print("|   "+AnsiChar.MISSING_PAWN.toString()+"    "+AnsiChar.MISSING_PAWN+toString()+"   |");
                }else{
                   System.out.print("|   "+this.fromPawnToAnsi(this.cloud.getStudents().get(0).getColor())+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString()+"    "+this.fromPawnToAnsi(this.cloud.getStudents().get(1).getColor())+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString()+"   |");
                }
            case 3:
            case 4:
            case 5:

        }
    }

    private String fromPawnToAnsi(PawnColor color){
        for(AnsiColor ansiColor : AnsiColor.values()){
            if(ansiColor.getColor() == color){
                return ansiColor.toString();
            }
        }
    }
}
