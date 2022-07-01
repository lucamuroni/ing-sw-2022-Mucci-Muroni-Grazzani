package it.polimi.ingsw.controller.networking;

import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * Class used to manage connections between hosts
 */
class ConnectionHandler {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final ArrayList<String> inputMessages;
    private final ArrayList<String> outputMessages;
    private boolean isON;
    private boolean hasConnectionBeenLost;
    private MessageTimer timer;

    /**
     * Class builder
     * @param socket is the socket of the host you want to connect to
     */
    public ConnectionHandler(Socket socket){
        this.clientSocket = socket;
        this.isON = true;
        this.inputMessages = new ArrayList<>();
        this.outputMessages = new ArrayList<>();
        this.hasConnectionBeenLost = false;
    }

    /**
     * Method used to start the connections between hosts
     * @throws IOException if the local machine cannot open a channel to comunicate in either directions
     */
    public void start() throws IOException{
        this.out = new PrintWriter(this.clientSocket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.readInputMessage();
        this.writeOutputMessage();
        this.ping(3000);
        this.timer = new MessageTimer(23000);
    }

    /**
     * Method used to safely shut down communications threads
     */
    public void shutDown(){
        this.isON = false;
        timer.kill();
    }

    /**
     * Method responsible for reading incoming messages from socket. It starts a thread that can autodetect ping requests and reply to them without touching incoming messages.
     * Messages are managed with a FIFO politics
     */
    private void readInputMessage(){
        Thread t = new Thread(()->{
            String s;
            while(isON){
                try {
                    this.clientSocket.setSoTimeout(20000);
                    s = this.in.readLine();
                    if(s != null){
                        if(s.equals("ping-ok")){
                            this.ping(8000);
                            timer.kill();
                            timer = new MessageTimer(28000);
                        }else if(s.equals("ping")){
                            synchronized (this.out){
                                this.out.println("ping-ok");
                            }
                        }else{
                            synchronized (this.inputMessages){
                                this.inputMessages.add(s);
                            }
                        }
                    }
                } catch (IOException e) {
                    this.setDisconnection();
                    this.shutDown();
                }
                synchronized (this){
                    try {
                        this.wait(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        t.start();
    }

    /**
     * Method used to get a message from other clients
     * @return the first messages that has already been read or the first message that this client receives in the timing window
     * @throws ClientDisconnectedException if a disconnection is revealed
     */
    public String getInputMessage() throws ClientDisconnectedException {
        String result;
        while(true){
            synchronized (this.inputMessages){
                if(!this.inputMessages.isEmpty()){
                    result = this.inputMessages.get(0);
                    this.inputMessages.remove(0);
                    return result;
                }
                try {
                    this.inputMessages.wait(100);
                } catch (InterruptedException e) {
                }
            }
            if(getDisconnection() || timer.isTimeEnded()){
                throw new ClientDisconnectedException("Found disconnection");
            }
        }
    }

    /**
     * Method used to send a message to another client
     * @param string is the string you want to send
     */
    public void setOutputMessage(String string){
        synchronized (this.outputMessages){
            this.outputMessages.add(string);
        }
    }

    /**
     *  Method responsible for sending to the socket. It starts a thread that waits for a new message to be sent.
     *  If no message is present it will sleep for 50 ns.
     */
    private void writeOutputMessage(){
        Thread t = new Thread(()->{
            boolean empty = true;
            while (this.isON){
                while(empty) {
                    synchronized (this.outputMessages){
                        if(!this.outputMessages.isEmpty()){
                            empty = false;
                        }
                    }
                    try {
                        synchronized (this.outputMessages){
                            this.outputMessages.wait(100);
                        }
                    } catch (InterruptedException e) {
                    }
                }
                synchronized (this.outputMessages){
                    while(!this.outputMessages.isEmpty()){
                        synchronized (this.out){
                            this.out.println(this.outputMessages.get(0));
                            this.outputMessages.remove(0);
                        }
                    }
                }
            }
        });
        t.start();
    }

    /**
     * Method used to send ping request to other clients
     * @param milliSeconds are the seconds to wait before sending the ping request
     */
    private void ping(int milliSeconds){
        Thread t = new Thread(()->{
            synchronized (this.out){
                try{
                    if(milliSeconds>0){
                        this.out.wait(milliSeconds);
                    }
                }catch (InterruptedException e){
                    System.out.println("Could not wait for time before sending ping");
                }finally {
                    this.out.println("ping");
                }
            }
        });
        t.start();
    }

    /**
     * Method used to reset all piped messages already present.
     * This method was designed to try to recover possible errors
     */
    public void restLines(){
        synchronized (this.outputMessages){
            this.outputMessages.clear();
        }
        synchronized (this.inputMessages){
            try {
                this.inputMessages.wait(1000);
            } catch (InterruptedException e) {
                System.out.println("Could not wait for time before cleaning up cache");
            }
            finally {
                this.inputMessages.clear();
            }
        }
    }

    /**
     * Getter method
     * @return a boolean which represent if the connection has been lost due to a Socket TimeOut
     */
    private synchronized boolean getDisconnection(){
        return this.hasConnectionBeenLost;
    }

    /**
     * Setter method launched when the connection is lost due to a Socket TimeOut
     */
    private synchronized void setDisconnection(){
        this.hasConnectionBeenLost = true;
    }
}