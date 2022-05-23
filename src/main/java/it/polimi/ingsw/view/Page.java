package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.page.UndoException;

public interface Page {
    public void handle() throws UndoException;
    public boolean isProcessReady();
    public void setClearance(boolean clearance);
}
