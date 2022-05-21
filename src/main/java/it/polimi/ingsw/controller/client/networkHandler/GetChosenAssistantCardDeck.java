package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class GetChosenAssistantCardDeck {
    private MessageHandler messageHandler;
    private Game game;

    private AssistantCardDeckFigures deck;

    public GetChosenAssistantCardDeck(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        int id = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment()));
        Gamer owner = null;
        for (Gamer gamer : game.getGamers()) {
            if (gamer.getId() == id) {
                owner = gamer;
            }
        }
        String name = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
        for (AssistantCardDeckFigures deckFigures: AssistantCardDeckFigures.values()) {
            if (name.equals(deckFigures.name())) {
                deck = deckFigures;
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD_DECK.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        assert owner != null;
        owner.updateFigure(deck);
    }
}
