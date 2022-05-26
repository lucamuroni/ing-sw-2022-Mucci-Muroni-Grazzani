package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.Island;

public class AsciiIsland {
    private final Island island;
    private final static int height = 11;
    private final static int width = 19;

    public AsciiIsland(Island island){
        this.island = island;
    }

    public int draw(int line){
        int size;
        String string = "";
        switch (line){
            case 0:
                string = "     _________";
                System.out.print(string);
                break;
            case 1:
                string = "    |    "+island.getId()+"    |";
                System.out.print(string);
                break;
            case 2:
                string = "   -           -   ";
                System.out.print(string);
                break;
            case 3:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.BLUE).count();
                if(size>0){
                    string = "  |     "+AnsiColor.BLUE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     |";
                    System.out.print(string);
                }else{
                    string = "  |             | ";
                    System.out.print(string);
                }
                break;
            case 4:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.RED).count();
                if(size>0){
                    string = " -      "+AnsiColor.RED.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"      -";
                    System.out.print(string);
                }else{
                    string = " -               -";
                    System.out.print(string);
                }
                break;
            case 5:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.GREEN).count();
                if(size>0){
                    string = "|       "+AnsiColor.GREEN.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"       |";
                    System.out.print(string);
                }else{
                    string = "|                 |";
                    System.out.print(string);
                }
                break;
            case 6:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.PINK).count();
                if(size>0){
                    string = " -      "+AnsiColor.PURPLE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"      -";
                    System.out.print(string);
                }else{
                    string = " -               -";
                    System.out.print(string);
                }
                break;
            case 7:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.YELLOW).count();
                if(size>0){
                    string = "  |     "+AnsiColor.YELLOW.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     |";
                    System.out.print(string);
                }else{
                    string = "  |             |";
                    System.out.print(string);
                }
                break;
            case 8:

                if(this.island.isMotherNaturePresent()){
                    string = "   -     "+AnsiChar.MOTHER_NATURE+"     -";
                    System.out.print(string);
                }else{
                    string = "   -           -  ";
                    System.out.print(string);
                }
                break;
            case 9:
                if(this.island.isTowerPresent()){
                    string = "    |   "+AnsiChar.TOWER+"("+this.island.getTowersColor().getAcronym()+")  |";
                    System.out.print(string);
                }else{
                    string = "    |         |";
                    System.out.print(string);
                }
                break;
            case 10:
                string = "     ---------";
                System.out.print(string);
                break;
        }
        return string.length();
    }

    public void draw(){
        for(int i = 0;i< height;i++){
            this.draw(i);
            System.out.print("\n");
        }
    }
}
