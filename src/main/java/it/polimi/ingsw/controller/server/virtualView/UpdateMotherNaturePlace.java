package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.Island;

//TODO : classe che gestisce l'aggiornamento della posizione di madre natura


class UpdateMotherNaturePlace {
    Island island;
    MessageHandler messageHandler;

    public UpdateMotherNaturePlace(Island island, MessageHandler messageHandler){
        this.island = island;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException {
        // TODO :corpo della funzione (messaggio) tira tutte le eccezioni
        
    }
}
