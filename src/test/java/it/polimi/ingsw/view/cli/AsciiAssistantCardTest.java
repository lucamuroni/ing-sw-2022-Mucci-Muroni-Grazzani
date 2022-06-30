package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Gamer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsciiAssistantCardTest {

    @Test
    void draw() {
        Gamer gamer = new Gamer(1);
        gamer.setUsername("pollo",false);
        AsciiAssistantCard asciiAssistantCard = new AsciiAssistantCard(gamer);
        for(int i = 0;i<AsciiAssistantCard.getHeight();i++){
            asciiAssistantCard.draw(i);
            System.out.print("\n");
        }
        gamer.setCurrentSelection(AssistantCard.CAT);
        for(int i = 0;i<AsciiAssistantCard.getHeight();i++){
            asciiAssistantCard.draw(i);
            System.out.print("\n");
        }
    }
}