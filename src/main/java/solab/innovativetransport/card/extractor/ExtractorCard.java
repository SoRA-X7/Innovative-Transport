package solab.innovativetransport.card.extractor;

import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardTickable;
import solab.innovativetransport.pipe.Pipe;

public class ExtractorCard extends CardBase implements ICardTickable {

    /**
     * 毎Tick呼び出されます。
     */
    @Override
    public void update() {

    }

    /**
     * カードがセットされた時に呼び出されます。
     *
     * @param pipe セットされたパイプ
     */
    @Override
    public void onCardInserted(Pipe pipe) {

    }
}
