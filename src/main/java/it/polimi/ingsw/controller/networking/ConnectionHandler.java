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
class ConnectionHandler {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<String> inputMessages;
    private ArrayList<String> outputMessages;
    private boolean isON;
    private boolean hasConnectionBeenLost;

    public ConnectionHandler(Socket socket){
        this.clientSocket = socket;
        this.isON = true;
        this.inputMessages = new ArrayList<String>();
        this.outputMessages = new ArrayList<String>();
        this.hasConnectionBeenLost = false;
    }

    public void start() throws IOException{
        this.out = new PrintWriter(this.clientSocket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
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
                    }else if(s.equals("ping")){
                        synchronized (this.out){
                            this.out.println("ping-ok");
                        }
                    }else{
                        synchronized (this.inputMessages){
                            this.inputMessages.add(s);
                        }
                    }
                } catch (IOException e) {
                    hasConnectionBeenLost = true;
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

    public void setOutputMessage(String string){
        synchronized (this.outputMessages){
            this.outputMessages.add(string);
            this.outputMessages.notifyAll();
        }
    }


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
