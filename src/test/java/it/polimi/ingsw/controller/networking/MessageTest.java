package it.polimi.ingsw.controller.networking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    Message msg;

    public void builder(){
        msg = new Message("ok","va bene",1);
    }
    @Test
    void getHeader() {
        builder();
        assertEquals("ok",msg.getHeader());
    }

    @Test
    void getPayload() {
        builder();
        assertEquals("va bene",msg.getPayload());
    }
}