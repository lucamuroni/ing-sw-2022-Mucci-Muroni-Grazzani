package it.polimi.ingsw.view.cli;

import com.sun.security.auth.UnixNumericGroupPrincipal;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

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
                    positionalMatrix[i][j] = this.asciiIslands.get(counter).getIsland().getId();
                    counter ++;
                }else{
                    positionalMatrix[i][j] = 0;
                }
            }
        }
    }

    private void print(int line,int row) throws AssetErrorException {
        for(int i=0; i < column;i++){
            if(positionalMatrix[i][row]==0){
                printSpace(AsciiIsland.getWidth());
            }else {
                AsciiIsland island = null;
                for(AsciiIsland island1 : this.asciiIslands){
                    if (island1.getIsland().getId()==positionalMatrix[i][row]){
                        island = island1;
                    }
                }
                if(island == null){
                    throw new AssetErrorException("No island founded with that id ");
                }
                int space = island.draw(line);
                this.printSpace(AsciiIsland.getWidth()-space);
            }
        }
        System.out.print("\n");
    }

    private void printSpace(int number){
        for(int i = 0;i < number ; i++){
            System.out.print(" ");
        }
    }

    public void draw() throws AssetErrorException {
        for(int i = 0;i < row; i++){
            for(int j = 0 ; j < AsciiIsland.getHeight();j++){
                this.print(j,i);
            }
        }
    }

}
