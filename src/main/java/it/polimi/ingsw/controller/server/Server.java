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
    private boolean isEveryThingOK = true;

    public Server(int portNumber){
        while(launchServerSocket(portNumber)){
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
        this.clientReception.start();
        this.gameStarter();
        System.out.println("Input q for quit");
        Scanner scanner = new Scanner(System.in);
        while (isEveryThingOK){
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


    private void gameStarter(){
        Thread t = new Thread(()->{
            while(isEveryThingOK){
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
                    System.out.println(AnsiColor.GREEN.toString()+"A new Game Has started"+AnsiColor.RESET.toString());
                }
            }
        });
        t.start();
    }

}
