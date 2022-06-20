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
                if(i ==0 && j%2==0){
                    positionalMatrix[j][i] = this.asciiIslands.get(counter).getIsland().getId();
                    counter ++;
                } else if (i==2 && j%2==0) {
                    positionalMatrix[j][i] = this.asciiIslands.get(counter).getIsland().getId();
                    counter --;
                } else if (i ==1) {
                    counter = 11;
                } else{
                    positionalMatrix[j][i] = 0;
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

    public void mergeIsland(int id1, int id2){
        for (AsciiIsland island : this.asciiIslands) {
            if (island.getIsland().getId() == id2) {
                island.setMerged(true);
                island.getIsland().setMerged();
            }
        }
        ArrayList<Integer> attachedIsland = new ArrayList<>();
        for(AsciiIsland island : this.asciiIslands){
            if(isAttached(id1,island.getIsland().getId(),null)){
                attachedIsland.add(island.getIsland().getId());
            }
        }
        ArrayList<Integer> nearIsland = closestIsland(attachedIsland,id2);
        if(nearIsland.isEmpty()){
            nearIsland = getIslandPosition(id1);
        }
        int x1 = nearIsland.get(0);
        int y1 = nearIsland.get(1);
        int island = positionalMatrix[y1][x1];
        while (!isAttached(island,id2,null)){
            nearIsland = getIslandPosition(id2);
            int x2 = nearIsland.get(0);
            int y2 = nearIsland.get(1);
            nearIsland.clear();
            int id = positionalMatrix[y2][x2];
            positionalMatrix[y2][x2] = 0;
            if(x1 == x2){
                if(y1-y2>0){
                    positionalMatrix[y2+1][x2] = id;
                }else{
                    positionalMatrix[y2-1][x2] = id;

                }
            }else{
                if(x1-x2>0){
                    positionalMatrix[y1][x1-1] = id;
                }else{
                    positionalMatrix[y1][x1+1] = id;
                }
            }
        }
    }

    private ArrayList<Integer> closestIsland(ArrayList<Integer> attachedIsland,Integer islandId){
        int dist = 0;
        int x,y;
        ArrayList<Integer> results = new ArrayList<>();
        results = getIslandPosition(islandId);
        x = results.get(0);
        y = results.get(1);
        results.clear();
        for(Integer island : attachedIsland){
            int difference = 0, distance = 0;
            ArrayList<Integer> coordinates = getIslandPosition(island);
            distance =  x -coordinates.get(0);
            if(distance<0){
                distance = -distance;
            }
            distance = distance*2;
            difference = distance;
            distance = y -coordinates.get(1);
            if(distance<0){
                distance = -distance;
            }
            difference = difference + distance;
            if(difference<dist || dist == 0){
                dist = difference;
                results.clear();
                results.add(coordinates.get(0));
                results.add(coordinates.get(1));
            }
        }
        return results;
    }

    private boolean isAttached(int id1, int id2,ArrayList<Integer> alreadyProbed){
        if(alreadyProbed == null){
            alreadyProbed = new ArrayList<>();
        }
        ArrayList<Integer> nearestIsland = new ArrayList<>(getNearIslands(id1));
        if(nearestIsland.isEmpty()){
            return false;
        }
        if(nearestIsland.contains(id2)){
            return true;
        }else{
            alreadyProbed.add(id1);
            for(Integer island : nearestIsland){
                if(!alreadyProbed.contains(island)){
                    boolean result = isAttached(island,id2,alreadyProbed);
                    if(result){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private ArrayList<Integer> getIslandPosition(int id){
        ArrayList<Integer> result = new ArrayList<>();
        for(int x = 0;x< row; x++){
            for(int y = 0;y <column;y++){
                if(positionalMatrix[y][x] == id){
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
        ArrayList<Integer> result = getIslandPosition(id);
        x = result.get(0);
        y = result.get(1);
        result.clear();
        result = new ArrayList<>();
        if(x-1>0){
            if(positionalMatrix[y][x-1]!=0){
                result.add(positionalMatrix[y][x-1]);
            }
        }
        if(y-1>0){
            if(positionalMatrix[y-1][x]!=0){
                result.add(positionalMatrix[y-1][x]);
            }
        }
        if(x+1<row){
            if(positionalMatrix[y][x+1]!=0){
                result.add(positionalMatrix[y][x+1]);
            }
        }
        if(y+1<column){
            if(positionalMatrix[y+1][x]!=0){
                result.add(positionalMatrix[y+1][x]);
            }
        }
        return result;
    }

}
