package it.polimi.ingsw.view.asset.game;

import java.util.ArrayList;

public class Game {
    private ArrayList<Gamer> gamers;
    private ArrayList<it.polimi.ingsw.model.Island> islands;
    private ArrayList<Cloud> clouds;
    //TODO: confrontarsi con Grazza per capire se va bene MN in questo modo oppure è necessario creare una classe
    //      apposta perché serve alla cli/gui
    private Island motherNaturePosition;
    private Gamer self;
    //private ArrayList<Student> bag;

    public void setMotherNaturePosition(Island motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    public Game(Gamer gamer) {
        this.gamers = new ArrayList<>();
        this.islands = new ArrayList<it.polimi.ingsw.model.Island>();
        this.clouds = new ArrayList<>();
        this.self = gamer;
    }

    public void updateGame(ArrayList<Gamer> gamers, ArrayList<it.polimi.ingsw.model.Island> islands, ArrayList<Cloud> clouds, Island motherNaturePosition) {
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

    public ArrayList<it.polimi.ingsw.model.Island> getIslands() {
        return islands;
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }
}
