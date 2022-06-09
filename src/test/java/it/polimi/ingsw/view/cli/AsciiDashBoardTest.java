package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.asset.game.DashBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsciiDashBoardTest {

    @Test
    void draw() {
        DashBoard d = new DashBoard(1);
        AsciiDashBoard asciiDashBoard = new AsciiDashBoard(d);
        asciiDashBoard.draw();
    }
}