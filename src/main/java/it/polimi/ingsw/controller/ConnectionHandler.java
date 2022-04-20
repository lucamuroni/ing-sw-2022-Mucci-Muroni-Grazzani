package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.server.ClientConnectionAccepter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread{
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String inputMessage;
    private String outputMessage;
    private boolean isON;

    public ConnectionHandler(Socket socket){
        this.clientSocket = socket;
        this.isON = true;
    }

    @Override
    public void run() {
        try{
            this.out = new PrintWriter(this.clientSocket.getOutputStream(),true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        }catch (IOException e){
            System.out.println("Error while opening connection between server and client");
            e.printStackTrace();
        }
        while(this.isON){
            // TODO : verifica che la funzione posso funzionare così com'è e non  con 2 thred per gestire input e output separato
            try {
                readInputMessage();
            } catch (IOException e) {
                System.out.println("Cannot get message from client");
                e.printStackTrace();
            }
            writeOutputMessage();
        }
    }

    public void shutDown(){
        this.isON = false;
    }

    private void readInputMessage() throws IOException{
        synchronized (this.inputMessage){
            this.inputMessage =  this.in.readLine();
            this.inputMessage.notifyAll();
        }
    }

    private void writeOutputMessage(){
        synchronized (this.outputMessage){
            if(this.outputMessage != ""){
                this.out.println(this.outputMessage);
                this.outputMessage = "";
            }
        }
    }

    public String getInputMessage(){
        String cp;
        synchronized (this.inputMessage){
            cp = new String(this.inputMessage);
        }
        return cp;
    }

    public void setOutputMessage(String string){
        synchronized (this.outputMessage){
            this.outputMessage = string;
        }
    }
}
