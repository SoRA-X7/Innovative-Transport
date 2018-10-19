package solab.innovativetransport.card.extractor;

import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardTickable;

public class ExtractorCardMk1 extends CardBase implements ICardTickable {

    private int timer = 0;
    private int interval = 10;
    /**
     * 毎Tick呼び出されます。
     */
    @Override
    public void update() {
        if (++timer >= interval) {
            timer = 0;

        }
    }
}
