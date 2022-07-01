package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.networking.*;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;
import it.polimi.ingsw.view.cli.AnsiColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to welcome players when first connecting to the server
 * @author Davide Grazzani
 */
class ClientReception extends Thread{
    private final ServerSocket serverSocket;
    private final ArrayList<Lobby> lobbies;

    /**
     * Class builder
     * @param socket is the socket of a new client
     */
    public ClientReception(ServerSocket socket){
        this.serverSocket = socket;
        this.lobbies = new ArrayList<Lobby>();
    }

    /**
     * Main method used to start and run all threads responsible for connecting players to server
     */
    @Override
    public void run() {
        MessageHandler messageHandler;
        while(true){
            try {
                messageHandler = new MessageHandler(this.serverSocket.accept());
                messageHandler.startConnection();
                Player player = new Player(messageHandler);
                playerHandShake(player);
            } catch (IOException e) {
                System.out.println("Error while accepting a new client on ServerSocket");
                e.printStackTrace();
            }
        }
    }

    /**
     * Method used to first connect to a new client
     * @param player is the player you want to handle
     */
    private void playerHandShake(Player player){
        Thread t = new Thread(() -> {
            int uniqueMsgID = player.getMessageHandler().getNewUniqueTopicID();
            ArrayList<Message> messages = new ArrayList<Message>();
            Message m0 = new Message(MessageFragment.GREETINGS.getFragment(), "",uniqueMsgID);
            try {
                player.getMessageHandler().write(m0);
            } catch (MalformedMessageException e1) {
                System.out.println("Strange behavior detected : while initializing some messages where already present");
                player.getMessageHandler().writeOut();
                try{
                    player.getMessageHandler().write(m0);
                }catch( MalformedMessageException e2){
                    System.out.println("Broken MessageHandler revealed");
                    e2.printStackTrace();
                }
            }
            try {
                player.getMessageHandler().writeOutAndWait();
                player.getMessageHandler().assertOnEquals(MessageFragment.OK.getFragment(), MessageFragment.GREETINGS.getFragment());
                messages.clear();
                Integer uniquePlayerID = this.generateUniquePlayerID();
                messages.add(new Message(MessageFragment.AUTH_ID.getFragment(), uniquePlayerID.toString(),uniqueMsgID));
                player.getMessageHandler().write(messages);
                messages.clear();
                player.getMessageHandler().writeOutAndWait();
                player.getMessageHandler().assertOnEquals(uniquePlayerID.toString(), MessageFragment.AUTH_ID.getFragment());
                String name = player.getMessageHandler().getMessagePayloadFromStream(MessageFragment.PLAYER_NAME.getFragment());
                player.createGamer(name,uniquePlayerID);
                String gameType = player.getMessageHandler().getMessagePayloadFromStream(MessageFragment.GAME_TYPE.getFragment());
                String lobbySize = player.getMessageHandler().getMessagePayloadFromStream(MessageFragment.LOBBY_SIZE.getFragment());
                insertPlayerIntoLobby(gameType,lobbySize,player);
            } catch (ClientDisconnectedException | MalformedMessageException | FlowErrorException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    /**
     * Method used to generate a unique player ID, which allows for duplicated nicknames on same lobbies
     * @return an integer which represent the ID
     */
    private Integer generateUniquePlayerID(){
        Random random = new Random();
        int number = random.nextInt((int) Math.pow(2,29),(int) Math.pow(2,30));
        return number;
    }

    /**
     * Method used to insert a player in a lobby given its own preference
     * @param gameType is the type of the game the player want to play
     * @param lobbySize is the number of the player the match will be
     * @param player is the player that is going to be inserted into a lobby
     * @throws MalformedMessageException in case of error communicating with client
     */
    private void insertPlayerIntoLobby(String gameType,String lobbySize, Player player) throws MalformedMessageException {
        GameType type;
        int numOfPlayers;
        try{
            numOfPlayers = Integer.parseInt(lobbySize);
        }catch (NumberFormatException e){
            throw new MalformedMessageException("payload lobbySize not an integer");
        }
        if(gameType.equals(GameType.NORMAL.getName())){
            type = GameType.NORMAL;
        }else if(gameType.equals(GameType.EXPERT.getName())){
            type = GameType.EXPERT;
        }else{
            throw new MalformedMessageException("payload GameType not in a valid format");
        }
        synchronized (this.lobbies){
            if(this.lobbies.isEmpty()){
                Lobby lobby = new Lobby(type,numOfPlayers,player);
                lobbies.add(lobby);
            }else{
                boolean lobbyAlreadyChosen = false;
                for(Lobby lobby : this.lobbies){
                    if(lobby.getType().equals(type) && !lobby.isLobbyReady() && !lobbyAlreadyChosen){
                        lobbyAlreadyChosen = true;
                        lobby.addPlayer(player);
                    }
                }
                if(!lobbyAlreadyChosen){
                    Lobby lobby = new Lobby(type,numOfPlayers,player);
                    this.lobbies.add(lobby);
                }
            }
        }
        ArrayList<Message> messages = new ArrayList<Message>();
        int uniqueMsgID = player.getMessageHandler().getNewUniqueTopicID();
        messages.add(new Message(MessageFragment.GREETINGS.getFragment(), MessageFragment.GREETINGS_STATUS_SUCCESFULL.getFragment(),uniqueMsgID));
        try{
            player.getMessageHandler().write(messages);
            player.getMessageHandler().writeOutAndWait();
            player.getMessageHandler().assertOnEquals(MessageFragment.OK.getFragment(), MessageFragment.GREETINGS.getFragment());
        }catch (ClientDisconnectedException | MalformedMessageException | FlowErrorException e){
            System.out.println(AnsiColor.RED.toString());
            e.printStackTrace();
            System.out.println(AnsiColor.RESET.toString());
            synchronized (this.lobbies){
                for(Lobby lobby : this.lobbies){
                    if(lobby.contains(player)){
                        lobby.removePlayer(player);
                    }
                }
            }
            throw new MalformedMessageException();
        }
    }

    /**
     * Getter method
     * @return an ArrayList of lobbies that are still in queue
     */
    public ArrayList<Lobby> getLobbies(){
        synchronized (this.lobbies){
            return this.lobbies;
        }
    }


}
