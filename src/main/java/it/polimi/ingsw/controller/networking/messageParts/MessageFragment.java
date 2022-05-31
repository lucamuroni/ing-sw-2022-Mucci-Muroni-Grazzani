package it.polimi.ingsw.controller.networking.messageParts;

/**
 * @author Davide Grazzani
 * Enum used to define standard messages' headers and payloads
 */
public enum MessageFragment {
    ASSISTANT_CARD("assistantCard"),
    ASSISTANT_CARD_DECK("assistantCardDeck"),
    AUTH("auth"),
    AUTH_ID("auth_id"),
    CLOUD("cloud"),
    CLOUD_ID("cloud_id"),
    CONTEXT("context"),
    CONTEXT_CARD("cardSelection"),
    CONTEXT_CLOUD("fillCloud"),
    CONTEXT_COLOR("towerColor"),
    CONTEXT_DASHBOARD("dashboardUpdate"),
    CONTEXT_FIGURE("figureSelection"),
    CONTEXT_ISLAND("islandUpdate"),
    CONTEXT_MOTHER("motherNature"),
    CONTEXT_PHASE("changePhase"),
    CONTEXT_USERNAME("activeUsername"),
    DASHBOARD("dashboard"),
    GAME_TYPE("GameType"),
    GAME_NORMAL("normal game"),
    GAME_EXPERT("expert game"),
    GREETINGS("greetings"),
    GREETINGS_STATUS_SUCCESFULL("successful"),
    HALL_PAWN_RED("red"),
    HALL_PAWN_BLUE("blue"),
    HALL_PAWN_YELLOW("yellow"),
    HALL_PAWN_GREEN("green"),
    HALL_PAWN_PINK("pink"),
    ISLAND("island"),
    ISLAND_ID("island_id"),
    LOBBY("lobby"),
    LOBBY_CREATE("create lobby"),
    LOBBY_JOIN("join lobby"),
    LOBBY_SIZE("LobbySize"),
    MN_LOCATION("MNLocation"),
    NUM_TOWERS("numTowers"),
    OK("ok"),
    OWNER("Owner"),
    PAYLOAD_SIZE("payload dimension"),
    PAWN_RED("red"),
    PAWN_BLUE("blue"),
    PAWN_YELLOW("yellow"),
    PAWN_GREEN("green"),
    PAWN_PINK("pink"),
    PHASE ("phase"),
    PLAYER_NAME("PlayerName"),
    STUDENT_COLOR("studentColor"),
    STUDENT_LOCATION("studentLocation"),
    TOWER_COLOR("towerColor"),
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

    public static MessageFragment getEnum(String string) {
        MessageFragment frag = null;
        for (MessageFragment fragment : MessageFragment.values()) {
            if (fragment.getFragment().equals(string))
                frag = fragment;
        }
        return frag;
    }
}