package it.polimi.ingsw;

import it.polimi.ingsw.controller.server.Server;

public class Launcher {
    public static void main(String args[]){
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
