package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.expert.CharacterCardDeck;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * Class that represents the expert version of a game
 */
public class ExpertGame extends Game {
    private int coinBank;
    private final CharacterCardDeck deck;
    private boolean moreSteps;
    private boolean villagerCard;
    private boolean isCharacterCardBeenPlayed;

    /**
     * Class constructor
     * @param expertGamers represents the gamers of the game
     */
    public ExpertGame(ArrayList<Gamer> expertGamers){
        super(expertGamers);
        if(this.getGamers().size()==2){
            this.coinBank = 18;
        }else{
            this.coinBank = 17;
        }
        this.deck = new CharacterCardDeck();
        this.moreSteps = false;
        this.villagerCard = false;
        this.isCharacterCardBeenPlayed = false;
        this.deck.initDeck();
    }

    /**
     * Method used to init the dashboards
     */
    public void initiateExpertDashboards(){
        for(ExpertGamer gamer : this.getExpertGamers()){
            gamer.getDashboard().setGame(this);
        }
    }

    /**
     * Method used to check if a professor has a new owner
     * @param color is the color of the professor to check
     * @return the owner (a new one or the old one)
     */
    @Override
    public Gamer changeProfessorOwner(PawnColor color) {
        Optional<Gamer> oldOwner= this.professors.stream().filter(x->x.getColor().equals(color)).map(Professor::getOwner).findFirst().orElse(Optional.empty());
        if(oldOwner.isEmpty() || oldOwner.get().equals(this.getCurrentPlayer())) {
            Objects.requireNonNull(this.professors.stream().filter(x -> x.getColor().equals(color)).findFirst().orElse(null)).setOwner(this.getCurrentPlayer());
            return this.getCurrentPlayer();
        }
        else {
            int oldInfluence = oldOwner.get().getDashboard().checkInfluence(color);
            int currentInfluence = this.getCurrentPlayer().getDashboard().checkInfluence(color);
            if (currentInfluence > oldInfluence || (villagerCard && currentInfluence >= oldInfluence)) {
                Objects.requireNonNull(this.professors.stream().filter(x -> x.getColor().equals(color)).findFirst().orElse(null)).setOwner(this.getCurrentPlayer());
                return this.getCurrentPlayer();
            }else{
                return oldOwner.get();
            }
        }
    }

    /**
     * Method used to add a new turn
     */
    @Override
    public void setTurnNumber() {
        super.setTurnNumber();
        super.getInfluenceCalculator().reset();
        this.moreSteps = false;
        this.villagerCard = false;
        this.isCharacterCardBeenPlayed = false;
    }

    /**
     * Method that returns the coins of the game
     * @return the number of coins
     */
    public int getCoinBank() {
        return this.coinBank;
    }

    /**
     * Method used when a gamer play a card and part of its cost return to the coin bank
     * @param value is the number of coins that returns to the coin bank
     */
    public void setCoinBank(int value){
        this.coinBank += value;
    }

    /**
     * Method that returns the gamers
     * @return the gamers in the game
     */
    public ArrayList<ExpertGamer> getExpertGamers() {
        ArrayList<Gamer> gamers = this.getGamers();
        ArrayList<ExpertGamer> expertGamers = new ArrayList<>();
        for(Gamer gamer : gamers){
            expertGamers.add((ExpertGamer) gamer);
        }
        return expertGamers;
    }

    /**
     * Method that returns the current player
     * @return the current player of the game
     */
    @Override
    public ExpertGamer getCurrentPlayer() {
        Gamer gamer = super.getCurrentPlayer();
        return (ExpertGamer) gamer;
    }

    /**
     * Method that returns the cards that can be played during the game
     * @return the playable cards
     */
    public ArrayList<CharacterCard> getGameCards() {
        return this.deck.getCards();
    }

    /**
     * Method that sets the variable moreSteps to true when a player plays the characterCard postman
     */
    public void setMoreSteps() {
        this.moreSteps = true;
    }

    /**
     * Method that returns the mother nature destination; in case it can perform more steps due to a characterCard played
     * @return the mother nature destination
     */
    @Override
    public ArrayList<Island> getMotherNatureDestination() {
        ArrayList<Island> result = new ArrayList<>(super.getMotherNatureDestination());
        int islandIndex = this.islands.indexOf(result.get(result.size()-1));
        int index;
        if(this.moreSteps){
            this.moreSteps = false;
            for(int i=1;i<=2;i++){
                index = islandIndex+i;
                if(index>=this.islands.size()){
                    index = index % islands.size();
                }
                result.add(this.islands.get(index));
            }
        }
        return result;
    }

    /**
     * Method used when a gamer plays the characterCard villager
     */
    public void setEqualProfessorFlag() {
        this.villagerCard = true;
    }

    /**
     * Method used to get the deck of characterCard
     * @return the deck
     */
    public CharacterCardDeck getDeck() {
        return deck;
    }

    /**
     * Method used to check if a gamer has already played a character card
     * @return true if has been played, false otherwise
     */
    public boolean isCharacterCardBeenPlayed(){
        return isCharacterCardBeenPlayed;
    }

    /**
     * Method used to set that the current player has played a card
     */
    public void setCharacterCardBeenPlayed(){
        isCharacterCardBeenPlayed = true;
    }
}