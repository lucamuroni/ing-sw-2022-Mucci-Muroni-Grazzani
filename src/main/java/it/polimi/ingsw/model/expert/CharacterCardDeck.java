package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CharacterCardDeck {
    private ArrayList<CharacterCard> cards;

    public CharacterCardDeck(){
        this.cards = new ArrayList<>();
        Collections.addAll(this.cards, CharacterCard.values());
    }

    public ArrayList<CharacterCard> drawCards() {
        ArrayList<CharacterCard> playableCards = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            Random random = new Random();
            int rand = random.nextInt(0, this.cards.size());
            playableCards.add(this.cards.get(rand));
        }
        return playableCards;
    }
}
