package it.polimi.ingsw.model;

/**
 * @author Luca Muroni
 * Class that represents the cards that every player must play during the first part of the round
 */
public enum AssistantCard {
    LEOPARD("Leopard",1,1),
    OSTRICH("Ostrich",2,1),
    CAT("Cat",3,2),
    EAGLE("Eagle",4,2),
    FOX("Fox",5,3),
    SNAKE("Snake",6,3),
    OCTOPUS("Octopus",7,4),
    DOG("Dog",8,4),
    ELEPHANT("Elephant",9,5),
    TURTLE("Turtle",10,5);

    private final int turnValue;
    private final int movement;
    private String name;
    /**
     * Class constructor
     * @param turn represents the value of the card used for turn order
     * @param move represents the number of steps that MotherNature can do
     */
    private AssistantCard(String name,int turn, int move){
        this.name = name;
        turnValue = turn;
        movement = move;
    }

    /**
     * Getter method
     * @return the steps MotherNature can do
     */
    public int getMovement() {
        return movement;
    }

    /**
     * Getter method
     * @return the value for turn order
     */
    public int getTurnValue() {
        return turnValue;
    }

    public String getName(){
        return this.name;
    }
}
