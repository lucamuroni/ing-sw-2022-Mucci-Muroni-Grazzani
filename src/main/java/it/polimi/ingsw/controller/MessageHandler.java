package it.polimi.ingsw.controller;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

public class MessageHandler {
    private JSONObject encoder;
    private JSONObject decoder;

    public MessageHandler(){}

    public void write(Message msg){
        if(this.encoder.equals(null)){
            this.encoder = new JSONObject();
            this.encoder.put("topicUniqueID",msg.getUniqueTopicID());
        }
        this.encoder.put(msg.getHeader(),msg.getPayload());
    }

    public void writeOut(ConnectionHandler connection){
        connection.setOutputMessage(this.encoder.toJSONString());
        this.encoder = null;
    }

    public ArrayList<Message> read(ConnectionHandler connection){
        ArrayList<Message> result;
        String messagesStringFormat = connection.getInputMessage();
        Object messagesParsed = JSONValue.parse(messagesStringFormat);
        JSONObject messages = (JSONObject) messagesParsed;
        messages.
    }
}
