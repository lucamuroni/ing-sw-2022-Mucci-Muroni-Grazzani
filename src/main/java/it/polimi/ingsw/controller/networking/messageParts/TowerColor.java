package it.polimi.ingsw.controller.networking.messageParts;

public enum TowerColor {
    WHITE("White"),
    BLACK("Black"),
    GREY("Grey");

    private final String color;

    private TowerColor(String color){
        this.color = color;
    }
}
