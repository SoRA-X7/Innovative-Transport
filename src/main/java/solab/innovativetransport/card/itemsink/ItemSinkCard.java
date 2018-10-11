package solab.innovativetransport.card.itemsink;

import net.minecraft.item.Item;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardItemSink;

import java.util.Collection;

public class ItemSinkCard extends CardBase implements ICardItemSink {

    /**
     * 回収する行き先未指定アイテムを指定します。
     *
     * @return 回収するアイテム
     */
    @Override
    public Collection<Item> getPassiveRequestedItems() {
        return null;
    }

    /**
     * 行き先未指定アイテムの回収優先順位を指定します。
     * 通常のItemSinkを10としてください。
     *
     * @return 優先順位(> 0)
     */
    @Override
    public int getPassiveRequestOrder() {
        return 0;
    }
}
