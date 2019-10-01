/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.card.cardbase;

import net.minecraft.nbt.NBTTagCompound;
import warpdrive.innovativetransport.card.extractor.ExtractorCard;
import warpdrive.innovativetransport.card.itemsink.SinkCard;
import warpdrive.innovativetransport.pipe.normal.Pipe;
import warpdrive.innovativetransport.utils.Constants.EnumCards;

public abstract class CardBase implements ICardBehaviour {

    public Pipe pipe;
    protected int tier;
    protected int maxStackSize;
    protected static int[] acceptedTiers = {1};

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

    /**
     * カードの種類を返します。
     *
     * @return カードの種類
     */
    @Override
    public abstract EnumCards getCardType();

    /**
     * Tierの範囲を返します。
     * @return Tierの範囲を返します。
     */
    public abstract int[] getAcceptedTiers();

    public static CardBase getCardFromType(EnumCards type, int mk) {
        switch (type) {
            case ItemSink:
                return new SinkCard();
            case Extractor:
                return new ExtractorCard(mk);
                default:
                    return null;
        }
    }

    public abstract NBTTagCompound writeToNBT(NBTTagCompound compound);

    public abstract void readFromNBT(NBTTagCompound compound);

    protected NBTTagCompound writeToNBTShared(NBTTagCompound compound) {
        compound.setInteger("IT_Card_Tier",tier);
        compound.setInteger("IT_Card_Type",getCardType().ordinal());
        return compound;
    }

    protected void readFromNBTShared(NBTTagCompound compound) {
        tier = compound.getInteger("IT_Card_Tier");
    }
}
