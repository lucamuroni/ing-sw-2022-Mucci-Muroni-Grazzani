package it.polimi.ingsw.debug;

import java.util.ArrayList;

public class AssistantCardDeck {
    private ArrayList<AssistantCard> cardList;
    private AssistantCard currentSelection;
    private AssistantCard pastSelection;

    public AssistantCardDeck(){
        cardList = new ArrayList<AssistantCard>(10);
        int temp=1;
        for(int i=1; i<=10; i++){
            cardList.add(new AssistantCard(i,temp));
            if(i%2==0){
                temp++;
            }
        }
    }

    public ArrayList<AssistantCard> getCardList() {
        return cardList;
    }

    public AssistantCard getCurrentSelection() {
        return currentSelection;
    }

    public AssistantCard getPastSelection() {
        return pastSelection;
    }

    public void setCurrentSelection(AssistantCard currentSelection) {
        this.currentSelection = currentSelection;
    }

    public void setPastSelection(AssistantCard pastSelection) {
        this.pastSelection = pastSelection;
    }
}
