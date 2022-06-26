package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.asset.game.DashBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//TODO: da risolvere perchè non va
class AsciiDashBoardTest {

    @Test
    void draw() {
        DashBoard d = new DashBoard();

        AsciiDashBoard asciiDashBoard = new AsciiDashBoard(new Cli(),d);
        d.setUsername("bro");
        //asciiDashBoard.draw();
    }
}