package it.polimi.ingsw.controller.networking;

/**
 * @author Davide Grazzani
 * Enum used to define standard messages headers and payloads
 */
public enum MessageFragment {
    AUTH("auth"),
    AUTH_ID("auth_id"),
    GAME_TYPE("GameType"),
    GREETINGS("greetings"),
    GREETINGS_STATUS_SUCCESFULL("successful"),
    LOBBY_TYPE("LobbyType"),
    LOBBY_SIZE("LobbySize"),
    OK("ok"),
    PLAYER_NAME("PlayerName"),
    ASSISTANT_CARD("AssistantCard"),
    MN_LOCATION("MNLocation");
    private String fragment;

    /**
     * Enum Builder
     * @param fragment represent the standard header or payload
     */
    private MessageFragment(String fragment){
        this.fragment = fragment;
    }

    /**
     * Getter method
     * @return the header or payload associated with the enum's keyword
     */
    public String getFragment() {
        return fragment;
    }
}
