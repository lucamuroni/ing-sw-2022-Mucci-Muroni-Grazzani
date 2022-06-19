package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * This class implements the second part of the third phase of the game, which is the MotherNaturePhase, and in particular this part
 * handles the conquest of an island
 */
public class ConquerIslandPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final View view;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller link with this game
     */
    public ConquerIslandPhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
    }

    /**
     * This is the main method that handles the ConquerIslandPhase
     */
    @Override
    public void handle() {
        this.game.checkIslandOwner();
        int id = this.game.getIslands().indexOf(this.game.getMotherNature().getPlace());
        if(this.game.getIslands().get(id).getId() != this.game.getIslands().get(this.findFirst()).getId()){
            this.mergeIsland(id,this.findPrevious(id));
        }else{
            this.mergeIsland(id,this.findLast());
        }
        if(this.game.getIslands().get(id).getId() != this.game.getIslands().get(this.findLast()).getId()){
            this.mergeIsland(id,this.findNext(id));
        }else{
            this.mergeIsland(id,this.findFirst());
        }
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        for (Player pl : players) {
            this.view.setCurrentPlayer(pl);
            try {
                try {
                    //System.out.println("qui");
                    this.view.sendContext(CONTEXT_ISLAND.getFragment());
                    this.view.updateIslandStatus(this.game.getMotherNature().getPlace());
                } catch (MalformedMessageException  | FlowErrorException e) {
                    this.view.sendContext(CONTEXT_ISLAND.getFragment());
                    this.view.updateIslandStatus(this.game.getMotherNature().getPlace());
                }
            } catch (MalformedMessageException | ClientDisconnectedException | FlowErrorException e){
                this.controller.handlePlayerError(pl,"Error while updating islands status");
            }
            try {
                try {
                    for(Gamer gamer : this.game.getGamers()){
                        this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                        this.view.updateDashboards(gamer, game);
                    }
                } catch (MalformedMessageException e) {
                    for(Gamer gamer : this.game.getGamers()){
                        this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                        this.view.updateDashboards(gamer, game);
                    }
                }
            } catch (MalformedMessageException | ClientDisconnectedException | FlowErrorException e) {
                this.controller.handlePlayerError(pl,"Error while updating dashboard status");
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        boolean thereIsAWinner = false;
        if(this.game.getIslands().size()<=3){
            thereIsAWinner = true;
        }else{
            for(Gamer gamer :this.game.getGamers()){
                if(gamer.getDashboard().getNumTowers()==0){
                    thereIsAWinner = true;
                }
            }
        }
        if(thereIsAWinner){
            return new VictoryPhase(this.game, this.controller);
        }
        return new ActionPhase3(this.game,this.controller);
    }

    private void mergeIsland(int id1, int id2){
        if(this.game.getIslands().get(id1).getOwner().isPresent() && this.game.getIslands().get(id1).getOwner().equals(this.game.getIslands().get(id2).getOwner())){
            this.game.getIslands().get(id1).mergeIsland(this.game.getIslands().get(id2));
            ArrayList<Island> islands = new ArrayList<Island>();
            islands.add(this.game.getIslands().get(id1));
            islands.add(this.game.getIslands().get(id2));
            for(Player player : this.controller.getPlayers()){
                this.view.setCurrentPlayer(player);
                try {
                    try {
                        this.view.sendContext(CONTEXT_MERGE.getFragment());
                        this.view.sendMergedIslands(islands);
                    }catch (MalformedMessageException | FlowErrorException e){
                        this.view.sendContext(CONTEXT_MERGE.getFragment());
                        this.view.sendMergedIslands(islands);
                    }
                }catch (MalformedMessageException | ClientDisconnectedException | FlowErrorException e){
                    this.controller.handlePlayerError(player,"Could not send merged Islands");
                }
            }
            //TODO controllo
            this.game.getIslands().remove(this.game.getIslands().get(id2));
        }
    }

    private int findPrevious(int idCurrent) {
        int ind = this.game.getIslands().indexOf(this.game.getIslands().get(idCurrent-1));
        return ind;
    }

    private int findNext(int idCurrent) {
        int ind = this.game.getIslands().indexOf(this.game.getIslands().get(idCurrent+1));
        return ind;
    }

    private int findLast() {
        int ind = this.game.getIslands().indexOf(this.game.getIslands().get(this.game.getIslands().size()-1));
        return ind;
    }

    private int findFirst() {
        int ind = this.game.getIslands().indexOf(this.game.getIslands().stream().findFirst().get());
        return ind;
    }
}
