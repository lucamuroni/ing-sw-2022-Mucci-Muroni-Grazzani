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

    public void draw(int line){
        int size;
        switch (line){
            case 0:
                System.out.print("     _________     ");
                break;
            case 1:
                System.out.print("    |    "+island.getId()+"    |    ");
                break;
            case 2:
                System.out.print("   -           -   ");
                break;
            case 3:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.BLUE).count();
                if(size>0){
                    System.out.print("  |     "+AnsiColor.BLUE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     | ");
                }else{
                    System.out.print("  |             | ");
                }
                break;
            case 4:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.RED).count();
                if(size>0){
                    System.out.print(" -      "+AnsiColor.RED.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"      -");
                }else{
                    System.out.print(" -               -");
                }
                break;
            case 5:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.GREEN).count();
                if(size>0){
                    System.out.print("|       "+AnsiColor.GREEN.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"       |");
                }else{
                    System.out.print("|                 |");
                }
                break;
            case 6:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.PINK).count();
                if(size>0){
                    System.out.print(" -      "+AnsiColor.PURPLE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"      -");
                }else{
                    System.out.print(" -               -");
                }
                break;
            case 7:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.YELLOW).count();
                if(size>0){
                    System.out.print("  |     "+AnsiColor.YELLOW.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     | ");
                }else{
                    System.out.print("  |             | ");
                }
                break;
            case 8:

                if(this.island.isMotherNaturePresent()){
                    System.out.print("   -     "+AnsiChar.MOTHER_NATURE+"     -  ");
                }else{
                    System.out.print("   -           -  ");
                }
                break;
            case 9:
                if(this.island.isTowerPresent()){
                    System.out.print("    |   "+AnsiChar.TOWER+"("+this.island.getTowersColor().getAcronym()+")  |    ");
                }else{
                    System.out.print("    |         |    ");
                }
                break;
            case 10:
                System.out.print("     ---------     ");
                break;
        }
    }

    public void draw(){
        for(int i = 0;i< height;i++){
            this.draw(i);
            System.out.print("\n");
        }
    }
}
