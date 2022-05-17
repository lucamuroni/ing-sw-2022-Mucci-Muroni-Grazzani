package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.model.AssistantCardDeck;

public class SendCardDeck {
    MessageHandler messageHandler;
    AssistantCardDeck assistantCardDeck;

    public SendCardDeck(AssistantCardDeck assistantCardDeck, MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.assistantCardDeck = assistantCardDeck;
    }

    public void handle() {

    }
}
