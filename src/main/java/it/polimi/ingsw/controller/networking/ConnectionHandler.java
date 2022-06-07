package it.polimi.ingsw.controller.networking;

import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class used to manage connections between hosts
 * @author Davide Grazzani
 */
class ConnectionHandler {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final ArrayList<String> inputMessages;
    private final ArrayList<String> outputMessages;
    private boolean isON;
    private boolean hasConnectionBeenLost;

    /**
     * Class builder
     * @param socket is the socket of the host you want to connect to
     */
    public ConnectionHandler(Socket socket){
        this.clientSocket = socket;
        this.isON = true;
        this.inputMessages = new ArrayList<String>();
        this.outputMessages = new ArrayList<String>();
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
    }

    /**
     * Method used to safely shut down communications threads
     */
    public void shutDown(){
        this.isON = false;
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
                    hasConnectionBeenLost = true;
                    this.shutDown();
                }
            }
        });
        t.start();
    }

    /**
     * Method used to get a message from other clients
     * @param actionTimeOutMs is the maximum time allowed to read the message
     * @return the first messages that has already been read or the first message that this client receives in the timing window
     * @throws TimeHasEndedException if no messages where founded on this lapse of time
     * @throws ClientDisconnectedException if a disconnection is revealed
     */
    public String getInputMessage(int actionTimeOutMs) throws TimeHasEndedException, ClientDisconnectedException {
        boolean isInputEmpty = true;
        MessageTimer msgTimer = new MessageTimer(actionTimeOutMs);
        msgTimer.start();
        while(isInputEmpty){
            synchronized (this.inputMessages){
                isInputEmpty = this.inputMessages.isEmpty();
            }
            if(hasConnectionBeenLost){
                throw new ClientDisconnectedException("Found disconnection");
            }
            if(msgTimer.isTimeEnded()){
                throw new TimeHasEndedException("Time to respond ended");
            }
        }
        msgTimer.kill();
        String s;
        synchronized (this.inputMessages){
            s = this.inputMessages.get(0);
            this.inputMessages.remove(0);
        }
        return s;
    }

    /**
     * Method used to send a message to another client
     * @param string is the string you want to send
     */
    public void setOutputMessage(String string){
        synchronized (this.outputMessages){
            this.outputMessages.add(string);
            this.outputMessages.notifyAll();
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
                            this.outputMessages.wait(50);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Could not wait for new output message");
                        System.out.println("Restarting Thread ...");
                        this.writeOutputMessage();
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
}
