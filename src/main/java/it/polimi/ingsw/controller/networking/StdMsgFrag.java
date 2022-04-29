package it.polimi.ingsw.controller.networking;

public enum StdMsgFrag {
    AUTH("auth"),
    AUTH_ID("auth_id"),
    GAME_TYPE("GameType"),
    GREETINGS("greetings"),
    LOBBY_TYPE("LobbyType"),
    LOBBY_SIZE("LobbySize"),
    OK("ok"),
    PLAYER_NAME("PlayerName");

    private String header;

    private StdMsgFrag(String header){
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
