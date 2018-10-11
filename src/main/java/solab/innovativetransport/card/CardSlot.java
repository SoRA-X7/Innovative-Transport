package solab.innovativetransport.card;

import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardItemSink;
import solab.innovativetransport.card.cardbase.ICardTickable;

public class CardSlot {

    CardBase cards[] = new CardBase[4];

    public CardSlot() {}

    public void update() {
        for (CardBase card:
             cards) {
            if (card instanceof ICardTickable) {
                ((ICardTickable) card).update();
            }
            if (card instanceof ICardItemSink) {

            }
        }
    }

}
