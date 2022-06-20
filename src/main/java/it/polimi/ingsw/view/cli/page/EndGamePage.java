package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Results;
import it.polimi.ingsw.view.cli.AnsiColor;
import it.polimi.ingsw.view.cli.Cli;

public class EndGamePage implements Page {
    private final Cli cli;
    private boolean killed = false;
    private Results results;

    public EndGamePage(Cli cli, Results results){
        this.cli = cli;
        this.results = results;
    }

    @Override
    public void handle() throws UndoException {
        this.cli.clearConsole();
        if(results == Results.WIN){
            System.out.println(AnsiColor.GREEN.toString() +"\n" +
                    "██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗ \n" +
                    "██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗\n" +
                    "██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝\n" +
                    "██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗\n" +
                    "╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║\n" +
                    " ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝"+"\n"+AnsiColor.RESET.toString());
        }else if(results == Results.TIE){
            System.out.println(AnsiColor.YELLOW.toString()+"\n" +
                    "██████╗ ██████╗  █████╗ ██╗    ██╗\n" +
                    "██╔══██╗██╔══██╗██╔══██╗██║    ██║\n" +
                    "██║  ██║██████╔╝███████║██║ █╗ ██║\n" +
                    "██║  ██║██╔══██╗██╔══██║██║███╗██║\n" +
                    "██████╔╝██║  ██║██║  ██║╚███╔███╔╝\n" +
                    "╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚══╝╚══╝ "+"\n"+AnsiColor.RESET.toString());
        }else{
            System.out.println(AnsiColor.RED.toString()+"\n" +
                    "██╗      ██████╗ ███████╗███████╗██████╗ \n" +
                    "██║     ██╔═══██╗██╔════╝██╔════╝██╔══██╗\n" +
                    "██║     ██║   ██║███████╗█████╗  ██████╔╝\n" +
                    "██║     ██║   ██║╚════██║██╔══╝  ██╔══██╗\n" +
                    "███████╗╚██████╔╝███████║███████╗██║  ██║\n" +
                    "╚══════╝ ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝"+AnsiColor.RESET.toString());
        }
        System.out.println("\n");
        System.out.println("Thank you for playing this game");
        System.exit(0);
    }

    @Override
    public boolean isReadyToProceed() {
        return true;
    }

    @Override
    public void kill() {
        this.killed = true;
    }
}
