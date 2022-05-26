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
    public int draw(int line){
        String string = "";
        switch (line){
            case 0:
                string = "    _____";
                System.out.print(string);
                break;
            case 1:
                string = " __|     |__";
                System.out.print(string);
                break;
            case 2:
                if(this.cloud.getStudents().isEmpty()){
                    string = "|   "+AnsiChar.MISSING_PAWN.toString()+"    "+AnsiChar.MISSING_PAWN+toString()+"   |";
                    System.out.print(string);
                }else{
                    string = "|   "+this.fromPawnToAnsi(this.cloud.getStudents().get(0).getColor())+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString()+"    "+this.fromPawnToAnsi(this.cloud.getStudents().get(1).getColor())+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString()+"   |";
                    System.out.print(string);
                }
                break;
            case 3:
                if(this.cloud.getStudents().size()==3){
                    string = "|     "+this.fromPawnToAnsi(this.cloud.getStudents().get(2).getColor())+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString()+"     |";
                    System.out.print(string);
                }else{
                    string = "|   "+this.fromPawnToAnsi(this.cloud.getStudents().get(2).getColor())+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString()+"    "+this.fromPawnToAnsi(this.cloud.getStudents().get(3).getColor())+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString()+"   |";
                    System.out.print(string);
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
        return string.length();
    }

    private String fromPawnToAnsi(PawnColor color){
        for(AnsiColor ansiColor : AnsiColor.values()){
            if(ansiColor.getColor() == color){
                return ansiColor.toString();
            }
        }
        return null;
    }
}
