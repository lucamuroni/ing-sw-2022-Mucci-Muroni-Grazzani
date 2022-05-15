package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

public class Game {
    private ArrayList<Gamer> gamers;
    private ArrayList<Island> islands;
    private ArrayList<Cloud> clouds;
    private Gamer self;
    //private ArrayList<Student> bag;

    public Game(Gamer gamer) {
        this.gamers = new ArrayList<>();
        this.islands = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.self = gamer;
    }

    public void updateGame(ArrayList<Gamer> gamers, ArrayList<Island> islands, ArrayList<Cloud> clouds) {
        this.gamers.addAll(gamers);
        this.islands.addAll(islands);
        this.clouds.addAll(clouds);
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
}
