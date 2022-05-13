package it.polimi.ingsw.controller.networking;

import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
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
    private final String msg = "qui si che si fa sul serio";
    private Object flip =new Object();
    private boolean done = false;
    private String messgae;

    private void startServer(){
        try {
            serverSocket = new ServerSocket(port);
            server = new ConnectionHandler(serverSocket.accept());
        } catch (IOException e) {
            System.out.println("Sono cazzi");
        }
    }

    private void startClient(){
        try {
            clientSocket = new Socket(localhost,port);
            client = new ConnectionHandler(clientSocket);
        } catch (IOException e) {
            System.out.println("Sono cazzi");
        }

    }


    @Test
    void start() {
        Thread t = new Thread(()->{
            startServer();
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        Thread h = new Thread(()->{
            startClient();
            try {
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        h.start();
    }

    @Test
    void shutDown() {

    }

    @Test
    void getInputMessage() {
        Thread t = new Thread(()->{
            startServer();
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                String message = server.getInputMessage(5000);
                assertEquals(this.msg,message);
                synchronized (flip){
                    done = true;
                }
            } catch (TimeHasEndedException | ClientDisconnectedException e) {
                System.out.println("Non funziona un cazzo");
                e.printStackTrace();
            }
        });
        t.start();
        Thread h = new Thread(()->{
            startClient();
            try {
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            synchronized (this.client){
                try {
                    this.client.wait(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ho scritto");
            client.setOutputMessage(this.msg);
        });
       h.start();
        synchronized (flip){
            while (!done){
                try {
                    flip.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    void setOutputMessage() {
        Thread t = new Thread(()->{
            startServer();
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            server.setOutputMessage(msg);

        });
        t.start();

        Thread h = new Thread(()->{
            startClient();
            try {
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("sto aspettando");
            try {
                messgae = client.getInputMessage(15000);
                assertEquals(this.msg, messgae);
                synchronized (flip) {
                    done = true;
                }
            }catch (TimeHasEndedException | ClientDisconnectedException e) {
                System.out.println("Non funziona un cazzo");
                e.printStackTrace();
            }
            synchronized (flip){
                done = true;
            }
        });
        h.start();
        synchronized (flip){
            while (!done){
                try {
                    flip.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    void resetLines(){
        Thread t = new Thread(()->{
            startServer();
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            server.setOutputMessage(msg);

        });
        t.start();

        Thread h = new Thread(()->{
            startClient();
            try {
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("sto aspettando");
            try {
                messgae = client.getInputMessage(15000);
                assertEquals(this.msg, messgae);
                client.restLines();
                synchronized (flip) {
                    done = true;
                }
            }catch (TimeHasEndedException | ClientDisconnectedException e) {
                System.out.println("Non funziona un cazzo");
                e.printStackTrace();
            }
            synchronized (flip){
                done = true;
            }
        });
        h.start();
        synchronized (flip){
            while (!done){
                try {
                    flip.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}