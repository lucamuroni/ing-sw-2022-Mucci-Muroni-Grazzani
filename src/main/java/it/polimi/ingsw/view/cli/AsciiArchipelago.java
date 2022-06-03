package it.polimi.ingsw.view.cli;

import java.util.ArrayList;

public class AsciiArchipelago {
    //TODO : istanza unica che risiede nella cli -> viene creata una sola volta
    private final ArrayList<AsciiIsland> asciiIslands;
    private int[][] positionalMatrix;

    public AsciiArchipelago(ArrayList<AsciiIsland> islands){
        this.asciiIslands = new ArrayList<AsciiIsland>(islands);
        positionalMatrix = new int[3][12];
        this.initMatrix();
    }

    private void initMatrix(){
        int counter = 0;
        for(int i = 0; i<12;i++){
            for(int j = 0; j<3;j++){
                if(j != 1 && i%2==0){
                    positionalMatrix[j][i] = this.asciiIslands.get(counter).getIsland().getId();
                }else{
                    positionalMatrix[j][i] = 0;
                }
            }
        }
    }

}
