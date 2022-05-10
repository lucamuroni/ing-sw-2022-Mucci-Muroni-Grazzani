package it.polimi.ingsw.controller.networking;


import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
// TODO: update method to replay to ping from client
class ConnectionHandler {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<String> inputMessages;
    private ArrayList<String> outputMessages;
    private boolean isON;
    private boolean isSocketTimeOutOccurred;
    private final Object inputMessagesLock = new Object();
    private final Object outputMessagesLock = new Object();
    private final Object outLock = new Object();

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
        this.writeOutputMessage();
        this.ping(3000);
    }

    public void shutDown(){
        this.isON = false;
    }

    private void readInputMessage(){
        Thread t = new Thread(()->{
            String s;
            while(isON){
                try {
                    this.clientSocket.setSoTimeout(20000);
                    s = this.in.readLine();
                    if(s.equals("ping-ok")){
                        this.ping(8000);
                    }else{
                        synchronized (this.inputMessagesLock){
                            this.inputMessages.add(s);
                        }
                    }
                } catch (SocketException e) {
                    isSocketTimeOutOccurred = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


    public String getInputMessage(int actionTimeOutMs) throws TimeHasEndedException, ClientDisconnectedException {
        boolean isInputEmpty = true;
        MessageTimer msgTimer = new MessageTimer(actionTimeOutMs);
        msgTimer.start();
        while(isInputEmpty){
            synchronized (this.inputMessagesLock){
                if(!this.inputMessages.isEmpty()){
                    isInputEmpty = false;
                }
            }
            if(isSocketTimeOutOccurred){
                throw new ClientDisconnectedException("Found disconnection");
            }
            if(msgTimer.isTimeEnded()){
                throw new TimeHasEndedException("Time to respond ended");
            }
        }
        msgTimer.kill();
        String s;
        synchronized (this.inputMessagesLock){
            s = this.inputMessages.get(0);
            this.inputMessages.remove(0);
        }
        return s;
    }

    public void setOutputMessage(String string){
        synchronized (this.outputMessagesLock){
            this.outputMessages.add(string);
            this.outputMessages.notifyAll();
        }
    }


    private void writeOutputMessage(){
        Thread t = new Thread(()->{
            boolean empty = true;
            while (this.isON){
                while(empty) {
                    synchronized (this.outputMessagesLock){
                        if(!this.outputMessages.isEmpty()){
                            empty = false;
                        }
                    }
                    try {
                        this.outputMessages.wait(10000);
                    } catch (InterruptedException e) {
                        System.out.println("Could not wait for new output message");
                    }
                }
                synchronized (this.outputMessagesLock){
                    while(!this.outputMessages.isEmpty()){
                        synchronized (this.outLock){
                            this.out.println(this.outputMessages.get(0));
                            this.outputMessages.remove(0);
                        }
                    }
                }
            }
        });
        t.start();
    }


    private void ping(int milliSeconds){
        Thread t = new Thread(()->{
            synchronized (this.outLock){
                if(milliSeconds>0){
                    try {
                        this.out.wait(milliSeconds);
                    } catch (InterruptedException e) {
                        System.out.println("Could not wait for time before sending ping");
                    }
                }
                this.out.println("ping");
            }
        });
        t.start();
    }

    
    public void restLines(){
        synchronized (this.outputMessagesLock){
            this.outputMessages.clear();
        }
        synchronized (this.inputMessagesLock){
            try {
                this.inputMessages.wait(1000);
            } catch (InterruptedException e) {}
            finally {
                this.inputMessages.clear();
            }
        }
    }
}
