package solab.innovativetransport.card;

import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardTickable;
import solab.innovativetransport.pipe.Pipe;

import java.util.ArrayList;
import java.util.List;

public class CardSlot {

    private List<CardBase> cards = new ArrayList<>(4);
    private Pipe pipe;

    public CardSlot(Pipe pipeIn) {
        pipe = pipeIn;
    }

    boolean insert(CardBase newCard) {
        boolean success = cards.add(newCard);
        if (success) {
            cards.get(cards.size()-1).onCardInserted(pipe);
        }
        return success;
    }

    public List<CardBase> getCards() {
        return cards;
    }

    public void update() {
        for (CardBase card:
             cards) {
            if (card instanceof ICardTickable) {
                ((ICardTickable) card).update();
            }
        }
    }

}
