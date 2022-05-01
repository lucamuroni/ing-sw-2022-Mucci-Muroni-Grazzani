package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.networking.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;

public class ClientReception extends Thread{
    private final ServerSocket serverSocket;
    private ArrayList<Lobby> lobbies;
    private boolean isON;

    public ClientReception(ServerSocket socket){
        this.serverSocket = socket;
        this.lobbies = new ArrayList<Lobby>();
        this.isON = true;
    }

    @Override
    public void run() {
        MessageHandler messageHandler;
        while(this.isON){
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
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close Server Socket");
            e.printStackTrace();
        }
    }

    private void playerHandShake(Player player){
        Thread t = new Thread(() -> {
            int uniqueMsgID = player.getMessageHandler().getNewUniqueTopicID();
            ArrayList<Message> msgs = new ArrayList<Message>();

            Message m0 = new Message(StdMsgFrag.GREETINGS.getHeader(), "",uniqueMsgID);
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
                msgs.addAll(player.getMessageHandler().writeOutAndWait(ConnectionTimings.CONNECTION_STARTUP.getTiming()));
                player.getMessageHandler().assertOnEquals(StdMsgFrag.OK.getHeader(), StdMsgFrag.GREETINGS.getHeader(), msgs);
                msgs.clear();
                Integer uniquePlayerID = this.generateUniquePlayerID();
                msgs.add(new Message(StdMsgFrag.AUTH_ID.getHeader(), uniquePlayerID.toString(),uniqueMsgID));
                player.getMessageHandler().write(msgs);
                msgs.clear();
                msgs.addAll(player.getMessageHandler().writeOutAndWait(ConnectionTimings.INFINTE.getTiming()));
                player.getMessageHandler().assertOnEquals(uniquePlayerID.toString(), StdMsgFrag.AUTH_ID.getHeader(), msgs);
                String name = player.getMessageHandler().getMessagePayloadFromStream(StdMsgFrag.PLAYER_NAME.getHeader(),msgs);
                player.createGamer(name,uniquePlayerID);
                String gameType = player.getMessageHandler().getMessagePayloadFromStream(StdMsgFrag.GAME_TYPE.getHeader(),msgs);
                String lobbySize = player.getMessageHandler().getMessagePayloadFromStream(StdMsgFrag.LOBBY_SIZE.getHeader(),msgs);
                insertPlayerIntoLobby(gameType,lobbySize,player);
            } catch (TimeHasEndedException e) {
                e.printStackTrace();
            } catch (ClientDisconnectedException e) {
                e.printStackTrace();
            } catch (MalformedMessageException e) {
                e.printStackTrace();
            }

        });
        t.start();
    }

    private Integer generateUniquePlayerID(){
        Random random = new Random();
        int number = random.nextInt((int) Math.pow(2,29),(int) Math.pow(2,30));
        return number;
    }

    private void insertPlayerIntoLobby(String gameType,String lobbySize, Player player) throws MalformedMessageException {
        GameType type;
        Integer numOfPlayers;
        try{
            numOfPlayers = Integer.valueOf(lobbySize);
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
                for()
            }
        }
    }
}
