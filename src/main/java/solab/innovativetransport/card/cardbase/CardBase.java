package solab.innovativetransport.card.cardbase;

import solab.innovativetransport.pipe.Pipe;

public class CardBase implements ICardBehaviour {

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
}
