package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;

/**
 * @author Luca Muroni
 * This class is used to get the possible characterCards to choose to play
 */
public class GetPossibleCharacters {
    private final MessageHandler messageHandler;
    private final Game game;
    private final ArrayList<CharacterCard> cards;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetPossibleCharacters(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.cards = new ArrayList<>();
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @return the arrayList of possible choices
     */
    public ArrayList<CharacterCard> handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException  {
        this.messageHandler.read();
        int size = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        boolean check;
        for (int i = 0; i<size; i++) {
            this.messageHandler.read();
            String name = this.messageHandler.getMessagePayloadFromStream(CHARACTER_CARD.getFragment());
            check = false;
            for (CharacterCard card : this.game.getCards()) {
                if (card.getName().equals(name)) {
                    cards.add(card);
                    check = true;
                    break;
                }
            }
            if (!check) {
                throw new AssetErrorException();
            }
        }
        return cards;
    }
}
