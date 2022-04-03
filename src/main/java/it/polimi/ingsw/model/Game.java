package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.Cloud;
import it.polimi.ingsw.debug.Gamer;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Luca Muroni
 */
public class Game {
    private MotherNature motherNature;
    private ArrayList<Cloud> clouds;
    private ArrayList<Professor> profs;
    private ArrayList<Island> islands;
    private Bag bag;
    private ArrayList<Gamer> gamers;
    private Gamer currentPlayer;
    private int turnNumber;

    public Game (ArrayList<Gamer> gamers){
        this.gamers = new ArrayList<>(gamers);

        //this.motherNature = new MotherNature(/* manca come scegliere la prima isola casuale */);

        this.clouds = new ArrayList<Cloud>();
        int numClouds = 0;
        while (numClouds<gamers.size()){
            clouds.add(new Cloud());
            numClouds++;
        }

        this.profs = new ArrayList<Professor>();
        for (PawnColor color : PawnColor.values()){
            profs.add(new Professor(color));
        }

        this.islands = new ArrayList<Island>();
        int numIslands = 0;
        while(numIslands < 12){
            islands.add(new Island());
            numIslands++;
        }

        //this. currentPlayer = /* manca un metodo con cui scegliere randomicamente il primo giocatore all'inizio della partita */

        this.turnNumber = 1;
    }

    private void fillClouds (ArrayList<Student> students, Cloud cloud){
        cloud.pushStudents(students);
    }

    public void initIsland (ArrayList<Student> students){

    }

    public void moveMotherNature (Island island){
        this.motherNature.setPlace(island);
    }

    public ArrayList<Island> getMotherNatureDestination (){
        /* necessario prima portare AssistantCard e Gamer*/
        return null;
    }

    public void changeProfessorOwner (Gamer owner, PawnColor color){
        for (Professor prof : this.profs){
            if(prof.getColor().equals(color)){
                prof.setOwner(owner);
            }
        }
    }

    public Gamer checkIslandOwner (){
        //prendo l'isola da controllare
        Island islandToCheck = this.motherNature.getPlace();
        //creo un array con i colori dei prof posseduti dal currentPlayer
        ArrayList<PawnColor> colors = new ArrayList<PawnColor>();
        //cerco i profs posseduti dal currentPlayer
        for (Professor professor : this.profs){
            if (professor.getOwner().equals(currentPlayer)){ //metodo da modificare: l'equals dovrebbe controllare il token
                colors.add(professor.getColor());
            }
        }
        //calcolo l'influenza del currentPlayer sull'isola
        int influence = islandToCheck.getInfluenceByColor(colors); //influenza del giocatore corrente

        //prendo l'owner corrente dell'isola
        Optional<Gamer> ownerIsland = islandToCheck.getOwner();
        //come in currentPlayer
        ArrayList<PawnColor> colorsOwner = new ArrayList<PawnColor>();
        //come in currentPlayer
        for (Professor professor : this.profs){
            if (professor.getOwner().equals(ownerIsland)){ //metodo da modificare: l'equals dovrebbe controllare il token
                colorsOwner.add(professor.getColor());
            }
        }
        //come in currentPlayer
        int influenceOwner = islandToCheck.getInfluenceByColor(colorsOwner) + islandToCheck.getNumTowers(); //influenza del giocatore proprietario dell'isola

        if(influenceOwner<influence){
            return currentPlayer;
        }
        else{
            //return ownerIsland;
            //problema: conflitto con l'uso di Optional<Gamer>
            return currentPlayer;
        }
    }

    public void updatePlayersOrder (){
        Gamer pastFirstPlayer = gamers.get(0);
        gamers.removeIf(gamer -> gamer.getToken()==pastFirstPlayer.getToken());
        gamers.add(pastFirstPlayer);
    }
}
