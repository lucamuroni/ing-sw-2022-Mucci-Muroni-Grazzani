package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the winner (or winners in case of a tie) for the current game
 */
public class GetWinner {
    MessageHandler messageHandler;
    Game game;
    ArrayList<Gamer> winners;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetWinner(MessageHandler messageHandler, Game game)  {
        this.messageHandler = messageHandler;
        this.winners = new ArrayList<>();
        this.game = game;
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public ArrayList<Gamer> handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
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