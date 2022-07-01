package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca Muroni
 * This class represents the current game
 */
public class Game {
    private final ArrayList<Gamer> gamers;
    private final ArrayList<Island> islands;
    private final ArrayList<Cloud> clouds;
    private Island motherNaturePosition;
    private final Gamer self;
    private String currentPlayer;
    private GameType type;
    private int lobbySize;
    private Island chosenIsland;
    private PawnColor chosenColor;
    private Cloud chosenCloud;
    private final ArrayList<CharacterCard> cards;
    private int coins;

    /**
     * Constructor of the class
     * @param gamer is the gamer playing on his device
     */
    public Game(Gamer gamer) {
        this.gamers = new ArrayList<>();
        this.islands = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.self = gamer;
        this.gamers.add(self);
        this.createIslands();
        this.cards = new ArrayList<>();
        this.coins = -1;
    }

    /**
     * Method used to create the islands
     */
    private void createIslands() {
        for (int i = 0; i<12; i++) {
            this.islands.add(new Island(i+1));
        }
    }

    /**
     * Method used to create the clouds
     */
    private void createClouds(){
        for (int i = 0; i<lobbySize; i++) {
            this.clouds.add(new Cloud(i+1));
        }
    }

    /**
     * Getter method
     * @return return the gamer playing on his device
     */
    public Gamer getSelf() {
        return this.self;
    }

    /**
     * Getter method
     * @return the arrayList of gamers
     */
    public ArrayList<Gamer> getGamers() {
        return gamers;
    }

    /**
     * Getter method
     * @return the arrayList of islands
     */
    public ArrayList<Island> getIslands() {
        return islands;
    }

    /**
     * Getter method
     * @return the arrayList of clouds
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * Getter method
     * @return the position of motherNature
     */
    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    /**
     * Getter method
     * @return the island chosen by self
     */
    public Island getChosenIsland() {
        return chosenIsland;
    }

    /**
     * Getter method
     * @return the color chosen by self
     */
    public PawnColor getChosenColor() {
        return chosenColor;
    }

    /**
     * Getter method
     * @return the cloud chosen by self
     */
    public Cloud getChosenCloud() {
        return chosenCloud;
    }

    /**
     * Getter method
     * @return the version of the game that self is playing
     */
    public String getGameType() {
        return this.type.getName();
    }

    /**
     * Getter method
     * @return the number of players that are playing
     */
    public int getLobbySize() {
        return lobbySize;
    }

    /**
     * Getter method
     * @return the name of the current player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter method
     * @return the arrayList of characterCards drawn for this game
     */
    public ArrayList<CharacterCard> getCards() {
        return this.cards;
    }

    /**
     * Getter method
     * @return the coins owned by the player
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     * Setter method
     * @param coins are the coins owned by the player
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Setter method
     * @param card is the card that will be added
     */
    public void addCard(CharacterCard card) {
        this.cards.add(card);
    }

    /**
     * Setter method
     * @param motherNaturePosition is the new location of motherNature
     */
    public void setMotherNaturePosition(Island motherNaturePosition) {
        if (this.motherNaturePosition != null)
            this.motherNaturePosition.setMotherNaturePresent(false);
        this.motherNaturePosition = motherNaturePosition;
        motherNaturePosition.setMotherNaturePresent(true);
    }

    /**
     * Setter method
     * @param chosenIsland is the island chosen by self
     */
    public void setChosenIsland(Island chosenIsland) {
        this.chosenIsland = chosenIsland;
    }

    /**
     * Setter method
     * @param chosenColor is the color chosen by self
     */
    public void setChosenColor(PawnColor chosenColor) {
        this.chosenColor = chosenColor;
    }

    /**
     * Setter method
     * @param currentPlayer is the name of the current player
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Setter method
     * @param chosenCloud is the cloud chosen by self
     */
    public void setChosenCloud(Cloud chosenCloud) {
        this.chosenCloud = chosenCloud;
    }

    /**
     * Setter method
     * @param type is the type of game chosen by self
     */
    public void setType(GameType type) {
        this.type = type;
    }

    /**
     * Setter method
     * @param lobbySize is the number of players that self wants to play with
     */
    public void setLobbySize(int lobbySize) {
        this.lobbySize = lobbySize;
        this.clouds.clear();
        this.createClouds();
    }

    /**
     * Getter method
     * @param place is the location where to take the possible colors to choose from
     * @return the arrayList of possible colors
     */
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

    /**
     * Getter method
     * @param col is the color to check if still present
     * @return the arrayList of possible colors from waiting
     */
    public ArrayList<PawnColor> getWaitingColors(PawnColor col) {
        ArrayList<PawnColor> colors = new ArrayList<>(List.of(PawnColor.values()));
        for (PawnColor color : PawnColor.values()) {
            int num = Math.toIntExact(this.self.getDashBoard().getWaitingRoom().stream().filter(x -> x.getColor().equals(color)).count());
            if (num == 0) {
                colors.remove(color);
            } else {
                num = Math.toIntExact(this.self.getDashBoard().getHall().stream().filter(x -> x.getColor().equals(color)).count());
                if (num == 10 && !col.equals(color)) {
                    colors.remove(color);
                }
            }
        }
        return colors;
    }

    /**
     * Getter method
     * @return the arrayList of possible colors from hall
     */
    public ArrayList<PawnColor> getHallColors() {
        ArrayList<PawnColor> colors = new ArrayList<>(List.of(PawnColor.values()));
        for (PawnColor color : PawnColor.values()) {
            int num = Math.toIntExact(this.self.getDashBoard().getHall().stream().filter(x -> x.getColor().equals(color)).count());
            if (num == 0) {
                colors.remove(color);
            }
        }
        return colors;
    }

    /**
     * Getter method
     * @return the possible places (dashboard, island)
     */
    public int getPossiblePlace() {
        int num = this.self.getDashBoard().getHall().size();
        if (num == 50)
            return 1;
        return 2;
    }
}