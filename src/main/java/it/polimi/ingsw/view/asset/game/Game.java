package it.polimi.ingsw.view.asset.game;

import java.util.ArrayList;
import java.util.List;


import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.asset.game.Island;

public class Game {
    private ArrayList<Gamer> gamers;
    private ArrayList<Island> islands;
    private ArrayList<Cloud> clouds;
    private Island motherNaturePosition;
    private Gamer self;
    private String currentPlayer;
    private GameType type;
    private int lobbySize;
    private Island chosenIsland;
    private PawnColor chosenColor;
    private Cloud chosenCloud;

    public Game(Gamer gamer) {
        this.gamers = new ArrayList<>();
        this.islands = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.self = gamer;
        this.gamers.add(self);
        this.createIslands();
    }

    private void createIslands() {
        for (int i = 0; i<12; i++) {
            this.islands.add(new Island(i+1));
        }
    }

    private void createClouds(){
        for (int i = 0; i<lobbySize; i++) {
            this.clouds.add(new Cloud(i+1));
        }
    }

    public Gamer getSelf() {
        return this.self;
    }

    public ArrayList<Gamer> getGamers() {
        return gamers;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public Island getChosenIsland() {
        return chosenIsland;
    }

    public PawnColor getChosenColor() {
        return chosenColor;
    }

    public Cloud getChosenCloud() {
        return chosenCloud;
    }

    public String getGameType() {
        return this.type.getName();
    }

    public int getLobbySize() {
        return lobbySize;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setMotherNaturePosition(Island motherNaturePosition) {
        if (this.motherNaturePosition != null)
            this.motherNaturePosition.setMotherNaturePresent(false);
        this.motherNaturePosition = motherNaturePosition;
        motherNaturePosition.setMotherNaturePresent(true);
    }

    public void setChosenIsland(Island chosenIsland) {
        this.chosenIsland = chosenIsland;
    }

    public void setChosenColor(PawnColor chosenColor) {
        this.chosenColor = chosenColor;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setChosenCloud(Cloud chosenCloud) {
        this.chosenCloud = chosenCloud;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public void setLobbySize(int lobbySize) {
        this.lobbySize = lobbySize;
        this.createClouds();
    }

    public ArrayList<PawnColor> getPossibleColors(int place) {
        ArrayList<PawnColor> colors = new ArrayList<>(List.of(PawnColor.values()));
        int num;
        for (PawnColor color : PawnColor.values()) {
            num = Math.toIntExact(this.self.getDashBoard().getWaitingRoom().stream().filter(x -> x.getColor().equals(color)).count());
            if (num == 0)
                colors.remove(color);
            else {
                if (place == 1) {
                    num = Math.toIntExact(this.self.getDashBoard().getHall().stream().filter(x -> x.getColor().equals(color)).count());
                        if (num == 10)
                            colors.remove(color);
                }
            }
        }
        return colors;
    }

    public int getPossiblePlace() {
        int num = this.self.getDashBoard().getHall().size();
        if (num == 50)
            return 1;
        return 2;
    }
}
