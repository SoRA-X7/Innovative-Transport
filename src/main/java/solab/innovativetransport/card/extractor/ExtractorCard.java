package solab.innovativetransport.card.extractor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import solab.innovativetransport.card.EnumCards;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardTickable;
import solab.innovativetransport.transporter.ItemTransporter;
import solab.innovativetransport.utils.ModuleInventoryAccessHelper;

import java.util.Arrays;

public class ExtractorCard extends CardBase implements ICardTickable {

    private static int[] acceptedTiers = {1,2,3};
    private int timer;
    private int interval;

    public ExtractorCard(int tierIn) {
        this(tierIn,0);
    }
    public ExtractorCard(int tierIn,int timerIn) {
        this.timer = timerIn;
        this.tier = tierIn;
        switch (tierIn) {
            case 1:
                interval = 10;
                maxStackSize = 1;
                return;
            case 2:
                interval = 5;
                maxStackSize = 8;
                return;
            case 3:
                interval = 2;
                maxStackSize = 64;
                return;
            default:
                throw new IllegalArgumentException("Given mark value is not accepted. Given:" + tierIn + " Acceptable:" + Arrays.toString(acceptedTiers));
        }
    }
    /**
     * 毎Tick呼び出されます。
     */
    @Override
    public void update() {
        if (pipe.managedInventory != null && !pipe.getHolder().getWorld().isRemote) {
            if (++timer >= interval) {
                timer = 0;
                ItemStack stack = ModuleInventoryAccessHelper.pullNextItemInInventory(pipe.managedInventory,maxStackSize);
                if (stack != null) {
                    ItemTransporter transporter = new ItemTransporter(stack,pipe.getHolder(), EnumFacing.DOWN, pipe.getRandomExit(EnumFacing.DOWN));
                    pipe.getHolder().inject(transporter);
                }
            }
        }
    }

    @Override
    public EnumCards getCardType() {
        return EnumCards.Extractor;
    }

    /**
     * Tierの範囲を返します。
     *
     * @return Tierの範囲を返します。
     */
    @Override
    public int[] getAcceptedTiers() {
        return acceptedTiers;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = writeToNBTShared(compound);
        compound.setInteger("IT_ExtractorCard_Timer", timer);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readFromNBTShared(compound);
        timer = compound.getInteger("IT_ExtractorCard_Timer");
    }
}
