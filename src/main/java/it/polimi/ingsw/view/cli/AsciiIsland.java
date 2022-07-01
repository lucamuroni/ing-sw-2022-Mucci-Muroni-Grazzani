package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.Island;

/**
 * @author Davide Grazzani
 * Class that represents an Island in the cli
 */
public class AsciiIsland {
    private final Island island;
    private boolean isMerged;
    private final static int height = 11;
    private final static int width = 19;

    /**
     * Class constructor
     * @param island represents the island to show
     */
    public AsciiIsland(Island island){
        this.island = island;
        this.isMerged = false;
    }

    /**
     * Method that draws a line of the island
     * @param line represents a line in the cli
     * @return the length of the string
     */
    public int draw(int line){
        int size;
        String string = "";
        boolean pawn = false,mn = false,tower = false;
        switch (line){
            case 0:
                string = "     _________";
                System.out.print(string);
                break;
            case 1:
                if(island.getId()>=10){
                    string = "    |    "+island.getId()+"   |";
                }else{
                    string = "    |    "+island.getId()+"    |";
                }
                System.out.print(string);
                break;
            case 2:
                string = "   -           -   ";
                System.out.print(string);
                break;
            case 3:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.BLUE).count();
                if(size>0 && !isMerged){
                    string = "  |     "+ AnsiColor.BLUE +AnsiChar.PAWN+ AnsiColor.RESET +"x"+size+"     |";
                    System.out.print(string);
                    pawn = true;
                }else{
                    string = "  |             | ";
                    System.out.print(string);
                }
                break;
            case 4:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.RED).count();
                if(size>0 && !isMerged){
                    string = " -      "+ AnsiColor.RED +AnsiChar.PAWN+ AnsiColor.RESET +"x"+size+"      -";
                    System.out.print(string);
                    pawn = true;
                }else{
                    string = " -               -";
                    System.out.print(string);
                }
                break;
            case 5:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.GREEN).count();
                if(size>0 && !isMerged){
                    string = "|       "+ AnsiColor.GREEN +AnsiChar.PAWN+ AnsiColor.RESET +"x"+size+"       |";
                    System.out.print(string);
                    pawn = true;
                }else{
                    string = "|                 |";
                    System.out.print(string);
                }
                break;
            case 6:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.PINK).count();
                if(size>0 && !isMerged){
                    string = " -      "+ AnsiColor.PURPLE +AnsiChar.PAWN+ AnsiColor.RESET +"x"+size+"      -";
                    System.out.print(string);
                    pawn = true;
                }else{
                    string = " -               -";
                    System.out.print(string);
                }
                break;
            case 7:
                size = (int) this.island.getStudents().stream().filter(x -> x.getColor() == PawnColor.YELLOW).count();
                if(size>0 && !isMerged){
                    string = "  |     "+ AnsiColor.YELLOW +AnsiChar.PAWN+ AnsiColor.RESET +"x"+size+"     |";
                    System.out.print(string);
                    pawn = true;
                }else{
                    string = "  |             |";
                    System.out.print(string);
                }
                break;
            case 8:

                if(this.island.isMotherNaturePresent()){
                    string = "   -     "+AnsiChar.MOTHER_NATURE+"    -";
                    System.out.print(string);
                    mn = true;
                }else{
                    string = "   -           -  ";
                    System.out.print(string);
                }
                break;
            case 9:
                if(this.island.isTowerPresent()){
                    string = "    |   "+AnsiChar.TOWER+"("+this.island.getTowersColor().getAcronym()+")  |";
                    System.out.print(string);
                    tower = true;
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
        if(pawn){
            return (string.length()-9);
        }else if(tower){
            return string.length();
        }else if(mn){
            return string.length()-2;
        }else{
            return string.length();
        }
    }

    /**
     * Method that draws the island
     */
    public void draw(){
        for(int i = 0;i< height;i++){
            this.draw(i);
            System.out.print("\n");
        }
    }

    /**
     * Getter method
     * @return the associated island
     */
    public Island getIsland() {
        return island;
    }

    /**
     * Getter method
     * @return the height of the island
     */
    public static int getHeight(){
        return height;
    }

    /**
     * Getter method
     * @return the width of the island
     */
    public static int getWidth(){
        return width;
    }

    /**
     * Setter method used to signal that this island is merged and therefore does not need to display its own pawns
     */
    public void setMerged(){
        this.isMerged = true;
        this.island.setMerged();
    }
}
