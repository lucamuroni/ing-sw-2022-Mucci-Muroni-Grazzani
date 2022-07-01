package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.page.UndoException;

/**
 * @author Davide Grazzani
 * Interface with the methods for the cli and the gui
 */
public interface Page {
    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    void handle() throws UndoException;

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    boolean isReadyToProceed();

    /**
     * Method used to set that the page has completed its task
     */
    void kill();
}
