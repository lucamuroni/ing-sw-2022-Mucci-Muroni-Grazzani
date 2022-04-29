package it.polimi.ingsw.controller.server;

public enum GameType {
    NORMAL("normal"),
    EXPERT("expert");

    private String name;

    private GameType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
