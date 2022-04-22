package it.polimi.ingsw.controller;

/**
 * @author Davide Grazzani
 * This class is used for modeling a single message shared between Server and Clients.
 * The Message class does NOT rapresent an entire command(or Message) that a host can handle,it rapresent only a fragment of it;
 * A correct wellformed Message is a combination of object type Message sharing same ID.
 * Messages are immutable.
 */
public class Message {
    private final String header;
    private final String payload;
    private final int uniqueTopicID;

    /**
     * Class builder
     * @param header is the header of the message
     * @param payload is the topic of the message
     * @param hash is the univocal number that represent a topic
     */
    public Message(String header,String payload,int hash){
        this.header = header;
        this.payload = payload;
        this.uniqueTopicID = hash;
    }

    /**
     *
     * @return
     */
    public String getHeader(){
        return this.header;
    }

    public String getPayload(){
        return this.payload;
    }

    public int getUniqueTopicID(){
        return this.uniqueTopicID;
    }
}
