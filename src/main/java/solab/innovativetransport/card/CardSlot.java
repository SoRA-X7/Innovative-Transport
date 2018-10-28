package solab.innovativetransport.card;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardTickable;
import solab.innovativetransport.pipe.Pipe;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class CardSlot {

    private List<CardBase> cards;
    private Pipe pipe;

    public CardSlot(Pipe pipeIn) {
        pipe = pipeIn;
        cards = new ArrayList<>(4);
    }

    boolean insert(CardBase newCard) {
        boolean success = cards.add(newCard);
        if (success) {
            cards.get(cards.size()-1).onCardInserted(pipe);
        }
        return success;
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
        System.out.println(cards.toString());
        for (CardBase card:cards) {
//            System.out.println("Write:" + card.writeToNBT(new NBTTagCompound()).toString());
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
//            System.out.println("Read:" + compound1.toString());
            CardBase card = CardBase.getCardFromType(EnumCards.values()[compound1.getInteger("IT_Card_Type")],compound1.getInteger("IT_Card_Tier"));
            card.readFromNBT(compound1);
            card.onCardInserted(pipe);
            cards.add(card);
        }
        System.out.println(cards.toString());
    }
}
