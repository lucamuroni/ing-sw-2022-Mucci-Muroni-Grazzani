package it.polimi.ingsw.view.cli;

import com.sun.security.auth.UnixNumericGroupPrincipal;

import java.util.ArrayList;

public class AsciiArchipelago {
    //TODO : istanza unica che risiede nella cli -> viene creata una sola volta
    private final ArrayList<AsciiIsland> asciiIslands;
    private int[][] positionalMatrix;
    private final static int row = 3;
    private final static int column =12;

    public AsciiArchipelago(ArrayList<AsciiIsland> islands){
        this.asciiIslands = new ArrayList<AsciiIsland>(islands);
        positionalMatrix = new int[column][row];
        this.initMatrix();
    }

    private void initMatrix(){
        int counter = 0;
        for(int i = 0; i<column;i++){
            for(int j = 0; j<row;j++){
                if(j != 1 && i%2==0){
                    positionalMatrix[j][i] = this.asciiIslands.get(counter).getIsland().getId();
                }else{
                    positionalMatrix[j][i] = 0;
                }
            }
        }
    }

    public void print(int line){
        for(int i=0; i < column;i++){
            for(int j=0; j<row;j++){
                if(positionalMatrix[i][j]==0){

                }
            }
        }
    }

    private void printSpace(int number){
        for(int i = 0;i < number ; i++){
            System.out.print(" ");
        }
    }

}
