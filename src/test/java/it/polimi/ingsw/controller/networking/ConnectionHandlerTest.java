package it.polimi.ingsw.controller.networking;

import org.junit.jupiter.api.Test;

import javax.swing.event.CaretListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionHandlerTest {
    private ConnectionHandler server;
    private ConnectionHandler client;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int sem = 0;
    private final int port =17098;
    private final String localhost = "127.0.0.1";

    private void startServer(){
        try {
            serverSocket = new ServerSocket(port);
            server = new ConnectionHandler(serverSocket.accept());
        } catch (IOException e) {
            assertEquals(false,true);
        }
    }

    private void startClient(){
        try {
            clientSocket = new Socket(localhost,port);
            client = new ConnectionHandler(clientSocket);
        } catch (IOException e) {
            assertEquals(false,true);
        }

    }


    @Test
    void start() {
        Thread t = new Thread(()->{
            startServer();
            server.start();
        });
        t.start();
        Thread h = new Thread(()->{
            startServer();
            client.start();
        });
        h.start();
    }

    @Test
    void shutDown() {

    }

    @Test
    void getInputMessage() {

    }

    @Test
    void setOutputMessage() {

    }
}