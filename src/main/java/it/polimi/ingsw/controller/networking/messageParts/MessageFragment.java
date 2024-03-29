package it.polimi.ingsw.controller.networking.messageParts;

/**
 * @author Davide Grazzani
 * Enum used to define standard messages' headers and payloads
 */
public enum MessageFragment {
    ANSWER("answer"),
    ASSISTANT_CARD("assistantCard"),
    ASSISTANT_CARD_DECK("assistantCardDeck"),
    AUTH("auth"),
    AUTH_ID("auth_id"),
    CHARACTER_CARD("character"),
    CLOUD("cloud"),
    CLOUD_ID("cloud_id"),
    COIN("coin"),
    COLOR("color"),
    CONTEXT("context"),
    CONTEXT_CARD("cardSelection"),
    CONTEXT_CHARACTER("characterSelection"),
    CONTEXT_CLOUD("fillCloud"),
    CONTEXT_COIN("sendingCoin"),
    CONTEXT_COLOR("towerColor"),
    CONTEXT_DASHBOARD("dashboardUpdate"),
    CONTEXT_FIGURE("figureSelection"),
    CONTEXT_ISLAND("islandUpdate"),
    CONTEXT_MERGE("mergeIslands"),
    CONTEXT_MOTHER("motherNature"),
    CONTEXT_PHASE("changePhase"),
    CONTEXT_USERNAME("activeUsername"),
    DASHBOARD("dashboard"),
    GAME_TYPE("GameType"),
    GAME_NORMAL("normal game"),
    GAME_EXPERT("expert game"),
    GREETINGS("greetings"),
    GREETINGS_STATUS_SUCCESFULL("successful"),
    HALL_PAWN_RED("hall_red"),
    HALL_PAWN_BLUE("hall_blue"),
    HALL_PAWN_YELLOW("hall_yellow"),
    HALL_PAWN_GREEN("hall_green"),
    HALL_PAWN_PINK("hall_pink"),
    ISLAND("island"),
    ISLAND_ID("island_id"),
    LOBBY("lobby"),
    LOBBY_CREATE("create lobby"),
    LOBBY_JOIN("join lobby"),
    LOBBY_SIZE("LobbySize"),
    MN_LOCATION("MNLocation"),
    MERGED_ISLAND_1("mergedIsland1"),
    MERGED_ISLAND_2("mergedIsland2"),
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
    PLAYER("Player"),
    PLAYER_NAME("PlayerName"),
    PLAYER_ID("PlayerId"),
    STUDENT_COLOR("studentColor"),
    STUDENT_LOCATION("studentLocation"),
    TOWER_COLOR("towerColor"),
    WAITING_PAWN_RED("waiting_red"),
    WAITING_PAWN_BLUE("waiting_blue"),
    WAITING_PAWN_YELLOW("waiting_yellow"),
    WAITING_PAWN_GREEN("waiting_green"),
    WAITING_PAWN_PINK("waiting_pink"),
    WINNER("winner");

    private final String fragment;

    /**
     * Enum Builder
     * @param fragment represent the standard header or payload
     */
    MessageFragment(String fragment){
        this.fragment = fragment;
    }

    /**
     * Getter method
     * @return the header or payload associated with the enum's keyword
     */
    public String getFragment() {
        return fragment;
    }

    /**
     * Method used to return the enum with a string
     * @param string is the name linked with an enum
     * @return the associated enum
     */
    public static MessageFragment getEnum(String string) {
        MessageFragment frag = null;
        for (MessageFragment fragment : MessageFragment.values()) {
            if (fragment.getFragment().equals(string))
                frag = fragment;
        }
        return frag;
    }
}