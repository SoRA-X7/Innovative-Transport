package solab.innovativetransport.card.itemsink;

import net.minecraft.item.Item;
import solab.innovativetransport.card.EnumCards;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardItemSink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemSinkCard extends CardBase implements ICardItemSink {

    public List<Item> requestedItems = new ArrayList<>();
    public boolean defaultRoute = false;

    /**
     * 回収する行き先未指定アイテムを指定します。
     *
     * @return 回収するアイテム
     */
    @Override
    public Collection<Item> getPassiveRequestedItems() {
        return requestedItems;
    }

    /**
     * デフォルトルート指定の場合はtrueを返して下さい。
     *
     * @return デフォルトルートならtrue
     */
    @Override
    public boolean isDefaultRoute() {
        return defaultRoute;
    }

    /**
     * 行き先未指定アイテムの回収優先順位を指定します。
     * 通常のItemSinkを10としてください。
     *
     * @return 優先順位(0以上)
     */
    @Override
    public int getPassiveRequestOrder() {
        return 10;
    }

    /**
     * カードの種類を返します。
     *
     * @return カードの種類
     */
    @Override
    public EnumCards getCardType() {
        return EnumCards.ItemSink;
    }
}
