package it.polimi.ingsw.view;

public interface Page {
    public void handle();
    public boolean isProcessReady();
    public void setClearance(boolean clearance);
}
