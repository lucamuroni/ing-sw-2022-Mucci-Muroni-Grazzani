package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

public interface VirtualViewInterface {
    public PawnColor getMovedStudentColor(MessageHandler messageHandler);
    public int getMovedStudentLocation(MessageHandler messageHandler);
    public AssistantCard getChosenAssistantCard(MessageHandler messageHandler, ArrayList<AssistantCard> assistantCards);
    public int getMotherNaturePosition(MessageHandler messageHandler, ArrayList<Island> islands);
}
