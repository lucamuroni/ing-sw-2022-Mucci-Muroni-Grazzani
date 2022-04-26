package it.polimi.ingsw.controller.networking;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Class responsible for the translation between messages and JSON type of messages witch will be exchanged through sockets
 * @author Davide Grazzani
 */
public class MessageHandler {
    private JSONObject encoder;
    private final static String topKeyWord = "topicUniqueID";
    private final ConnectionHandler connectionHandler;

    /**
     * Class Builder
     * @param socket is the socket of a client/server
     */
    public MessageHandler(Socket socket){
        connectionHandler = new ConnectionHandler(socket);
    }

    /**
     * Method used for writing the message that will be sent through sockets.
     * If a message encoder is not present this method creates it and self-initialize with correct TopicID, otherwise it will append the message-fragment to create
     * the real message that will be sent
     * @param msg represent a fragment of a socket-Message
     * @throws MalformedMessageException if a message has a different topicID
     */
    public void write(Message msg) throws MalformedMessageException{
        if(this.encoder.equals(null)){
            this.encoder = new JSONObject();
            this.encoder.put(this.topKeyWord,msg.getUniqueTopicID());
        }else{
            if(msg.getUniqueTopicID() != (int)this.encoder.get(this.topKeyWord)){
                throw new MalformedMessageException("Message already present, please writeOut() it before writing a new one");
            }
        }
        this.encoder.put(msg.getHeader(),msg.getPayload());
    }

    /**
     * Method used to flush a message (created with write method) through the sockets.
     */
    public void writeOut(){
        this.connectionHandler.setOutputMessage(this.encoder.toJSONString());
        this.encoder = null;
    }

    /**
     * Method used to flush a message to a socket and await for the response. This method automatically topic-secure message throwing an error
     * in case a client responded wrongly to a request
     * @param milliSeconds
     * @return
     * @throws TimeHasEndedException
     * @throws ClientDisconnectedException
     */
    public ArrayList<Message> writeOutAndWait(int milliSeconds) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        int topicID = (int)this.encoder.get(this.topKeyWord);
        writeOut();
        ArrayList<Message> messages = new ArrayList<Message>();
        messages = this.read(milliSeconds);
        if(messages.get(0).getUniqueTopicID()!=topicID){
            throw new MalformedMessageException();
        }
        return messages;
    }



    /**
     * Method used for reading an incoming message from client/server
     * @return an ArrayList of Messages
     */
    public ArrayList<Message> read(int maxActionTimeOut) throws TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> result = new ArrayList<Message>();
        int i = 0,uniqueID = 0;
        JSONObject decoder = new JSONObject();
        String messages = this.connectionHandler.getInputMessage(maxActionTimeOut);
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

    public void startConnection(){
        this.connectionHandler.start();
    }

    public void shutDown(){
        this.connectionHandler.shutDown();
    }

    public int getNewUniqueTopicID(){
        Random random = new Random();
        int number = random.nextInt((int) Math.pow(2,30),(int) Math.pow(2,31));
        return number;
    }
}
