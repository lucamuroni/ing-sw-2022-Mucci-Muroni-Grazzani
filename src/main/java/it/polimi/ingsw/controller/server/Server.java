package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.view.cli.AnsiColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Top Class of the server, used to launch the connection with clients and starting games
 */
public class Server {
    private ServerSocket serverSocket;
    private final ClientReception clientReception;

    /**
     * Class builder
     * @param portNumber is the number of the port upon which the socket will be opened
     */
    public Server(int portNumber){
        int i = 0;
        while(launchServerSocket(portNumber)){
            if(i>=5){
                System.out.print(AnsiColor.RED+"Error while opening socket on port "+portNumber+AnsiColor.RESET+"\n"+"Shutting down");
                System.exit(1);
            }
            i++;
            synchronized (this){
                try{
                    this.wait(2000);
                }catch (InterruptedException e){}
            }
        }
        this.clientReception = new ClientReception(this.serverSocket);
        System.out.println("Server is about to power up");
        this.run();
    }

    /**
     * Method used to open up the socket
     * @param portNumber is the number of the port on which the socket will be opened
     * @return false if a connection has been established
     */
    private boolean launchServerSocket(int portNumber){
        try{
            this.serverSocket = new ServerSocket(portNumber);
            return false;
        }catch (IOException e){
            System.out.println("Could not instantiate a new ServerSocket ; Attempting again in 2 seconds");
            return true;
        }
    }

    /**
     * Method used to offer an input to close the server
     */
    private void run(){
        this.clientReception.start();
        this.gameStarter();
        System.out.println("Input q for quit");
        Scanner scanner = new Scanner(System.in);
        while (true){
            if(scanner.nextLine().equals("q")){
                System.exit(0);
            }
            synchronized (this){
                try {
                    this.wait(500);
                } catch (InterruptedException e) {}
            }
        }
    }


    /**
     * Method used to start a lobby if the lobby is ready
     */
    private void gameStarter(){
        Thread t = new Thread(()->{
            while(true){
                Lobby lobbyToStart = null;
                boolean lobbyAlreadyChosen = false;
                ArrayList<Lobby> lobbies;
                synchronized (this.clientReception.getLobbies()) {
                    while (this.clientReception.getLobbies().isEmpty()) {
                        try {
                            this.clientReception.getLobbies().wait(3000);
                        } catch (InterruptedException e) {
                        }
                    }
                    lobbies = new ArrayList<>(this.clientReception.getLobbies());
                }
                for(Lobby lobby : lobbies){
                    if(lobby.isLobbyReady() && !lobbyAlreadyChosen){
                        lobbyAlreadyChosen = true;
                        lobbyToStart = lobby;
                        synchronized (this.clientReception.getLobbies()){
                            this.clientReception.getLobbies().remove(lobby);
                        }
                    }
                }
                if(lobbyToStart != null){
                    lobbyToStart.startGame();
                    System.out.println(AnsiColor.GREEN +"A new Game Has started"+ AnsiColor.RESET);
                }
                synchronized (this){
                    try {
                        this.wait(5000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        t.start();
    }

}
