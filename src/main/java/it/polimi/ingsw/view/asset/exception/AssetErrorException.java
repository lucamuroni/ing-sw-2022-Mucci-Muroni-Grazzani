package it.polimi.ingsw.view.asset.exception;

/**
 * @author Davide Grazzani
 * Class that represents an error while getting infos from server
 */
public class AssetErrorException extends Exception{
    /**
     * Constructor of class
     */
    public AssetErrorException(){
        super();
    }

    /**
     * Constructor of class with a custom message
     * @param s the message to print
     */
    public AssetErrorException(String s){
        super(s);
    }
}