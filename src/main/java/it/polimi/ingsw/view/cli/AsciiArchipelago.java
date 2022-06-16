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
        ArrayList<Integer> attachedIsland = new ArrayList<>();
        for(int x = 0;x< row; x++) {
            for (int y = 0; y < column; y++) {
                if(isAttached(id1,positionalMatrix[x][y])){
                    attachedIsland.add(positionalMatrix[x][y]);
                }
            }
        }
        ArrayList<Integer> nearIsland = closestIsland(attachedIsland,id2);
        int x1 = nearIsland.get(0);
        int y1 = nearIsland.get(1);
        nearIsland.clear();
        nearIsland = getIslandPosition(id2);
        int x2 = nearIsland.get(0);
        int y2 = nearIsland.get(1);
        int id = positionalMatrix[x2][y2];
        positionalMatrix[x2][y2] = 0;
        if(x1 == x2){
            if(y1-y2>0){
                positionalMatrix[x2][y2+1] = id;
                //TODO settare ismerged sull'isola
            }else{
                positionalMatrix[x2][y2-1] = id;
            }
        }else{
            if(x1-x2>0){
                positionalMatrix[x1-1][y1] = id;
            }else{
                positionalMatrix[x1+1][y1] = id;
            }
        }
    }

    private ArrayList<Integer> closestIsland(ArrayList<Integer> attachedIsland,Integer islandId){
        return null;
    }

    private boolean isAttached(int id1, int id2){
        ArrayList<Integer> position;
        int x1,x2,y1,y2;
        position = getIslandPosition(id1);
        x1 = position.get(0);
        y1 = position.get(1);
        position = getIslandPosition(id2);
        x2 = position.get(0);
        y2 = position.get(1);
        if((x1-x2)<=1 && (x1-x2)>=-1){
            return true;
        }
        if((y1-y2)<=1 && (y1-y2)>=-1){
            return true;
        }
        ArrayList<Integer> nearIslands;
        nearIslands = getNearIslands(id1);
        for(Integer island : nearIslands){
            boolean result = isAttached(island,id2);
            if(result){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer> getIslandPosition(int id){
        ArrayList<Integer> result = new ArrayList<>();
        for(int x = 0;x< row; x++){
            for(int y = 0;y <column;y++){
                if(positionalMatrix[x][y] == id){
                    result.add(x);
                    result.add(y);
                    return result;
                }
            }
        }
        return result;
    }

    private ArrayList<Integer> getNearIslands(int id){
        int x,y;
        ArrayList<Integer> result = new ArrayList<>();
        result = getIslandPosition(id);
        x = result.get(0);
        y = result.get(1);
        result.clear();
        if(x-1>0){
            if(positionalMatrix[x-1][y]!=0){
                result.add(positionalMatrix[x-1][y]);
            }
        }
        if(y-1>0){
            if(positionalMatrix[x][y-1]!=0){
                result.add(positionalMatrix[x][y-1]);
            }
        }
        if(x+1<row){
            if(positionalMatrix[x+1][y]!=0){
                result.add(positionalMatrix[x+1][y]);
            }
        }
        if(y+1>column){
            if(positionalMatrix[x][y+1]!=0){
                result.add(positionalMatrix[x][y+1]);
            }
        }
        return result;
    }

}
