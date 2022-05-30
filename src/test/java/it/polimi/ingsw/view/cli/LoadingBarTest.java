package it.polimi.ingsw.view.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadingBarTest {

    @Test
    void print() {
        LoadingBar loadingBar= new LoadingBar();
        for(int i = 0;i <1000;i++){
            loadingBar.print();
        }
    }
}