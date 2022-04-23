package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.debug.CharacterCard;
import it.polimi.ingsw.model.debug.CharacterCardDeck;
import it.polimi.ingsw.model.gamer.ExpertGamer;

import java.util.ArrayList;
import java.util.Random;

public class ExpertGame extends Game {
    private int coinBank;
    private ArrayList<ExpertGamer> expertGamers;
    private ExpertGamer currentPlayer;
    private CharacterCardDeck deck;
    private ArrayList<CharacterCard> gameCards;

    public ExpertGame(ArrayList<ExpertGamer> expertGamers){
        super(expertGamers.size());
        this.coinBank = 20;
        this.expertGamers = new ArrayList<ExpertGamer>(expertGamers);
        this.deck = new CharacterCardDeck();
        initiateGamersOrder();
        initDeck();
    }

    private void initDeck() {
        this.gameCards = new ArrayList<CharacterCard>(this.deck.drawCards());
    }

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

    public void playCard(int coins, CharacterCard card){
        this.currentPlayer.getExpertDashboard().setCoins(-coins);
        card.activate();
    }

    public int getCoinBank() {
        return this.coinBank;
    }

    public ArrayList<ExpertGamer> getExpertGamers() {
        return this.expertGamers;
    }

    @Override
    public ExpertGamer getCurrentPlayer() {
        return this.currentPlayer;
    }

    public ArrayList<CharacterCard> getGameCards() {
        return gameCards;
    }
}
