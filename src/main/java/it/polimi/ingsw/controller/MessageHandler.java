package it.polimi.ingsw.controller;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Set;

/**
 * Class responsible for the translation between messages and JSON type of messages witch will be exchanged through sockets
 * @author Davide Grazzani
 */
public class MessageHandler {
    private JSONObject encoder;
    private final static String topKeyWord = "topicUniqueID";

    /**
     * Class Builder
     */
    public MessageHandler(){}

    /**
     * Method used for writing the message that will be sent through sockets.
     * If a message encoder is not present this method creates it and self-initialize with correct TopicID, otherwise it will append the message-fragment to create
     * the real message that will be sent
     * @param msg represent a fragment of a socket-Message
     */
    public void write(Message msg){
        if(this.encoder.equals(null)){
            this.encoder = new JSONObject();
            this.encoder.put(this.topKeyWord,msg.getUniqueTopicID());
        }
        this.encoder.put(msg.getHeader(),msg.getPayload());
    }

    /**
     * Method used to flush a message (created with write method) through the sockets.
     * @param connection is the connection established previously with the gamer
     */
    public void writeOut(ConnectionHandler connection){
        connection.setOutputMessage(this.encoder.toJSONString());
        this.encoder = null;
    }

    /**
     * Method used for reading an incoming message from client/server
     * @param connection represent the connection which you want to get the message from
     * @return an ArrayList of Messages
     */
    public ArrayList<Message> read(ConnectionHandler connection){
        ArrayList<Message> result = new ArrayList<Message>();
        int i = 0,uniqueID = 0;
        JSONObject decoder = new JSONObject();
        String messages = connection.getInputMessage();
        // Object messagesParsed = JSONValue.parse(messages);
        // this.decoder = (JSONObject) messagesParsed;
        decoder = (JSONObject) JSONValue.parse(messages);
        uniqueID = (int)decoder.get(this.topKeyWord);
        Set<String> keySet = decoder.keySet();
        for(String key : keySet){
            Message m = new Message(key,(String) decoder.get(key),uniqueID);
            result.add(m);
        }
        return result;
    }
}
