package it.polimi.ingsw.model.pawn;

public enum TowerColor {
    BLACK("black"),
    WHITE("white"),
    GREY("grey");

    private final String color;

    private TowerColor(String color){
        this.color = color;
    }

    @Override
    public String toString() {
        return this.color;
    }
}
