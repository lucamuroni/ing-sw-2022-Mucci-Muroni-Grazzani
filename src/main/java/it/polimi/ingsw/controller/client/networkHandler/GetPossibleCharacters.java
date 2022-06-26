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

public class GetPossibleCharacters {
    private final MessageHandler messageHandler;
    private final Game game;
    private final ArrayList<CharacterCard> cards;

    public GetPossibleCharacters(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.cards = new ArrayList<>();
    }

    public ArrayList<CharacterCard> handle() throws MalformedMessageException, ClientDisconnectedException, AssetErrorException {
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
