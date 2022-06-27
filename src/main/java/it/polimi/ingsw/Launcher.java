package it.polimi.ingsw;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.server.Server;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;
import java.net.Socket;

public class Launcher {
    private final static int portNumber = 17946;
    private final static String ip = "localhost";

    public void standardLaunch(String args[]){
        {
            int standardServerPort = 17946;
            if(args.length <2){
                System.out.println("Bad formatting : try launch --help for help");
                System.exit(0);
            }
            if(args[1].equals("--help")){
                System.out.println("Usage :");
                System.out.println("-s       start a server");
                System.out.println("-p       specify port for server                [OPTIONAL]");
                System.out.println("-c       for starting client in default mode");
                System.out.println("--cli    for starting client in cli mode        [OPTIONAL]");
                System.out.println("--gui    for starting client in gui mode        [OPTIONAL,DEFAULT]");
                System.out.println("Examples :");
                System.out.println("Start a cli -c --cli");
                System.out.println("Start a server -s");
                System.exit(0);
            }else if(args[1].equals("-s")){
                int port = standardServerPort;
                for(int i = 2;i<args.length;i++){
                    if(args[i].equals("-p")){
                        try {
                            port = Integer.parseInt(args[i+1]);
                        }catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("Bad formatting : try launch --help for help");
                            System.exit(0);
                        }
                    }
                }
                if(port==standardServerPort){
                    Server server = new Server(standardServerPort);
                }
            }else if(args[1].equals("-c")){
                System.out.println("starto il client");
            }
        }
    }

    public static void main(String[] args){
        boolean debug = true;
        if(debug){
            if(args[0].equals("-s")){
                Server s = new Server(17894);
            }else{
                MessageHandler messageHandler = null;
                try {
                    messageHandler = new MessageHandler(new Socket("localhost",17894));
                } catch (IOException e) {
                    System.out.println("could not initiate client");
                }
                ViewHandler viewHandler = new Cli();
                ClientController c = new ClientController(messageHandler,viewHandler);

            }
        }else{
            Launcher launcher = new Launcher();
            launcher.standardLaunch(args);
        }
    }

    private void startServer(String[] args){
        if(args.length == 0){
            Server s = new Server(portNumber);
        }else{
            if(args[0].equals("-p")){
                int port = portNumber;
                try{
                    port = Integer.parseInt(args[1]);
                }catch (NumberFormatException e){
                    printError("Expected integer value as Server port");
                }
                Server s = new Server(port);
            }else{
                printError("Unknown flag for server");
            }
        }
    }

    private void startClient(String[] args){
        MessageHandler messageHandler = null;
        if(args.length == 0){
            try {
                messageHandler = new MessageHandler(new Socket(ip,portNumber));
            } catch (IOException e) {
                printError("Could not open Socket on "+portNumber+"; ip :"+ip);
            }
            //TODO rimpiazzare cli con gui
            ViewHandler viewHandler = new Cli();
            ClientController c = new ClientController(messageHandler,viewHandler);
        }else{
            int port = portNumber;
            String ip = Launcher.ip;
            ViewHandler viewHandler = null;
            if(args[0].equals("--gui")){
                //todo crea una nuova gui
                viewHandler = new Cli();
            } else if (args[0].equals("--cli")) {
                viewHandler = new Cli();
            }else{
                printError("Unspecified user interface"+"\n"+"Specify it whit --gui or --cli");
            }
            for(int i = 1;i <args.length;i = i+2){
                if ("-p".equals(args[i])) {
                    try {
                        port = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        printError("invalid port specified");
                    }
                } else if (args[i].equals("-ip")) {
                    ip = args[i+1];
                }
            }
            //todo creare il message handler
            //todo creare il clientcontroller
        }
    }

    private void printError(String s){
        System.out.println(AnsiColor.RED.toString()+s+AnsiColor.RED.toString());
        System.out.println(AnsiColor.YELLOW.toString()+"Bad formatting : try launch --help for help"+AnsiColor.RESET.toString());
        System.exit(1);
    }
}
