package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import java.util.ArrayList;

/**
 * Class used to draw islands and manage their movement when merging one another
 * @author Davide Grazzani
 */
public class AsciiArchipelago {
    private final ArrayList<AsciiIsland> asciiIslands;
    private final int[][] positionalMatrix;
    private final static int row = 3;
    private final static int column =12;

    /**
     * Class constructor
     * @param islands are the island that must be drawn
     */
    public AsciiArchipelago(ArrayList<AsciiIsland> islands){
        this.asciiIslands = new ArrayList<AsciiIsland>(islands);
        positionalMatrix = new int[column][row];
        this.initMatrix();
    }

    /**
     * Method used to initialize a matrix on which all future computing will be done.
     * This matrix implicitly contains all the position of every island (including already merged islands)
     */
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

    /**
     * Method used to print a single line of the archipelago given a certain row of the matrix
     * @param line is line of the archipelago you want to print
     * @param row is the row you are considering (accordingly to matrix)
     * @throws AssetErrorException if no island with a specified ID is found in the Asset space
     */
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

    /**
     * Method used to make correct padding between islands
     * @param number is the number of space you need to print
     */
    private void printSpace(int number){
        for(int i = 0;i < number ; i++){
            System.out.print(" ");
        }
    }

    /**
     * Method used to draw the entire archipelago
     * @throws AssetErrorException if the matrix contains an island with no validation from package Asset
     */
    public void draw() throws AssetErrorException {
        for(int i = 0;i < row; i++){
            for(int j = 0 ; j < AsciiIsland.getHeight();j++){
                this.print(j,i);
            }
        }
    }

    /**
     * Method used to merge 2 islands
     * @param motherNatureIslandId is the island on which MotherNature is present
     * @param otherIslandId represent the other island that needs to be merged
     */
    public void mergeIsland(int motherNatureIslandId,int otherIslandId){
        for (AsciiIsland island : this.asciiIslands) {
            if (island.getIsland().getId() == otherIslandId) {
                island.setMerged();
            }
        }
        ArrayList<Integer> motherNatureArchipelago = new ArrayList<>();
        for(AsciiIsland island : this.asciiIslands){
            if(isAttached(island.getIsland().getId(),motherNatureIslandId,null)){
                motherNatureArchipelago.add(island.getIsland().getId());
            }
        }
        ArrayList<Integer> otherIslandArchipelago = new ArrayList<>();
        for(AsciiIsland island : this.asciiIslands){
            if(isAttached(island.getIsland().getId(),otherIslandId,null)){
                otherIslandArchipelago.add(island.getIsland().getId());
            }
        }
        int closestToMnIslandId = closestIsland(otherIslandArchipelago,motherNatureIslandId);
        int closestToOtherIslandId = closestIsland(motherNatureArchipelago,otherIslandId);
        while(!isAttached(closestToOtherIslandId,closestToMnIslandId,null)){
            ArrayList<Integer> islands = new ArrayList<>();
            for(Integer island : motherNatureArchipelago){
                if(island != closestToOtherIslandId){
                    islands.add(island);
                }
            }
            makeAMove(closestToMnIslandId,closestToOtherIslandId,islands);
        }
    }

    /**
     * Method used to decide where an island must move in case of merge
     * @param settledID is the id of the island that will not move
     * @param IDToBeMoved is the id of the island that needs to be moved
     * @param islandToBePropagate is the ArrayList of island attached to IDToBeMoved
     */
    private void makeAMove(int settledID,int IDToBeMoved, ArrayList<Integer> islandToBePropagate){
        ArrayList<Integer> position = new ArrayList<>(getIslandPosition(settledID));
        int x1 = position.get(0);
        int y1 = position.get(1);
        position.clear();
        position.addAll(getIslandPosition(IDToBeMoved));
        int x2 = position.get(0);
        int y2 = position.get(1);
        positionalMatrix[y2][x2] = 0;
        if(x1 == x2){
            if(y1-y2>0){
                positionalMatrix[y2+1][x2] = IDToBeMoved;
            }else{
                positionalMatrix[y2-1][x2] = IDToBeMoved;
            }
        }else{
            if(x1-x2>0){
                positionalMatrix[y1][x1-1] = IDToBeMoved;
            }else{
                positionalMatrix[y1][x1+1] = IDToBeMoved;
            }
        }
        if(islandToBePropagate.isEmpty()){
            return;
        }else {
            int islandID = closestIsland(islandToBePropagate,IDToBeMoved);
            for(int i = 0;i<islandToBePropagate.size();i++){
                if(islandToBePropagate.get(i)==islandID){
                    islandToBePropagate.remove(i);
                    break;
                }
            }
            makeAMove(IDToBeMoved,islandID,islandToBePropagate);
        }
    }

    /**
     * Method used to find which island is the closest to a given target
     * @param attachedIsland is the ArrayList of island that needs to be probed in order to find which is the closest
     * @param islandId it's the target to reach
     * @return the island ID of the closest island to the target
     */
    private int closestIsland(ArrayList<Integer> attachedIsland,Integer islandId){
        int dist = 0;
        int x,y;
        int results = 0;
        ArrayList<Integer> position = getIslandPosition(islandId);
        x = position.get(0);
        y = position.get(1);
        for(Integer island : attachedIsland){
            int difference = 0, distance = 0;
            ArrayList<Integer> coordinates = getIslandPosition(island);
            distance =  x -coordinates.get(0);
            if(distance<0){
                distance = -distance;
            }
            difference = distance;
            distance = y -coordinates.get(1);
            if(distance<0){
                distance = -distance;
            }
            difference = difference + distance;
            if(difference<dist || dist == 0){
                dist = difference;
                results = positionalMatrix[coordinates.get(1)][coordinates.get(0)];
            }
        }
        return results;
    }

    /**
     * Method used to establish if an island is attached to another island
     * @param islandIdToBeVerified is the island that we want to probe
     * @param targetIslandId is the island target
     * @param alreadyProbed recursive parameter needed to correctly stop recursion
     * @return true if the 2 island are attached
     */
    private boolean isAttached(int islandIdToBeVerified, int targetIslandId,ArrayList<Integer> alreadyProbed){
        if(islandIdToBeVerified == targetIslandId){
            return true;
        }
        if(alreadyProbed == null){
            alreadyProbed = new ArrayList<>();
        }
        ArrayList<Integer> nearestIsland = new ArrayList<>(getNearIslands(islandIdToBeVerified));
        if(nearestIsland.isEmpty()){
            return false;
        }
        alreadyProbed.add(islandIdToBeVerified);
        for(Integer island : nearestIsland){
            if(!alreadyProbed.contains(island)){
                boolean result = isAttached(island,targetIslandId,alreadyProbed);
                if(result){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method used to find the position of an island on the matrix
     * @param id is the id of the island we are searching
     * @return an ArrayList of size 2, containing at index 0 the row and at index 1 the column
     */
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

    /**
     * Method used to return the adjacent islands given another island
     * @param id is the island target
     * @return an ArrayList containing the island 's id  
     */
    private ArrayList<Integer> getNearIslands(int id){
        int x,y;
        ArrayList<Integer> result = getIslandPosition(id);
        x = result.get(0);
        y = result.get(1);
        result.clear();
        result = new ArrayList<>();
        if(x-1>=0){
            if(positionalMatrix[y][x-1]!=0){
                result.add(positionalMatrix[y][x-1]);
            }
        }
        if(y-1>=0){
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
