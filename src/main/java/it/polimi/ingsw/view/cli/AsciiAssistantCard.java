package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Gamer;

/**
 * Class that represent an AssistantCard with Ascii
 * @author Davide Grazzani
 */
public class AsciiAssistantCard {
    private static final int height = 6;
    private static final int width = 15;
    private AssistantCard card = null;
    private final Gamer gamer;

    /**
     * Class builder
     * @param gamer is the gamer which will be linked with the card
     */
    public AsciiAssistantCard(Gamer gamer){
        this.gamer = gamer;
    }

    /**
     * Method used to draw a line of the card
     * @param line is the line of the card you want to print
     * @return an integer represent the length of the printed line
     */
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
                    if(card.getTurnValue()>=10){
                        string = "|    (" + card.getTurnValue() + "," + card.getMovement() + ")   |";
                    }else{
                        string = "|    (" + card.getTurnValue() + "," + card.getMovement() + ")    |";
                    }
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
                string = gamer.getUsername();
                for(int i = string.length();i<AsciiAssistantCard.getWidth();i++){
                    string = string+ " ";
                }
                System.out.print(string);
            }
        }
        return string.length();
    }

    /**
     * Getter method
     * @return the height of the card
     */
    public static int getHeight(){
        return height;
    }

    /**
     * Getter method
     * @return the width of the card
     */
    public static int getWidth(){
        return width;
    }

    /**
     * Setter method used to set the card to be displayed accordingly to the gamer's choice
     */
    private void setCard(){
        this.card = gamer.getCurrentSelection();
    }
}
