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
    HALL_PAWN_RED("red"),
    HALL_PAWN_BLUE("blue"),
    HALL_PAWN_YELLOW("yellow"),
    HALL_PAWN_GREEN("green"),
    HALL_PAWN_PINK("pink"),
    ISLAND("island"),
    ISLAND_ID("island_id"),
    LOBBY_TYPE("LobbyType"),
    LOBBY_SIZE("LobbySize"),
    MN_LOCATION("MNLocation"),
    NUM_TOWERS("numTowers"),
    OK("ok"),
    OWNER("Owner"),
    PAWN_RED("red"),
    PAWN_BLUE("blue"),
    PAWN_YELLOW("yellow"),
    PAWN_GREEN("green"),
    PAWN_PINK("pink"),
    PHASE ("phase"),
    PLAYER_NAME("PlayerName"),
    STOP("Stop"),
    STUDENT_COLOR("studentColor"),
    STUDENT_LOCATION("studentLocation"),
    TOWER_COLOR("towerColor"),
    WAITING_STUDENT("waitingStudent"),
    WAITING_PAWN_RED("red"),
    WAITING_PAWN_BLUE("blue"),
    WAITING_PAWN_YELLOW("yellow"),
    WAITING_PAWN_GREEN("green"),
    WAITING_PAWN_PINK("pink"),
    WINNER("winner");

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