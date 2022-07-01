package it.polimi.ingsw.view.cli.page;

import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.Page;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Menù;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * This class is used to ask a player which srudents he wants to move due to the effect of a charactercard (bard)
 */
public class SelectColorsPage implements Page {
    private boolean killed;
    private boolean readyToProceed = false;
    private final Cli cli;
    private final Game game;

    /**
     * Constructor of the class
     * @param cli is the handler used to interact with the player
     * @param game is the current game
     */
    public SelectColorsPage(Cli cli, Game game) {
        this.cli = cli;
        this.game = game;
        this.killed = false;
    }

    /**
     * This is the main method that manages the page
     * @throws UndoException when the player wants to redo his choice
     */
    @Override
    public void handle() throws UndoException {
        ArrayList<PawnColor> colors = new ArrayList<>();
        int cont = 1;
        while (cont <= 2) {
            this.cli.drawDashboard();
            ArrayList<String> options = new ArrayList<>();
            ArrayList<PawnColor> hallColors = new ArrayList<>(this.game.getHallColors());
            for (PawnColor color : hallColors) {
                options.add(color.name());
            }
            Menù menù = new Menù(options);
            menù.setContext("Choose a color to remove from your hall: ");
            int choice = this.cli.readInt(options.size(), menù, false);
            PawnColor hallColor = hallColors.get(choice-1);
            this.cli.drawDashboard();
            options.clear();
            ArrayList<PawnColor> waitingColors = new ArrayList<>(this.game.getWaitingColors(hallColor));
            for (PawnColor color : waitingColors) {
                options.add(color.name());
            }
            options.add("Back");
            menù.clear();
            menù.addOptions(options);
            menù.setContext("Choose a color to remove from your waiting room: ");
            choice = this.cli.readInt(options.size(), menù, true);
            options.clear();
            options.add("y");
            options.add("n");
            String input = this.cli.readString("Are you satisfied with your selections (y/n): ",options,true);
            if(input.equals("n")){
                throw new UndoException();
            }
            PawnColor waitingColor = waitingColors.get(choice-1);
            colors.add(waitingColor);
            colors.add(hallColor);
            this.game.getSelf().getDashBoard().getHall().remove(this.game.getSelf().getDashBoard().getHall().stream().filter(x -> x.getColor().equals(hallColor)).findFirst().get());
            this.game.getSelf().getDashBoard().getWaitingRoom().remove(this.game.getSelf().getDashBoard().getWaitingRoom().stream().filter(x -> x.getColor().equals(waitingColor)).findFirst().get());
            this.game.getSelf().getDashBoard().getHall().add(new Student(waitingColor));
            this.game.getSelf().getDashBoard().getWaitingRoom().add(new Student(hallColor));
            if (cont+1 == 2 && (long) this.game.getSelf().getDashBoard().getHall().size() > 0) {
                options.clear();
                options.add("y");
                options.add("n");
                input = this.cli.readString("Do you want to move another couple of students (y/n): ",options,true);
                if (input.equals("n"))
                    break;
                else
                    cont += 1;
            } else {
                break;
            }
        }
        System.out.println("Colori che ho selezionato, size = "+colors.size());
        colors.stream().forEach(x->System.out.println(x.toString()));
        game.getSelf().setSelectedColors(colors);
        this.setReadyToProcede();
    }

    /**
     * Method that checks if the process is ready
     * @return true if the process is ready, false otherwise
     */
    @Override
    public boolean isReadyToProceed() {
        if(readyToProceed){
            readyToProceed = false;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method used to set that the page has completed its task
     */
    private synchronized void setReadyToProcede(){
        this.readyToProceed = true;
    }

    /**
     * Method used to terminate the page in case of threading
     */
    @Override
    public synchronized void kill() {
        this.killed = true;
    }

    /**
     * Method that checks if the page has been killed
     * @return true if killed, false otherwise
     */
    private synchronized boolean isKilled(){
        return this.killed;
    }
}