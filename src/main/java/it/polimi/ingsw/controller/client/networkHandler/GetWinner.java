package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Sara Mucci
 * Class that implements the message to get the winner (or winner in case of a draw) for the game
 */
public class GetWinner {
    MessageHandler messageHandler;
    Boolean stop = false;
    Game game;
    ArrayList<Gamer> winners;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetWinner(MessageHandler messageHandler)  {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to get the winner/winners (in case of a draw) of the game
     * @return the arraylist with the winners
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public ArrayList<Gamer> handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<Message>();
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String string = this.messageHandler.getMessagePayloadFromStream(WINNER.getFragment());
            if (string.equals("stop")) {
                stop = true;
            }
            else {
                for (Gamer gamer : game.getGamers()) {
                    if (string.equals(gamer.getUsername())) {
                        winners.add(gamer);
                    }
                }
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        messages.add(new Message(OK.getFragment(), "", topicId));
        this.messageHandler.write(messages);
        return winners;
    }
}
