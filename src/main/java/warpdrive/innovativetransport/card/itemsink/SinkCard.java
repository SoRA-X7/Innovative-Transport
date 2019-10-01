/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.card.itemsink;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import warpdrive.innovativetransport.card.cardbase.CardBase;
import warpdrive.innovativetransport.card.cardbase.ICardItemSink;
import warpdrive.innovativetransport.utils.Constants.EnumCards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SinkCard extends CardBase implements ICardItemSink {

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

    /**
     * Tierの範囲を返します。
     *
     * @return Tierの範囲を返します。
     */
    @Override
    public int[] getAcceptedTiers() {
        return new int[]{1};
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return writeToNBTShared(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readFromNBTShared(compound);
    }
}
