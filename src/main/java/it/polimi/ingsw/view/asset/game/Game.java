package it.polimi.ingsw.view.asset.game;

import java.util.ArrayList;


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
    }

    public void updateGame(ArrayList<Gamer> gamers, ArrayList<Island> islands, ArrayList<Cloud> clouds, Island motherNaturePosition) {
        this.gamers.addAll(gamers);
        this.islands.addAll(islands);
        this.clouds.addAll(clouds);
        this.motherNaturePosition = motherNaturePosition;
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
        this.motherNaturePosition = motherNaturePosition;
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
    }

}
