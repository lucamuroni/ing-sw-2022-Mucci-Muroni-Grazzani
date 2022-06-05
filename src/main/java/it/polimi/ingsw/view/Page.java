package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.page.UndoException;

/**
 * @author Davide Grazzani
 * Interface with the methods for the cli and the gui
 */
public interface Page {

    /**
     *
     * @throws UndoException
     */
    public void handle() throws UndoException;

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    public boolean isReadyToProceed();
    public void kill();
}
