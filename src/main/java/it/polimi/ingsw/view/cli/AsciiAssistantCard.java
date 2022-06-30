package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Gamer;

public class AsciiAssistantCard {
    private static final int height = 6;
    private static final int width = 15;
    private AssistantCard card = null;
    private final Gamer gamer;

    public AsciiAssistantCard(Gamer gamer){
        this.gamer = gamer;
    }

    public int draw(int line){
        String string = "";
        switch (line) {
            case 0 -> {
                string = " _____________ ";
                System.out.print(string);
            }
            case 1 -> {
                setCard();
                if (card == null) {
                    string = "|             |";
                } else {
                    string = "| " + card.getName();
                    for (int i = card.getName().length(); i < 11; i++) {
                        string = string + " ";
                    }
                    string = string + " |";
                }
                System.out.print(string);
            }
            case 2 -> {
                string = "|             |";
                System.out.print(string);
            }
            case 3-> {
                setCard();
                if (card != null) {
                    string = "|    (" + card.getTurnValue() + "," + card.getMovement() + ")    |";
                } else {
                    string = "|             |";
                }
                System.out.print(string);
            }
            case 4 -> {
                string = " ------------- ";
                System.out.print(string);
            }
            case 5->{
                //todo fix lunghezza + nome self
                string = gamer.getUsername();
                System.out.print(string);
            }
        }
        return string.length();
    }

    public static int getHeight(){
        return height;
    }

    public static int getWidth(){
        return width;
    }

    private void setCard(){
        this.card = gamer.getCurrentSelection();
    }
}
