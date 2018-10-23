package solab.innovativetransport.card.cardbase;

import solab.innovativetransport.card.EnumCards;
import solab.innovativetransport.card.extractor.ExtractorCardMk1;
import solab.innovativetransport.card.itemsink.ItemSinkCard;
import solab.innovativetransport.pipe.Pipe;

public abstract class CardBase implements ICardBehaviour {

    public Pipe pipe;

    /**
     * カードがセットされた時に呼び出されます。
     *
     * @param pipeIn セットされたパイプ
     */
    @Override
    public void onCardInserted(Pipe pipeIn) {
        this.pipe = pipeIn;
    }

    /**
     * カードが取り外された時に呼ばれます。
     */
    @Override
    public void onCardDetached() {
        pipe = null;
    }

    /**
     * カードの種類を返します。
     *
     * @return カードの種類
     */
    @Override
    public abstract EnumCards getCardType();

    public static CardBase getCardFromType(EnumCards type) {
        switch (type) {
            case ItemSink:
                return new ItemSinkCard();
            case Extractor:
                return new ExtractorCardMk1();
                default:
                    return null;
        }
    }
}
