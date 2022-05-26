package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.DashBoard;

public class AsciiDashBoard {
    private final DashBoard dashBoard;

    public AsciiDashBoard(DashBoard dashBoard){
        this.dashBoard = dashBoard;
    }

    public void print(int line){
        int num;
        switch (line){
            case 0:
                System.out.print(" _________________________________________________________________");
                break;
            case 1:
                System.out.print("| Waiting Room | Hall                        | Professors | Towers |");
                break;
            case 2:
                System.out.print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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
                System.out.print("|     ");
                if(this.dashBoard.getProfessors().stream().filter(x -> x == PawnColor.RED).findFirst().orElse(null)== null){
                    System.out.print(AnsiColor.RED.toString()+"⚝"+AnsiColor.RESET.toString());
                }else{
                    System.out.print(" ");
                }
                System.out.print("|        |");
            //TODO : continuare i case switch per continuare a disegnare;
        }
    }
}
