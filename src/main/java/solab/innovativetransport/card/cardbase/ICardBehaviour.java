package solab.innovativetransport.card.cardbase;

import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.pipe.normal.Pipe;

public interface ICardBehaviour {

    /**
     * カードがセットされた時に呼び出されます。
     * @param pipeIn セットされたパイプ
     */
    void onCardInserted(Pipe pipeIn);

    /**
     * カードが取り外された時に呼ばれます。
     */
    void onCardDetached();

    /**
     * カードの種類を返します。
     * @return カードの種類
     */
    InnovativeTransport.EnumCards getCardType();
}
