package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.Island;

public class AsciiIsland {
    private final Island island;

    public AsciiIsland(Island island){
        this.island = island;
    }

    public void draw(int line){
        int size;
        switch (line){
            case 1:
                System.out.println("     _________");
                break;
            case 2:
                System.out.println("    |    "+island.getId()+"    |");
                break;
            case 3:
                System.out.println("   -           -");
                break;
            case 4:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.BLUE).count();
                if(size>0){
                    System.out.println("  |     "+AnsiColor.BLUE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     |");
                }else{
                    System.out.println("  |             |");
                }
                break;
            case 5:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.RED).count();
                if(size>0){
                    System.out.println(" -      "+AnsiColor.RED.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"      -");
                }else{
                    System.out.println(" -               -");
                }
                break;
            case 6:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.GREEN).count();
                if(size>0){
                    System.out.println("|       "+AnsiColor.GREEN.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"       |");
                }else{
                    System.out.println("|                 |");
                }
                break;
            case 7:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.PINK).count();
                if(size>0){
                    System.out.println(" -      "+AnsiColor.PURPLE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"      -");
                }else{
                    System.out.println(" -               -");
                }
                break;
            case 8:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.YELLOW).count();
                if(size>0){
                    System.out.println("  |     "+AnsiColor.YELLOW.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     |");
                }else{
                    System.out.println("  |             |");
                }
                break;
            case 9:

                if(this.island.isMotherNaturePresent()){
                    System.out.println("   -     "+AnsiChar.MOTHER_NATURE+"     -");
                }else{
                    System.out.println("   -           -");
                }
                break;
            case 10:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.BLUE).count();
                if(size>0){
                    System.out.println("  |     "+AnsiColor.BLUE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     |");
                }else{
                    System.out.println("  |             |");
                }
                break;
            case 5:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.BLUE).count();
                if(size>0){
                    System.out.println("  |     "+AnsiColor.BLUE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x"+size+"     |");
                }else{
                    System.out.println("  |             |");
                }
                break;
        }

        size = this.island.getStudents()
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

    }
}
