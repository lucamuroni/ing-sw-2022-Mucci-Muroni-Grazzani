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
        for(int i = 0; i<row;i++){
            for(int j = 0; j<column;j++){
                if(i != 1 && j%2==0){
                    positionalMatrix[j][i] = this.asciiIslands.get(counter).getIsland().getId();
                    counter ++;
                }else{
                    positionalMatrix[j][i] = 0;
                }
            }
        }
    }

    private void print(int line,int row) throws AssetErrorException {
        for(int i=0; i < column;i++){
            if(positionalMatrix[i][row]==0){
                printSpace(AsciiIsland.getWidth()/2);
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

    public void mergeIsland(int id1, int id2){
        // TODO non pronta
        // in ordine trovo isola target, trovo isola più vicina nel conglomerato di isole, faccio il merge tra isola vicina e isola target
        int island1X = 0,island2X = 0,island1Y = 0,island2Y = 0;
        for(int x = 0; x< row;x++){
            for(int y = 0; y< column; y++){
                if(positionalMatrix[y][x]==id1){
                    island1X = x;
                    island1Y = y;
                }else if(positionalMatrix[y][x]==id2){
                    island2X = x;
                    island2Y = y;
                }
            }
        }
        if(island1X == island2X){
            positionalMatrix[island2Y][island2X] = 0;
            if(island1Y - island2Y > 0){
                positionalMatrix [island1Y-1][island2X] = id2;
            }else{
                positionalMatrix [island1Y+1][island2X] = id2;
            }
        }
    }

}
