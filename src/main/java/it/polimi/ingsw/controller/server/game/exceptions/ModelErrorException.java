package it.polimi.ingsw.controller.server.game.exceptions;

/**
 * Class that represents an error while trying to modify the model
 */
public class ModelErrorException extends Exception{
    /**
     * Constructor of the class
     */
    public ModelErrorException(){
        super();
    }

    /**
     * Constructor of the class with a custom message to print
     * @param s is the custom message
     */
    public ModelErrorException(String s){
        super(s);
    }
}