package solab.innovativetransport.card.cardbase;

import solab.innovativetransport.pipe.normal.Pipe;
import solab.innovativetransport.utils.RequestItemStack;

public interface ICardProvider {

    /**
     * 要求系がアイテムを発注した際、アイテム一種類毎に一回呼ばれます。
     * 発送は自分でやって下さい。
     * @param requestedItem 要求されたアイテムとその個数。
     * @param destination 宛先。
     * @return 送ったアイテムとその個数。
     */
    RequestItemStack activeRequest(RequestItemStack requestedItem, Pipe destination);
}
