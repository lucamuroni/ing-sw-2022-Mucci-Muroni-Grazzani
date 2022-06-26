package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.DashBoard;

/**
 * @author Davide Grazzani
 * Class that represents a dashboard in the cli
 */
public class AsciiDashBoard {
    private final DashBoard dashBoard;
    private static Cli cli ;
    private final static int width = 80;
    private final static int height = 11;

    /**
     * Class constructor
     * @param dashBoard represents the dashboard to show
     */
    public AsciiDashBoard(Cli c,DashBoard dashBoard){
        this.dashBoard = dashBoard;
        cli = c;
    }

    /**
     * Method that draws a line of the dashboard
     * @param line represents a line in the cli
     */
    public void draw(int line){
        int num;
        switch (line){
            case 0:
                System.out.print(" _____________________________________________________________________________");
                break;
            case 1:
                System.out.print("| Waiting Room | Hall                                  | Professors | Towers |");
                break;
            case 2:
                System.out.print("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                break;
            case 3:
                System.out.print("|     "+AnsiColor.RED.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x");
                num = (int) this.dashBoard.getWaitingRoom().stream().filter(x->x.getColor()== PawnColor.RED).count();
                System.out.print(num+"      |  ");
                num = (int) this.dashBoard.getHall().stream().filter(x->x.getColor()== PawnColor.RED).count();
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiColor.RED.toString()+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString());
                    if(i != 9){
                        System.out.print("   ");
                    }
                }
                num = 10 - num ;
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiChar.MISSING_PAWN.toString());
                    if(i != num-1){
                        System.out.print("   ");
                    }
                }
                System.out.print("|      ");
                if(this.dashBoard.getProfessors().stream().anyMatch(x -> x == PawnColor.RED)){
                    System.out.print(AnsiColor.RED.toString()+AnsiChar.WIZARD.toString()+AnsiColor.RESET.toString());
                }else{
                    System.out.print(AnsiChar.MISSING_WIZARD.toString());
                }
                System.out.print("     ");
                System.out.print("|        |");
                break;
            case 4:
                System.out.print("|     "+AnsiColor.YELLOW.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x");
                num = (int) this.dashBoard.getWaitingRoom().stream().filter(x->x.getColor()== PawnColor.YELLOW).count();
                System.out.print(num+"      |  ");
                num = (int) this.dashBoard.getHall().stream().filter(x->x.getColor()== PawnColor.YELLOW).count();
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiColor.YELLOW.toString()+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString());
                    if(i != 9){
                        System.out.print("   ");
                    }
                }
                num = 10 - num ;
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiChar.MISSING_PAWN.toString());
                    if(i != num-1){
                        System.out.print("   ");
                    }
                }
                System.out.print("|      ");
                if(this.dashBoard.getProfessors().stream().anyMatch(x -> x == PawnColor.YELLOW)){
                    System.out.print(AnsiColor.YELLOW.toString()+AnsiChar.WIZARD.toString()+AnsiColor.RESET.toString());
                }else{
                    System.out.print(AnsiChar.MISSING_WIZARD.toString());
                }
                System.out.print("     ");
                System.out.print("|   "+AnsiChar.TOWER+"x"+this.dashBoard.getNumTower()+"  |");
                break;
            case 5:
                System.out.print("|     "+AnsiColor.BLUE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x");
                num = (int) this.dashBoard.getWaitingRoom().stream().filter(x->x.getColor()== PawnColor.BLUE).count();
                System.out.print(num+"      |  ");
                num = (int) this.dashBoard.getHall().stream().filter(x->x.getColor()== PawnColor.BLUE).count();
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiColor.BLUE.toString()+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString());
                    if(i != 9){
                        System.out.print("   ");
                    }
                }
                num = 10 - num ;
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiChar.MISSING_PAWN.toString());
                    if(i != num-1){
                        System.out.print("   ");
                    }
                }
                System.out.print("|      ");
                if(this.dashBoard.getProfessors().stream().anyMatch(x -> x == PawnColor.BLUE)){
                    System.out.print(AnsiColor.BLUE.toString()+AnsiChar.WIZARD.toString()+AnsiColor.RESET.toString());
                }else{
                    System.out.print(AnsiChar.MISSING_WIZARD.toString());
                }
                System.out.print("     ");
                System.out.print("|  ("+dashBoard.getTowerColor()+")   |");
                break;
            case 6:
                System.out.print("|     "+AnsiColor.PURPLE.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x");
                num = (int) this.dashBoard.getWaitingRoom().stream().filter(x->x.getColor()== PawnColor.PINK).count();
                System.out.print(num+"      |  ");
                num = (int) this.dashBoard.getHall().stream().filter(x->x.getColor()== PawnColor.PINK).count();
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiColor.PURPLE.toString()+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString());
                    if(i != 9){
                        System.out.print("   ");
                    }
                }
                num = 10 - num ;
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiChar.MISSING_PAWN.toString());
                    if(i != num-1){
                        System.out.print("   ");
                    }
                }
                System.out.print("|      ");
                if(this.dashBoard.getProfessors().stream().anyMatch(x -> x == PawnColor.PINK)){
                    System.out.print(AnsiColor.PURPLE.toString()+AnsiChar.WIZARD.toString()+AnsiColor.RESET.toString());
                }else{
                    System.out.print(AnsiChar.MISSING_WIZARD.toString());
                }
                System.out.print("     ");
                if(cli.getController().getGame().getGameType().equals(GameType.EXPERT.getName()) && this.dashBoard.getUsername().equals(cli.getController().getGame().getSelf().getUsername())){
                    System.out.print("|  "+AnsiChar.MONEY.toString()+"x"+cli.getController().getGame().getCoins()+"  |");
                }else{
                    System.out.print("|        |");
                }
                break;
            case 7:
                System.out.print("|     "+AnsiColor.GREEN.toString()+AnsiChar.PAWN+AnsiColor.RESET.toString()+"x");
                num = (int) this.dashBoard.getWaitingRoom().stream().filter(x->x.getColor()== PawnColor.GREEN).count();
                System.out.print(num+"      |  ");
                num = (int) this.dashBoard.getHall().stream().filter(x->x.getColor()== PawnColor.GREEN).count();
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiColor.GREEN.toString()+AnsiChar.PAWN.toString()+AnsiColor.RESET.toString());
                    if(i != 9){
                        System.out.print("   ");
                    }
                }
                num = 10 - num ;
                for(int i = 0; i<num; i++){
                    System.out.print(AnsiChar.MISSING_PAWN.toString());
                    if(i != num-1){
                        System.out.print("   ");
                    }
                }
                System.out.print("|      ");
                if(this.dashBoard.getProfessors().stream().anyMatch(x -> x == PawnColor.GREEN)){
                    System.out.print(AnsiColor.GREEN.toString()+AnsiChar.WIZARD.toString()+AnsiColor.RESET.toString());
                }else{
                    System.out.print(AnsiChar.MISSING_WIZARD.toString());
                }
                System.out.print("     ");
                System.out.print("|        |");
                break;
            case 8:
                System.out.print("|              |                                       |            |        |");
                break;
            case 9:
                System.out.print("------------------------------------------------------------------------------");
                break;
            case 10:
                String name = this.dashBoard.getUsername();
                int spaces = (width/2)-(name.length()/2);
                cli.printSpace(spaces);
                System.out.print(name);
                cli.printSpace((width/2)-(name.length()/2));
        }
    }

    /**
     * Method that draws the dashboard
     */
    public void draw(){
        for(int i = 0;i<height;i++){
            this.draw(i);
            System.out.print("\n");
        }
    }

    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
    }
}
