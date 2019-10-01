/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.card.cardbase;

import net.minecraft.item.Item;

import java.util.Collection;

public interface ICardItemSink {
    /**
     * 回収する行き先未指定アイテムを指定します。
     * @return 回収するアイテム
     * */
    Collection<Item> getPassiveRequestedItems();

    /**
     * デフォルトルート指定の場合はtrueを返して下さい。
     * @return デフォルトルートならtrue
     */
    boolean isDefaultRoute();

    /**
     * 行き先未指定アイテムの回収優先順位を指定します。
     * 通常のItemSinkを10としてください。
     * @return 優先順位(0以上)
     */
    int getPassiveRequestOrder();
}
