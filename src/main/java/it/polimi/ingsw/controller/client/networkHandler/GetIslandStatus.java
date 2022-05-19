package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.asset.game.Island;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Sara Mucci
 * Class that updates the island's status in the view
 */
public class GetIslandStatus {
    MessageHandler messageHandler;
    Boolean stop = false;
    Game game;
    int numTowers;
    ArrayList<Student> students;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetIslandStatus(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the update of the island in the view
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<Message>();
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            //TODO: Il server non deve prima mandare l'id dell'isola di qui fare l'update?
            String string = this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment());
            if (string.equals("stop")) {
                stop = true;
            }
            else {
                int result  =Integer.parseInt(string);
                for (Island island: game.getIslands()) {
                    if (result == island.getId()) {
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int owner = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment()));
                        if (owner == 0) {
                            island.setOwner(null);  //TODO: aggiungere in island di asset un metodo setOwner
                        }
                        else if (owner > 0) {
                            for (Gamer gamer: game.getGamers()){
                                if (owner == gamer.getId()) {
                                    island.setOwner(gamer);
                                }
                            }
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        numTowers = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(NUM_TOWERS.getFragment()));
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_RED.getFragment()));
                        if (colorRed == 0) {
                            students.add(null);
                        }
                        else if (colorRed > 0) {
                            for (int i = 0; i < colorRed; i++) {
                                Student redStudent = new Student(PawnColor.RED);
                                students.add(redStudent);
                            }
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_BLUE.getFragment()));
                        if (colorBlue == 0) {
                            students.add(null);
                        }
                        else if (colorBlue > 0) {
                            for (int i = 0; i < colorBlue; i++) {
                                Student blueStudent = new Student(PawnColor.BLUE);
                                students.add(blueStudent);
                            }
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_YELLOW.getFragment()));
                        if (colorYellow == 0) {
                            students.add(null);
                        }
                        else if (colorYellow > 0) {
                            for (int i = 0; i < colorYellow; i++) {
                                Student yellowStudent = new Student(PawnColor.YELLOW);
                                students.add(yellowStudent);
                            }
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_GREEN.getFragment()));
                        if (colorGreen == 0) {
                            students.add(null);
                        }
                        else if (colorGreen > 0) {
                            for (int i = 0; i < colorGreen; i++) {
                                Student greenStudent = new Student(PawnColor.GREEN);
                                students.add(greenStudent);
                            }
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorPink = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_PINK.getFragment()));
                        if (colorPink == 0) {
                            students.add(null);
                        }
                        else if (colorPink > 0) {
                            for (int i = 0; i < colorPink; i++) {
                                Student pinkStudent = new Student(PawnColor.PINK);
                                students.add(pinkStudent);
                            }
                        }
                        island.updateIsland(students, numTowers, owner.getColor()); //TODO: il server deve passare il colore delle torri o lo ottengo dall'owner?
                    }
                }
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        messages.add(new Message(OK.getFragment(), "", topicId));
        this.messageHandler.write(messages);
    }
}
