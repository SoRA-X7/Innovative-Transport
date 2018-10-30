package solab.innovativetransport.card.provider;

import net.minecraft.nbt.NBTTagCompound;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardProvider;
import solab.innovativetransport.pipe.normal.Pipe;
import solab.innovativetransport.utils.Constants;
import solab.innovativetransport.utils.RequestItemStack;

public class ProviderCard extends CardBase implements ICardProvider {

    /**
     * カードの種類を返します。
     *
     * @return カードの種類
     */
    @Override
    public Constants.EnumCards getCardType() {
        return null;
    }

    /**
     * Tierの範囲を返します。
     *
     * @return Tierの範囲を返します。
     */
    @Override
    public int[] getAcceptedTiers() {
        return new int[0];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return writeToNBTShared(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readFromNBTShared(compound);
    }

    /**
     * 要求系がアイテムを発注した際、アイテム一種類毎に一回呼ばれます。
     * 発送は自分でやって下さい。
     *
     * @param requestedItem 要求されたアイテムとその個数。
     * @param destination   宛先。
     * @return 送ったアイテムとその個数。
     */
    @Override
    public RequestItemStack activeRequest(RequestItemStack requestedItem, Pipe destination) {
        return null;
    }
}
