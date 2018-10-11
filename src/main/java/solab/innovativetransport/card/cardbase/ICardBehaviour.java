package solab.innovativetransport.card.cardbase;

import solab.innovativetransport.pipe.Pipe;

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
}
