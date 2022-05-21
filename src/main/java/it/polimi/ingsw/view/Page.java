package it.polimi.ingsw.view;

public interface Page {
    public void handle();
    public void kill();
    public boolean readyToProcede();
}
