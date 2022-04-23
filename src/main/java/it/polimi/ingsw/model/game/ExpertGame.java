package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.debug.CharacterCard;
import it.polimi.ingsw.model.debug.CharacterCardDeck;
import it.polimi.ingsw.model.gamer.ExpertGamer;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Luca Muroni
 * Class that represents an Expert Game
 */
public class ExpertGame extends Game {
    private int coinBank;
    private ArrayList<ExpertGamer> expertGamers;
    private ExpertGamer currentPlayer;
    private CharacterCardDeck deck;
    private ArrayList<CharacterCard> gameCards;

    /**
     * Class constructor
     * @param expertGamers represents the gamers of the game
     */
    public ExpertGame(ArrayList<ExpertGamer> expertGamers){
        super(expertGamers.size());
        this.coinBank = 20;
        this.expertGamers = new ArrayList<ExpertGamer>(expertGamers);
        this.deck = new CharacterCardDeck();
        initiateGamersOrder();
        initDeck();
    }

    /**
     * Method that creates a new deck for the game
     */
    private void initDeck() {
        this.gameCards = new ArrayList<CharacterCard>(this.deck.drawCards());
    }

    /**
     * Method that decides a new order for the players
     */
    private void initiateGamersOrder() {
        ArrayList<ExpertGamer> players = new ArrayList<>(this.expertGamers);
        this.expertGamers.clear();
        Random rand = new Random();
        this.currentPlayer = players.get(rand.nextInt(players.size()));
        this.expertGamers.add(currentPlayer);
        players.remove(currentPlayer);
        int size = players.size();
        for (int i = 0;i<size;i++) {
            ExpertGamer player = players.get(rand.nextInt(players.size()));
            this.expertGamers.add(player);
            players.remove(player);
        }
    }

    /**
     * Method that plays a card
     * @param coins represents the card cost
     * @param card represents the card to be played
     */
    public void playCard(int coins, CharacterCard card){
        this.currentPlayer.getExpertDashboard().setCoins(-coins);
        //DUBBIO: non si dovrebbe anche aggiornare la coinBank con i coin pagati dal giocatore?
        card.activate();
    }

    /**
     * Method that returns the coin bank
     * @return the coin bank of the game
     */
    public int getCoinBank() {
        return this.coinBank;
    }

    /**
     * Method that returns the gamers
     * @return the gamers in the game
     */
    public ArrayList<ExpertGamer> getExpertGamers() {
        return this.expertGamers;
    }

    /**
     * Method that returns the current player
     * @return the current player of the game
     */
    @Override
    public ExpertGamer getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Method that returns the cards that can be played during the game
     * @return the cards of the game
     */
    public ArrayList<CharacterCard> getGameCards() {
        return gameCards;
    }
}
