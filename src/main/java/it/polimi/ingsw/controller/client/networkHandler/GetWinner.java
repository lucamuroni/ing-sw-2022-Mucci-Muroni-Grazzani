package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the winner (or winner in case of a tie) for the current game
 */
public class GetWinner {
    MessageHandler messageHandler;
    Game game;
    ArrayList<Gamer> winners;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetWinner(MessageHandler messageHandler, Game game)  {
        this.messageHandler = messageHandler;
        this.winners = new ArrayList<>();
        this.game = game;
    }

    /**
     * Method that handles the messages to get the winner/winners (in case of a draw) of the game
     * @return the arraylist with the winners
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public ArrayList<Gamer> handle() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read();
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read();
            String string = this.messageHandler.getMessagePayloadFromStream(WINNER.getFragment());
            if(!string.equals("Error")){
                boolean check = false;
                for (Gamer gamer : game.getGamers()) {
                    if (string.equals(gamer.getUsername())) {
                        winners.add(gamer);
                        check = true;
                    }
                }
                if (!check)
                    throw new AssetErrorException();
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(WINNER.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return winners;
    }
}
