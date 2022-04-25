package it.polimi.ingsw.controller.networking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

class ConnectionHandler {
    private final Socket clientSocket;
    private final static int timeToRespond = 120; //time in seconds
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<String> inputMessages;
    private ArrayList<String> outputMessages;
    private boolean isON;
    private boolean isSocketTimeOutOccurred;

    public ConnectionHandler(Socket socket){
        this.clientSocket = socket;
        this.isON = true;
        this.inputMessages = new ArrayList<String>();
        this.outputMessages = new ArrayList<String>();
        this.isSocketTimeOutOccurred = false;
    }

    public void start() {
        try{
            this.out = new PrintWriter(this.clientSocket.getOutputStream(),true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        }catch (IOException e){
            System.out.println("Error while opening connection between server and client");
            e.printStackTrace();
        }
        this.readInputMessage();
        this.writeOutputMessages();
    }

    public void shutDown(){
        this.isON = false;
    }

    private void readInputMessage(){
        Thread t = new Thread(()->{
            String s;
            while(isON){
                try {
                    this.clientSocket.setSoTimeout(1500);
                    s = this.in.readLine();
                    if(s.equals("ping-ok")){
                        this.ping();
                    }else{
                        this.inputMessages.add(s);
                    }
                } catch (SocketException e) {
                    isSocketTimeOutOccurred = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public String getInputMessage(int actionTimeOut) throws TimeHasEndedException, ClientDisconnectedException{
        MessageTimer msgTimer = new MessageTimer(actionTimeOut);
        msgTimer.start();
        while(this.inputMessages.isEmpty()){
            if(isSocketTimeOutOccurred){
                throw new ClientDisconnectedException("Found disconnection");
            }
            if(msgTimer.isTimeEnded()){
                throw new TimeHasEndedException("Time to respond ended");
            }
        }
        msgTimer.kill();
        String s = this.inputMessages.get(0);
        this.inputMessages.remove(0);
        return s;
    }

    public void setOutputMessage(String string){
        synchronized (this.outputMessages){
            this.outputMessages.add(string);
            this.outputMessages.notifyAll();
        }
    }


    private void writeOutputMessages(){
        Thread t = new Thread(()->{
            while (this.isON){
                while(this.outputMessages.isEmpty()) {
                    try {
                        this.outputMessages.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Could not wait for new output message");
                        e.printStackTrace();
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
    }

    private void ping(){
        synchronized (this.out){
            this.out.println("ping");
        }
    }

}
