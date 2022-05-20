package it.polimi.ingsw.controller.networking;


import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
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
    private ArrayList<Message> incomingMessages;
    private int lastTopicRead;

    /**
     * Class Builder
     * @param socket is the socket of a client/server
     */
    public MessageHandler(Socket socket){
        connectionHandler = new ConnectionHandler(socket);
        incomingMessages = new ArrayList<Message>();
        this.lastTopicRead = 0;
    }

    /**
     * Method used for writing the message that will be sent through sockets.
     * If a message encoder is not present this method creates it and self-initialize with correct TopicID, otherwise it will append the message-fragment to create
     * the real message that will be sent
     * @param msg represent a fragment of a socket-Message
     * @throws MalformedMessageException if another message is present and differs by TopicID
     */
    public void write(Message msg) throws MalformedMessageException{
        if(this.encoder==null){
            this.encoder = new JSONObject();
            this.encoder.put(this.topKeyWord,msg.getUniqueTopicID());
        }else{
            if(msg.getUniqueTopicID() != (int)this.encoder.get(this.topKeyWord)){
                throw new MalformedMessageException("A message is already present, please writeOut() it before writing a new one");
            }
        }
        if(this.encoder.containsKey(msg.getHeader())){
            throw new MalformedMessageException("A message fragment with this key is already present");
        }
        this.encoder.put(msg.getHeader(),msg.getPayload());
    }

    /**
     * Method used to write an arraylist of Messages. This method relay on write method upper described
     * @param messages is the array of fragments of a socket-Message
     * @throws MalformedMessageException if another message is present and differs by TopicID
     */
    public void write(ArrayList<Message> messages) throws MalformedMessageException {
        for(Message msg: messages){
            this.write(msg);
        }
    }

    /**
     * Method used to flush a message (created with write method) through the sockets.
     */
    public void writeOut(){
        this.connectionHandler.setOutputMessage(this.encoder.toString());
        this.encoder = null;
    }

    /**
     * Method used to flush a message to a socket and await for the response. This method automatically topic-secure message throwing an error
     * in case a client responded wrongly to a request
     * @param milliSeconds are the milli-senconds given to a Client to perform a certain action
     * @throws TimeHasEndedException if the client does not respond in time
     * @throws ClientDisconnectedException if the client disconnects
     */
    public void writeOutAndWait(int milliSeconds) throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        int topicID = (int)this.encoder.get(this.topKeyWord);
        writeOut();
        this.read(milliSeconds);
        if(this.incomingMessages.get(0).getUniqueTopicID()!=topicID){
            clearIncomingMessages();
            throw new MalformedMessageException();
        }
    }

    /**
     * Method used for reading an incoming message from client/server
     * @param maxActionTimeOut are the milli-senconds given to a Client to perform a certain action
     * @throws TimeHasEndedException if the client does not respond in time
     * @throws ClientDisconnectedException if the client disconnects
     */
    public void read(int maxActionTimeOut) throws TimeHasEndedException, ClientDisconnectedException {
        int i = 0,uniqueID = 0;
        JSONObject decoder = new JSONObject();
        String messages = this.connectionHandler.getInputMessage(maxActionTimeOut);
        /* Object messagesParsed = JSONValue.parse(messages);
        this.decoder = (JSONObject) messagesParsed; */
        decoder = (JSONObject) JSONValue.parse(messages);
        uniqueID = (int)((Long)decoder.get(this.topKeyWord)).intValue();
        Set<String> keySet = decoder.keySet();
        for(String key : keySet){
            Message m = new Message(key,String.valueOf( decoder.get(key)),uniqueID);
            this.incomingMessages.add(m);
        }
    }

    /**
     * Method used for creating a stable connection between client and server
     */
    public void startConnection() throws IOException {
        this.connectionHandler.start();
    }

    /**
     * Method used for shutting down the connection between client and server
     */
    public void shutDown(){
        this.connectionHandler.shutDown();
    }

    /**
     * Method used for generating an unique topic identifier used for making sure request and response are synchronized
     * @return an integer between 2^30 and 2^31 representing a topic id
     */
    public int getNewUniqueTopicID(){
        Random random = new Random();
        int number = random.nextInt((int) Math.pow(2,30),(int) Math.pow(2,31));
        return number;
    }

    /**
     * Method used for cleaning and the cached messages already received from client
     */
    private void clearIncomingMessages(){
        this.connectionHandler.restLines();
    }

    /**
     * Method used for getting a payload of a messages given the associated key
     * @param key is the key associated with the searched payload
     * @return a string representing the payload
     * @throws MalformedMessageException if the arraylist of messages does not contain the key you searched for
     */
    public String getMessagePayloadFromStream(String key) throws MalformedMessageException {
        for(Message msg : this.incomingMessages){
            if(msg.getHeader().equals(key)){
                updateLastTopic(msg);
                this.incomingMessages.remove(msg);
                return msg.getPayload();
            }
        }
        throw new MalformedMessageException();
    }

    /**
     * Method based on function getMessagePayloadFromStream which ensures that a message contains a certain payload
     * @param payload is the expected payload
     * @param key is the key associated with the payload you want to check
     * @throws MalformedMessageException if the arraylist of messages does not contain the key you searched for or the Message Has not the expected Payload
     * @throws FlowErrorException if the payload of the message does not contain the expected value
     */
    public void assertOnEquals(String payload,String key) throws MalformedMessageException, FlowErrorException {
        if(!payload.equals(this.getMessagePayloadFromStream(key))){
            throw new FlowErrorException();
        }
    }

    public int getMessagesUniqueTopic(){
        return this.lastTopicRead;
    }

    private void updateLastTopic(Message message){
        this.lastTopicRead = this.incomingMessages.get(this.incomingMessages.indexOf(message)).getUniqueTopicID();
    }

}
