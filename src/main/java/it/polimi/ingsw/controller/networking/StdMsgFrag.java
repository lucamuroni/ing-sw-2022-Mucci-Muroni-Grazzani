package it.polimi.ingsw.controller.networking;

/**
 * @author Davide Grazzani
 * Enum used to define standard messages headers and payloads
 */
public enum StdMsgFrag {
    AUTH("auth"),
    AUTH_ID("auth_id"),
    GAME_TYPE("GameType"),
    GREETINGS("greetings"),
    GREETINGS_STATUS_SUCCESFULL("successful"),
    LOBBY_TYPE("LobbyType"),
    LOBBY_SIZE("LobbySize"),
    OK("ok"),
    PLAYER_NAME("PlayerName");

    private String header;

    /**
     * Enum Builder
     * @param header represent the standard header or payload
     */
    private StdMsgFrag(String header){
        this.header = header;
    }

    /**
     * Getter method
     * @return the header or payload associated with the enum's keyword
     */
    public String getHeader() {
        return header;
    }
}
