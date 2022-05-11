package it.polimi.ingsw.controller.networking;

/**
 * @author Davide Grazzani
 * Enum used to define standard messages headers and payloads
 */
public enum MessageFragment {
    ASSISTANT_CARD("assistantCard"),
    ASSISTANT_CARD_DECK("assistantCardDeck"),
    AUTH("auth"),
    AUTH_ID("auth_id"),
    CLOUD("cloud"),
    CLOUD_ID("cloud_id"),
    DASHBOARD("dashboard"),
    GAME_TYPE("GameType"),
    GREETINGS("greetings"),
    GREETINGS_STATUS_SUCCESFULL("successful"),
    HALL_STUDENT("hallStudent"),
    ISLAND("island"),
    ISLAND_ID("island_id"),
    LOBBY_TYPE("LobbyType"),
    LOBBY_SIZE("LobbySize"),
    MN_LOCATION("MNLocation"),
    NUM_TOWERS("numTowers"),
    OK("ok"),
    OWNER("Owner"),
    PHASE ("phase"),
    PLAYER_NAME("PlayerName"),
    STUDENT_COLOR("studentColor"),
    STUDENT_LOCATION("studentLocation"),
    TOWER_COLOR("towerColor"),
    WAITING_STUDENT("waitingStudent");

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
