package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;

import java.util.ArrayList;

public interface ViewHandler {
    AssistantCard selectCard(ArrayList<AssistantCard> cards);
}
