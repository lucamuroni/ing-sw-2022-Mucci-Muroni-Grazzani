package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.view.cli.AnsiColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: javadoc
public class Server {
    private ServerSocket serverSocket;
    private final ClientReception clientReception;
    private final static int maxNumOfInstances = 4;
    private int numOfInstances = 0;
    private boolean isEveryThingOK = true;

    public Server(int portNumber){
        int errors = 0;
        int errorThreshold = 5;
        while(errors<errorThreshold && launchServerSocket(portNumber)){
            synchronized (this){
                try{
                    this.wait(2000);
                }catch (InterruptedException e){}finally {
                    errors++;
                }
            }
        }
        this.clientReception = new ClientReception(this.serverSocket);
        System.out.println("Server is about to power up");
        this.run();
    }

    private boolean launchServerSocket(int portNumber){
        try{
            this.serverSocket = new ServerSocket(portNumber);
            return false;
        }catch (IOException e){
            System.out.println("Could not instantiate a new ServerSocket ; Attempting again in 2 seconds");
            return true;
        }
    }

    private void run(){
        System.out.println("Opening connections");
        this.clientReception.start();
        System.out.println("Starting Lobby thread");
        this.gameStarter();
        System.out.println("Input q for quit");
        Scanner scanner = new Scanner(System.in);
        while (isEveryThingOK){
            if(scanner.nextLine().equals("q")){
                System.exit(0);
            }
        }
    }


    private void gameStarter(){
        Thread t = new Thread(()->{
            while(isEveryThingOK){
                Lobby lobbyToStart = null;
                boolean lobbyAlreadyChosen = false;
                synchronized (this.clientReception.getLobbies()){
                    System.out.println("dim lobby "+this.clientReception.getLobbies().size());
                    while (this.clientReception.getLobbies().isEmpty() || this.numOfInstances>maxNumOfInstances){
                        try {
                            this.clientReception.getLobbies().wait(500);
                        } catch (InterruptedException e) {
                            System.out.println("Could not wait for scanning of lobbies");
                            e.printStackTrace();
                        }
                    }
                    ArrayList<Lobby> lobbies = new ArrayList<>(this.clientReception.getLobbies());
                    for(Lobby lobby : lobbies){
                        if(lobby.isLobbyReady() && !lobbyAlreadyChosen){
                            lobbyAlreadyChosen = true;
                            lobbyToStart = lobby;
                            this.clientReception.getLobbies().remove(lobby);
                        }
                    }
                }
                if(lobbyToStart != null){
                    this.numOfInstances++;
                    lobbyToStart.startGame(this);
                    System.out.println(AnsiColor.GREEN.toString()+"A new Game Has started"+AnsiColor.RESET.toString());
                }
            }
            System.out.println("Lobby thread ended");
        });
        t.start();
    }

    public void signalFatalError(){
        System.out.println("Shutting down everything");
        this.isEveryThingOK = false;
        synchronized (this.clientReception.getLobbies()){
            this.clientReception.getLobbies().notifyAll();
        }
        this.clientReception.shutdown();
    }
}
