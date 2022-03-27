package it.polimi.ingsw.model;

import it.polimi.ingsw.debug.AssistantCardDeck;
import it.polimi.ingsw.debug.Cloud;
import it.polimi.ingsw.debug.Student;

import java.util.ArrayList;

public abstract class NormalGamer {
    private final int token;
    private String username;
    //private Stream input;
    private boolean activity;
    private AssistantCardDeck deck;

    public NormalGamer(int token, String username/*, Stream input*/) {
        this.token = token;
        this.username = username;
        //this.input = input;
        activity = true;
        deck = new AssistantCardDeck();
        /**
         * Problema: la dashboard, per essere creata, necessita di un ArrayList di Student e un int per towers. Però questi due param. da dove
         * li prende Gamer? Bisogna magari fare una funzione a parte in Game che viene chiamata da Gamer (dopo che a sua volta Controller avrà
         * chiamato Game o direttamente Gamer), quando la partita inizia, nella quale viene creata tale dashboard (servono infatti le info sul
         * numero di giocatori, ed è solo la classe Game che lo sa)
         */
    }

    public abstract void selectCloud(Cloud cloud);
    public abstract void initGamer(ArrayList<Student> students, int towers);
}
