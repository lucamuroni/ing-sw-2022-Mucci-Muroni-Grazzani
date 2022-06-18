package it.polimi.ingsw.controller.networking;

import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

//TODO: da risolvere perchè non va
class MessageHandlerTest {
/*    private MessageHandler client;
    private MessageHandler server;
    private final int port =17099;
    private final String localhost = "127.0.0.1";
    private final static int defaultTimeOut = 10000;

    public void startClient() throws IOException {
        Socket socket = new Socket(localhost,port);
        this.client = new MessageHandler(socket);
        this.client.startConnection();
    }

    public void startServer() throws IOException{
        ServerSocket socket = new ServerSocket(port);
        this.server = new MessageHandler(socket.accept());
        this.server.startConnection();
    }


    @Test
    void write() {
    }

    @Test
    void testWrite() {

    }

    @Test
    void writeOut() {
        Thread t = new Thread(()->{
            try {
                startServer();
                System.out.println("s :Server started");
            } catch (IOException e) {
                System.out.println("s :Error while opening server");
            }
            try {
                System.out.println("s :aspetto qualche messaggio");
                this.server.read(defaultTimeOut);
            } catch (TimeHasEndedException | ClientDisconnectedException e) {
                System.out.println("s: non sono riuscito a ricevere nemmeno il primo messaggio");
            }
            try {
                System.out.println("s: controllo correttezza primo messaggio");
                this.server.assertOnEquals("primoPayload","Ciao");
                System.out.println("s : printo secondo messaggio");
                System.out.println(this.server.getMessagePayloadFromStream("bello"));
            } catch (MalformedMessageException | FlowErrorException e) {
                System.out.println("s :qualcosa è già andata storta con i primi 2 messaggi");
            }
            try{
                this.server.assertOnEquals("sti cazzi","non c'è");
            } catch (MalformedMessageException | FlowErrorException e) {
                System.out.println("s :Good boy you founded a expected exception");
            }finally {
                try {
                    this.server.read(defaultTimeOut);
                } catch (TimeHasEndedException |ClientDisconnectedException e) {
                    System.out.println("s :non sono riuscito a ricevere il secondo e terzo messaggio");
                }
                try {
                    this.server.write(new Message("Status","Test concluso con successo", this.server.getMessagesUniqueTopic()));
                    this.server.writeOut();
                } catch (MalformedMessageException e) {
                    System.out.println("s :sono cazzi");
                }
                try {
                    System.out.println("s :Ultimo sforzo");
                    server.assertOnEquals("terzoPayload","cazzo");
                    server.assertOnEquals("quartoPayload","bufu");
                } catch (MalformedMessageException |FlowErrorException e) {
                    System.out.println("s: errore Proprio alla fine");
                }
            }
            System.out.println("Server ha finito");
        });
        t.start();
        Thread h = new Thread(()->{
            try {
                startClient();
            } catch (IOException e) {
                System.out.println("c: Error while opening client");
            }
            int topic = this.client.getNewUniqueTopicID();
            try {
                this.client.write(new Message("Ciao","primoPayload",topic));
                this.client.write(new Message("bello","secondoPayload",topic));
                this.client.writeOut();
                System.out.println("c: Ho scritto i primi 2 messaggi");
                this.client.write(new Message("cazzo","terzoPayload",topic));
            } catch (MalformedMessageException e) {
                throw new RuntimeException(e);
            }
            try{
                this.client.write(new Message("Ciao","terzoPayload",1));
                System.out.println("Error revealed");
            }catch (MalformedMessageException e ){
                System.out.println("c :Good boy you founded a expected exception");
            }finally {
                try {
                    this.client.write(new Message("bufu","quartoPayload",topic));
                    System.out.println("c :Awaiting for msg");
                    this.client.writeOutAndWait(defaultTimeOut);
                } catch (TimeHasEndedException | MalformedMessageException | ClientDisconnectedException e) {
                    System.out.println("Cazzo di errore trovato qui");
                }
                try {
                    this.client.assertOnEquals("Test concluso con successo","Status");
                } catch (FlowErrorException | MalformedMessageException e) {
                    System.out.println("Cazzo di errore trovato");
                }
                System.out.println("Client ha finito");
            }
        });
        h.start();
    }

    @Test
    void simpleReadTest() {
        Thread server = new Thread(()->{
            try {
                startServer();
            } catch (IOException e) {
                System.out.println("Error while opening server");
            }
            try {
                this.server.write(new Message("header", "payload",this.server.getNewUniqueTopicID()));
                this.server.writeOut();
                System.out.println("Wrote out successfully");
            } catch (MalformedMessageException e) {
                System.out.println("Could not write anything");
            }
        });
        server.start();
        Thread client = new Thread(()->{
            try {
                startClient();
            } catch (IOException e) {
                System.out.println("Error while opening client");
            }
            try {
                this.client.read(10000);
            } catch (ClientDisconnectedException | TimeHasEndedException e) {
                System.out.println("Could not read anything");
            }
            try {
                System.out.println(this.client.getMessagePayloadFromStream("header"));
            } catch (MalformedMessageException e) {
                System.out.println("Wrong msg");
            }
        });
        client.start();
    }

    @Test
    void read() {
    }

    @Test
    void startConnection() {
    }

    @Test
    void shutDown() {
    }

    @Test
    void getNewUniqueTopicID() {
    }

    @Test
    void getMessagePayloadFromStream() {
    }

    @Test
    void assertOnEquals() {
    }*/
}