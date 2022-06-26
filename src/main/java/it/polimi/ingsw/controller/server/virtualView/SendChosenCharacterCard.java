package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.gamer.ExpertGamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class SendChosenCharacterCard {
    private final ExpertGamer gamer;
    private final CharacterCard card;
    private final MessageHandler messageHandler;

    public SendChosenCharacterCard(CharacterCard card, ExpertGamer gamer, MessageHandler messageHandler) {
        this.gamer = gamer;
        this.card = card;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        ArrayList<Message> messages = new ArrayList<>();
        //messages.add(new Message(PLAYER_ID.getFragment(), String.valueOf(this.gamer.getToken()), topicId));
        messages.add(new Message(CHARACTER_CARD.getFragment(), this.card.getName(), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), CHARACTER_CARD.getFragment());
    }
}
