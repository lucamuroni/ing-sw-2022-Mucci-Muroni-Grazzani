package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

public class Start implements GamePhase {
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public Start(ClientController controller) {
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        this.view.setController(controller);
        this.getUsernames();
        this.updateMNPlace();
        for(int i = 0; i< 12;i++){
            this.updateIslandStatus();
        }
        if(this.game.getGameType().equals(GameType.EXPERT.getName())){
            //TODO metodo privato per ricevere le monete
            this.getCharacterCards();
            this.getCoins();
        }
        for (int i = 0; i<this.game.getGamers().size(); i++) {
            this.updateColor();
            this.updateDashboards();
        }
    }

    private void getCharacterCards() {
        for (int i = 0; i<3; i++) {
            try {
                try {
                    this.network.getCharacterCard(game);
                } catch (MalformedMessageException e) {
                    this.network.getCharacterCard(game);
                }
            } catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError();
            } catch (AssetErrorException e) {
                this.controller.handleError("Doesn't found character card");
            }
        }
    }

    private void getCoins() {
        try {
            try {
                this.network.getCoins(game);
            } catch (MalformedMessageException e) {
                this.network.getCoins(game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
    }

    private void getUsernames() {
        for(int i = 0 ; i< this.game.getLobbySize()-1;i++) {
            try{
                try {
                    this.network.getUsernames(this.game);
                } catch (MalformedMessageException e) {
                    this.network.getUsernames(this.game);
                }
            }catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError("Could not receive info about other players");
            }
        }
    }

    private void updateMNPlace() {
        try {
            try {
                this.network.getMotherNaturePlace(this.game);
            } catch (MalformedMessageException e) {
                this.network.getMotherNaturePlace(this.game);
            }
        } catch (MalformedMessageException  | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found island of mother nature");
        }
    }

    private void updateIslandStatus() {
        try {
            try {
                this.network.getIslandStatus(this.game);
            } catch (MalformedMessageException e) {
                this.network.getIslandStatus(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found island");
        }
    }

    private void updateDashboards() {
        try {
            try {
                this.network.getDashboard(this.game);
            } catch (MalformedMessageException e) {
                this.network.getDashboard(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found dashboard");
        }
    }

    private void updateColor() {
        try {
            try {
                this.network.getTowerColor(this.game);
            } catch (MalformedMessageException e) {
                this.network.getTowerColor(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found tower color/gamer");
        }
    }

    @Override
    public GamePhase next() {
        Idle phase = new Idle(this.controller);
        phase.isGameStarted(false);
        return phase;
    }

}
