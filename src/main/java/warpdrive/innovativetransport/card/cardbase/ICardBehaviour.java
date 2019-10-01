/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.card.cardbase;

import warpdrive.innovativetransport.pipe.normal.Pipe;
import warpdrive.innovativetransport.utils.Constants.EnumCards;

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
    EnumCards getCardType();
}
