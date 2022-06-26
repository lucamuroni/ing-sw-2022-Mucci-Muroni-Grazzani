package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.expert.CharacterCardDeck;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import org.ietf.jgss.GSSManager;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * @author Luca Muroni
 * Class that represents an Expert Game
 */
public class ExpertGame extends Game {
    private int coinBank;
    private final CharacterCardDeck deck;
    private ArrayList<CharacterCard> gameCards;
    private boolean moreSteps;
    private boolean villagerCard;

    /**
     * Class constructor
     * @param expertGamers represents the gamers of the game
     */
    public ExpertGame(ArrayList<Gamer> expertGamers){
        super(expertGamers);
        this.coinBank = 20;
        this.deck = new CharacterCardDeck();
        this.moreSteps = false;
        this.villagerCard = false;
        //initiateGamersOrder();
        initDeck();
    }

    /**
     * Method that creates a new deck for the game
     */
    private void initDeck() {
        this.gameCards = new ArrayList<>(this.deck.drawCards());
    }

    @Override
    public Gamer changeProfessorOwner(PawnColor color) throws Exception {
        Optional<Gamer> oldOwner= this.professors.stream().filter(x->x.getColor().equals(color)).map(x->x.getOwner()).findFirst().orElse(Optional.empty());
        if(oldOwner==null){
            throw new Exception();
        }
        else if(oldOwner.isEmpty() || oldOwner.equals(this.getCurrentPlayer())) {
            this.professors.stream().filter(x->x.getColor().equals(color)).findFirst().orElse(null).setOwner(this.getCurrentPlayer());
            return this.getCurrentPlayer();
        }
        else {
            int oldInfluence = oldOwner.get().getDashboard().checkInfluence(color);
            int currentInfluence = this.getCurrentPlayer().getDashboard().checkInfluence(color);
            if (currentInfluence > oldInfluence || (villagerCard && currentInfluence >= oldInfluence)) {
                this.professors.stream().filter(x->x.getColor().equals(color)).findFirst().orElse(null).setOwner(this.getCurrentPlayer());
                return this.getCurrentPlayer();
            }else{
                return oldOwner.get();
            }
            //else newOwner = oldOwner;
        }
    }

    @Override
    public void setTurnNumber() {
        super.setTurnNumber();
        super.getInfluenceCalculator().reset();
        this.moreSteps = false;
        this.villagerCard = false;
    }

    /**
     * Method that decides a new order for the players
     */
    /*private void initiateGamersOrder() {
        ArrayList<ExpertGamer> players = new ArrayList<>(this.expertGamers);
        this.expertGamers.clear();
        Random rand = new Random();
        this.this.getCurrentPlayer() = players.get(rand.nextInt(players.size()));
        this.expertGamers.add(this.getCurrentPlayer());
        players.remove(this.getCurrentPlayer());
        int size = players.size();
        for (int i = 0;i<size;i++) {
            ExpertGamer player = players.get(rand.nextInt(players.size()));
            this.expertGamers.add(player);
            players.remove(player);
        }
    }*/

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
     * @return the cards of the game
     */
    public ArrayList<CharacterCard> getGameCards() {
        return this.deck.getCards();
    }

    /**
     * Method that sets the variable moreSteps to true
     */
    public void setMoreSteps() {
        this.moreSteps = true;
    }

    /**
     * Method that returns the mother nature destination in case it can perform more steps due to a character card played
     * @return the mother nature destination
     */
    @Override
    public ArrayList<Island> getMotherNatureDestination() {
        ArrayList<Island> result = new ArrayList<Island>(super.getMotherNatureDestination());
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

    public void setEqualProfessorFlag() {
        this.villagerCard = true;
    }

    public CharacterCardDeck getDeck() {
        return deck;
    }
}