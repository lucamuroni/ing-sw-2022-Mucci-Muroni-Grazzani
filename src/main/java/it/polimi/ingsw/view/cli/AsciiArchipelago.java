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

    /*public void mergeIsland(int id2, int id1){
        // TODO lista
        // 1 trovo tutte le isole vicino a MN (compresa)
        //trovo tutte le isole vicino a
        for (AsciiIsland island : this.asciiIslands) {
            if (island.getIsland().getId() == id1) {
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
        int islandID = positionalMatrix[y1][x1];
        ArrayList<Integer> attachedIslandToMN = new ArrayList<>();
        for(AsciiIsland island : this.asciiIslands){
            if(isAttached(id2,island.getIsland().getId(),null)){
                attachedIslandToMN.add(island.getIsland().getId());
            }
        }
        nearIsland = closestIsland(attachedIslandToMN,islandID);
        if(nearIsland.isEmpty()){
            nearIsland = getIslandPosition(id2);
        }
        int islandIDMN = positionalMatrix[nearIsland.get(1)][nearIsland.get(0)];
        if(!attachedIslandToMN.isEmpty()){
            attachedIslandToMN.clear();
            for(AsciiIsland island : this.asciiIslands){
                if(isAttached(id2,island.getIsland().getId(),null)){
                    attachedIslandToMN.add(island.getIsland().getId());
                }
            }
        }
        while (!isAttached(islandID,islandIDMN,null)){
            nearIsland = getIslandPosition(islandIDMN);
            //makeAMove();
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
    }*/

    public void mergeIsland(int motherNatureIslandId,int otherIslandId){
        //setto l'altra isola come mergiata
        for (AsciiIsland island : this.asciiIslands) {
            if (island.getIsland().getId() == otherIslandId) {
                island.setMerged();
            }
        }
        // trovo l'arcipelago su cui sta Madre Natura
        ArrayList<Integer> motherNatureArchipelago = new ArrayList<>();
        for(AsciiIsland island : this.asciiIslands){
            if(isAttached(island.getIsland().getId(),motherNatureIslandId,null)){
                motherNatureArchipelago.add(island.getIsland().getId());
            }
        }
        // trovo l'arcipelago dell'altra isola
        ArrayList<Integer> otherIslandArchipelago = new ArrayList<>();
        for(AsciiIsland island : this.asciiIslands){
            if(isAttached(island.getIsland().getId(),otherIslandId,null)){
                otherIslandArchipelago.add(island.getIsland().getId());
            }
        }
        //DEBUG
        System.out.println("Printo l'arcipelago delle isole di madrenatura :");
        motherNatureArchipelago.stream().forEach(x->System.out.println(x));
        System.out.println("Printo l'arcipelago delle altre isole :");
        otherIslandArchipelago.stream().forEach(x->System.out.println(x));
        //cerco l'sola più vicina a MN
        int closestToMnIslandId = closestIsland(otherIslandArchipelago,motherNatureIslandId);
        System.out.println("L isola più vicina a madre natura è :"+closestToMnIslandId);
        //cerco l'sola più vicina al gruppo di altre isole
        int closestToOtherIslandId = closestIsland(motherNatureArchipelago,otherIslandId);
        System.out.println("L isola più vicina aLL'altro arcipelago :"+closestToOtherIslandId);
        while(!isAttached(closestToOtherIslandId,closestToMnIslandId,null)){
            ArrayList<Integer> islands = new ArrayList<>();
            for(Integer island : motherNatureArchipelago){
                if(island != closestToOtherIslandId){
                    islands.add(island);
                }
            }
            makeAMove(closestToMnIslandId,closestToOtherIslandId,islands);
            System.out.println("Move made");
        }
    }

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
        //DUBUG
        System.out.println("Printo lo stato della matrice");
        for(int k = 0; k < row;k++){
            for(int q = 0; q<column;q++){
                System.out.print(positionalMatrix[q][k]+" ");
            }
            System.out.print("\n");
        }
        //FINE DUBUG
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
            //distance = distance*2;
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
