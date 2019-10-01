/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.pipe.attachment.cardslot;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import warpdrive.innovativetransport.card.cardbase.CardBase;
import warpdrive.innovativetransport.card.cardbase.ICardTickable;
import warpdrive.innovativetransport.pipe.attachment.IPipeAttachment;
import warpdrive.innovativetransport.pipe.normal.Pipe;
import warpdrive.innovativetransport.utils.Constants.EnumCards;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class CardSlot implements IPipeAttachment {

    private List<CardBase> cards;
    private Pipe pipe;

    public CardSlot(Pipe pipeIn) {
        pipe = pipeIn;
        cards = new ArrayList<>(4);
    }

    public CardBase insert(CardBase newCard) {
        return insert(newCard, -1);
    }

    public CardBase insert(CardBase newCard, int index) {
        CardBase card;
        if (index == -1) {
            cards.add(newCard);
            card = cards.get(cards.size() - 1);
        } else {
            card = cards.set(index, newCard);
        }
        card.onCardInserted(pipe);
        return card;
    }

    public List<CardBase> getCards() {
        return cards;
    }

    public void update() {
        for (CardBase card:
             cards) {
            if (card instanceof ICardTickable) {
                ((ICardTickable) card).update();
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        for (CardBase card:cards) {
            tagList.appendTag(card.writeToNBT(new NBTTagCompound()));
        }
        compound.setTag("IT_CardSlot_Cards",tagList);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        cards.clear();
        NBTTagList tagList = compound.getTagList("IT_CardSlot_Cards", TAG_COMPOUND);
        for (int i=0; i<tagList.tagCount(); i++) {
            NBTTagCompound compound1 = tagList.getCompoundTagAt(i);
            CardBase card = CardBase.getCardFromType(EnumCards.values()[compound1.getInteger("IT_Card_Type")],compound1.getInteger("IT_Card_Tier"));
            if (card != null) {
                card.readFromNBT(compound1);
                card.onCardInserted(pipe);
                cards.add(card);
            }
        }
    }
}
